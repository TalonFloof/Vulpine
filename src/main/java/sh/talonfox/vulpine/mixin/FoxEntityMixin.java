package sh.talonfox.vulpine.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.talonfox.vulpine.Vulpine;

import java.util.UUID;

import static net.minecraft.entity.passive.FoxEntity.OWNER;

/*
Tame Stages:
0: Wild
1: Trusting to Owner
2: Trusting to Owner and Others
3: Trusting to All
4: Fully Tamed (will fight mobs and is loyal to you)
 */

@Mixin(FoxEntity.class)
@SuppressWarnings("unused")
public abstract class FoxEntityMixin extends AnimalEntity {
    protected FoxEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initDataTracker()V")
    protected void addTameProgressTracker(CallbackInfo ci) {
        ((FoxEntity) (Object) this).getDataTracker().startTracking(Vulpine.TAME_PROGRESS, 0);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void tameProgressSave(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("TameProgress", ((FoxEntity) (Object) this).getDataTracker().get(Vulpine.TAME_PROGRESS));
    }

    @Unique
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ActionResult actionResult = super.interactMob(player,hand);
        if(actionResult.isAccepted()) return actionResult;
        UUID uuid = ((FoxEntity)(Object)this).getDataTracker().get(OWNER).orElse(null);
        if (((FoxEntity)(Object)this).getDataTracker().get(Vulpine.TAME_PROGRESS) != 4 || !player.getUuid().equals(uuid)) return ActionResult.PASS;
        ((FoxEntity)(Object)this).setSitting(!((FoxEntity)(Object)this).isSitting());
        this.jumping = false;
        this.navigation.stop();
        this.setTarget(null);
        return ActionResult.SUCCESS;
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void tameProgressLoad(NbtCompound nbt, CallbackInfo ci) {
        ((FoxEntity) (Object) this).getDataTracker().set(Vulpine.TAME_PROGRESS,nbt.getInt("TameProgress"));
        Vulpine.addFoxGoals(((FoxEntity) (Object) this),nbt.getInt("TameProgress"));
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    public void addAiGoals(CallbackInfo ci) {
        ((FoxEntity) (Object) this).followChickenAndRabbitGoal = new ActiveTargetGoal<AnimalEntity>(((FoxEntity) (Object) this), AnimalEntity.class, 10, false, false, entity -> ((FoxEntity) (Object) this).getDataTracker().get(Vulpine.TAME_PROGRESS) < 3 && (entity instanceof ChickenEntity || entity instanceof RabbitEntity));
        ((FoxEntity) (Object) this).followBabyTurtleGoal = new ActiveTargetGoal<TurtleEntity>(((FoxEntity) (Object) this), TurtleEntity.class, 10, false, false, entity -> TurtleEntity.BABY_TURTLE_ON_LAND_FILTER.test((LivingEntity)entity) && ((FoxEntity) (Object) this).getDataTracker().get(Vulpine.TAME_PROGRESS) < 3);
        ((FoxEntity) (Object) this).followFishGoal = new ActiveTargetGoal<FishEntity>(((FoxEntity) (Object) this), FishEntity.class, 20, false, false, entity -> entity instanceof SchoolingFishEntity && ((FoxEntity) (Object) this).getDataTracker().get(Vulpine.TAME_PROGRESS) < 3);
        ((FoxEntity) (Object) this).goalSelector.add(0, ((FoxEntity) (Object) this).new FoxSwimGoal());
        ((FoxEntity) (Object) this).goalSelector.add(3, ((FoxEntity) (Object) this).new MateGoal(1.0));
        ((FoxEntity) (Object) this).goalSelector.add(6, ((FoxEntity) (Object) this).new JumpChasingGoal());
        ((FoxEntity) (Object) this).goalSelector.add(11, ((FoxEntity) (Object) this).new PickupItemGoal());
        ((FoxEntity) (Object) this).goalSelector.add(12, ((FoxEntity) (Object) this).new LookAtEntityGoal(((FoxEntity) (Object) this), PlayerEntity.class, 24.0f));
        ((FoxEntity) (Object) this).goalSelector.add(7, ((FoxEntity) (Object) this).new AttackGoal((double) 1.2f, true));
        ((FoxEntity) (Object) this).goalSelector.add(4, new FleeEntityGoal<WolfEntity>(((FoxEntity) (Object) this), WolfEntity.class, 8.0f, 1.6, 1.4, entity -> !((WolfEntity) entity).isTamed() && !((FoxEntity) (Object) this).isAggressive()));
        ((FoxEntity) (Object) this).goalSelector.add(4, new FleeEntityGoal<PolarBearEntity>(((FoxEntity) (Object) this), PolarBearEntity.class, 8.0f, 1.6, 1.4, entity -> !((FoxEntity) (Object) this).isAggressive()));
        ((FoxEntity) (Object) this).goalSelector.add(10, new PounceAtTargetGoal(((FoxEntity) (Object) this), 0.4f));
        ((FoxEntity) (Object) this).goalSelector.add(11, new WanderAroundFarGoal(((FoxEntity) (Object) this), 1.0));
        ((FoxEntity) (Object) this).targetSelector.add(3, ((FoxEntity) (Object) this).new DefendFriendGoal(LivingEntity.class, false, false, entity -> FoxEntity.JUST_ATTACKED_SOMETHING_FILTER.test((Entity) entity) && !((FoxEntity) (Object) this).canTrust(entity.getUuid())));
        ci.cancel();
    }

    @Redirect(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/FoxEntity$Type;fromBiome(Lnet/minecraft/registry/entry/RegistryEntry;)Lnet/minecraft/entity/passive/FoxEntity$Type;"))
    public FoxEntity.Type setFoxVariant(RegistryEntry<Biome> biome) {
        FoxEntity fox = FoxEntity.class.cast(this);
        Identifier id = biome.getKey().get().getValue();
        if(id.equals(BiomeKeys.OLD_GROWTH_PINE_TAIGA.getValue()) || id.equals(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA.getValue())) {
            int variant = Random.create().nextInt(9);
            if(variant < 5) {
                return Vulpine.GRAY_FOX;
            } else if(variant < 7) {
                return FoxEntity.Type.RED;
            } else if(variant < 8) {
                return Vulpine.SILVER_FOX;
            } else {
                return Vulpine.CROSS_FOX;
            }
        } else if(biome.isIn(BiomeTags.SPAWNS_SNOW_FOXES)) {
            int variant = Random.create().nextInt(5);
            if(variant < 4) {
                return FoxEntity.Type.SNOW;
            } else {
                return Vulpine.SILVER_FOX;
            }
        } else {
            int variant = Random.create().nextInt(9);
            if(variant < 5) {
                return FoxEntity.Type.RED;
            } else if(variant < 7) {
                return Vulpine.SILVER_FOX;
            } else {
                return Vulpine.CROSS_FOX;
            }
        }
    }
}