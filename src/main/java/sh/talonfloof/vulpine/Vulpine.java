package sh.talonfloof.vulpine;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.PowderSnowJumpGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.talonfloof.vulpine.config.VulpineConfig;
import sh.talonfloof.vulpine.registry.ItemRegistration;
import sh.talonfloof.vulpine.registry.ModMobTags;
import sh.talonfloof.vulpine.world.generation.ModEntityGeneration;

import java.util.ArrayList;

public class Vulpine implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("vulpine");
    public static final String MOD_ID = "vulpine";
	public static final TrackedData<Integer> TAME_PROGRESS = DataTracker.registerData(FoxEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public static FoxEntity.Type SILVER_FOX = null;
	public static FoxEntity.Type GRAY_FOX = null;
	public static FoxEntity.Type CROSS_FOX = null;
	public static FoxEntity.Type TALON = null;
	public static FoxEntity.Type FENNEC_FOX = null;
	public static FoxEntity.Type ENDER_FOX = null;
	public static FoxEntity.Type SCULK_FOX = null;
	public static FoxEntity.Type NETHER_FOX = null;
	public static FoxEntity.Type ZOMBIE_FOX = null;
	public static FoxEntity.Type SKELETON_FOX = null;
	public static FoxEntity.Type ZOMBIFIED_FOX = null;
	public static FoxEntity.Type POLAR_FOX = null;
	public static FoxEntity.Type BLUE_FOX = null;
	public static FoxEntity.Type BLACK_FOX = null;
	public static FoxEntity.Type ALBINO_FOX = null;
	public static FoxEntity.Type GRAY2_FOX = null;
	public static FoxEntity.Type RED_FOX = null;
	public static FoxEntity.Type SOUL_FOX = null;
	public static FoxEntity.Type PINK_FOX = null;

	public static ArrayList<FoxEntity.Type> foxes = new ArrayList<>();

	public static VulpineConfig config = ConfigApiJava.registerAndLoadConfig(VulpineConfig::new);

	public static void addFoxes() {

		addDefaultFox(FoxEntity.Type.RED);
		addSnowFox(FoxEntity.Type.SNOW);

		addDefaultFox(RED_FOX);
		addDefaultFox(ALBINO_FOX);
		addDefaultFox(PINK_FOX);

		addTaigaFox(SILVER_FOX);
		addTaigaFox(GRAY_FOX);
		addTaigaFox(GRAY2_FOX);

		addSnowFox(CROSS_FOX);
		addSnowFox(POLAR_FOX);
		addSnowFox(BLUE_FOX);
		addSnowFox(BLACK_FOX);

		addDefaultFox(TALON);//TALON is remove from spawn pool on init.

		addDesertFox(FENNEC_FOX);

		addNetherFox(NETHER_FOX);
		addNetherFox(SOUL_FOX);

		addEnderFox(ENDER_FOX);

		addSculkFox(SCULK_FOX);

		addMonsterFox(ZOMBIE_FOX);
		addMonsterFox(SKELETON_FOX);
		addMonsterFox(ZOMBIFIED_FOX);

	}

	@Override
	public void onInitialize() {

		ModMobTags.registerModTags();
		ModEntityGeneration.addFoxSpawns();

		//add multi-biome rules
		FoxVariantSelector.SNOW_VARIANT.add(SILVER_FOX); //Silver spawn in any taiga, including the snowy ones
		FoxVariantSelector.TAIGA_VARIANT.add(FoxEntity.Type.RED); //Default fox is mostly taiga variant.
		FoxVariantSelector.TAIGA_VARIANT.add(RED_FOX); //Default fox is mostly taiga variant.
		FoxVariantSelector.TAIGA_VARIANT.add(ALBINO_FOX); //Default fox is mostly taiga variant.
		FoxVariantSelector.DEFAULT_VARIANT.remove(TALON);

		if(!config.spawning.enableBrightColorFoxes.get()) {
			FoxVariantSelector.DEFAULT_VARIANT.remove(PINK_FOX);
			FoxVariantSelector.SNOW_VARIANT.remove(BLUE_FOX);
		}
		if(!config.spawning.enableAlternateFoxVariants.get()) {
			FoxVariantSelector.DEFAULT_VARIANT.remove(RED_FOX);
			FoxVariantSelector.TAIGA_VARIANT.remove(GRAY2_FOX);
			FoxVariantSelector.SNOW_VARIANT.remove(BLACK_FOX);
		}

		ItemRegistration.register();
	}


	public static void addFoxGoals(FoxEntity entity, int tameProgress) {
		if(tameProgress == 4) {
			entity.goalSelector.add(1, new FoxSitGoal(entity));
			entity.goalSelector.add(1,  new FoxAttackWithOwnerGoal(entity));
			entity.goalSelector.add(6, new FoxFollowPlayerGoal(entity, 1.0, 10.0f, 2.0f));
		} else {
			entity.goalSelector.add(0, new PowderSnowJumpGoal(entity, entity.getWorld()));
			entity.goalSelector.add(1, entity.new StopWanderingGoal());
			entity.goalSelector.add(2, entity.new EscapeWhenNotAggressiveGoal(2.2));
			if(tameProgress < 3) {
				entity.goalSelector.add(4, new FleeEntityGoal<PlayerEntity>(entity, PlayerEntity.class, 16.0f, 1.6, 1.4, e -> !e.isSneaky() && !EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(e) && !entity.canTrust(e.getUuid()) && !entity.isAggressive()));
			}
			entity.goalSelector.add(5, entity.new MoveToHuntGoal());
			entity.goalSelector.add(7, entity.new DelayedCalmDownGoal());
			entity.goalSelector.add(8, entity.new FollowParentGoal(entity, 1.25));
			entity.goalSelector.add(9, entity.new GoToVillageGoal(32, 200));
			entity.goalSelector.add(10, entity.new EatBerriesGoal((double)1.2f, 12, 1));
			entity.goalSelector.add(13, entity.new SitDownAndLookAroundGoal());
		}
		if(tameProgress >= 2 && tameProgress < 4) {
			entity.goalSelector.add(1,new TemptGoal(entity,0.75, Ingredient.ofStacks(new ItemStack(Items.SWEET_BERRIES)),false));
		} else {
			entity.goalSelector.add(6, entity.new AvoidDaylightGoal(1.25));
		}
	}

	private static void addDefaultFox(FoxEntity.Type defaultFox){
		foxes.add(defaultFox);
		if(defaultFox != FoxEntity.Type.RED)
			FoxVariantTexture.addTexture(defaultFox, defaultFox.asString());
		FoxVariantSelector.DEFAULT_VARIANT.add(defaultFox);
	}

	private static void addSnowFox(FoxEntity.Type snowFox){
		foxes.add(snowFox);
		if(snowFox != FoxEntity.Type.SNOW)
			FoxVariantTexture.addTexture(snowFox, snowFox.asString());
		FoxVariantSelector.SNOW_VARIANT.add(snowFox);
	}

	private static void addTaigaFox(FoxEntity.Type taigaFox){
		foxes.add(taigaFox);
		FoxVariantTexture.addTexture(taigaFox, taigaFox.asString());
		FoxVariantSelector.TAIGA_VARIANT.add(taigaFox);
	}

	private static void addDesertFox(FoxEntity.Type desertFox){
		foxes.add(desertFox);
		FoxVariantTexture.addTexture(desertFox, desertFox.asString());
		FoxVariantSelector.DESERT_VARIANT.add(desertFox);
	}

	private static void addNetherFox(FoxEntity.Type netherFox){
		foxes.add(netherFox);
		FoxVariantTexture.addTexture(netherFox, netherFox.asString());
		FoxVariantSelector.NETHER_VARIANT.add(netherFox);
	}

	private static void addEnderFox(FoxEntity.Type enderFox){
		foxes.add(enderFox);
		FoxVariantTexture.addTexture(enderFox, enderFox.asString());
		FoxVariantSelector.ENDER_VARIANT.add(enderFox);
	}

	private static void addSculkFox(FoxEntity.Type sculkFox){
		foxes.add(sculkFox);
		FoxVariantTexture.addTexture(sculkFox, sculkFox.asString());
		FoxVariantSelector.SCULK_VARIANT.add(sculkFox);
	}

	private static void addMonsterFox(FoxEntity.Type monsterFox){
		foxes.add(monsterFox);
		FoxVariantTexture.addTexture(monsterFox, monsterFox.asString());
		FoxVariantSelector.MONSTER_VARIANT.add(monsterFox);
	}
}