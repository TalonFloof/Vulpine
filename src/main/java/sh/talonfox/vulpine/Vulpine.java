package sh.talonfox.vulpine;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.Entity;
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
import net.minecraft.recipe.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Objects;

public class Vulpine implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("vulpine");
	public static final TrackedData<Integer> TAME_PROGRESS = DataTracker.registerData(net.minecraft.entity.passive.FoxEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public static FoxEntity.Type SILVER_FOX = null;
	public static FoxEntity.Type GRAY_FOX = null;
	public static FoxEntity.Type CROSS_FOX = null;
	public static ArrayList<FoxEntity.Type> foxes = null;

	@Override
	public void onInitialize() {

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
			entity.goalSelector.add(4, new FleeEntityGoal<PlayerEntity>(entity, PlayerEntity.class, 16.0f, 1.6, 1.4, e -> FoxEntity.NOTICEABLE_PLAYER_FILTER.test((Entity)e) && entity.getDataTracker().get(Vulpine.TAME_PROGRESS) < 3 && entity.getDataTracker().get(Vulpine.TAME_PROGRESS) == 2 ? !Objects.equals(entity.getDataTracker().get(FoxEntity.OWNER).orElse(null), e.getUuid()) : !entity.canTrust(e.getUuid()) && !entity.isAggressive()));
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
}