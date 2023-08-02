package sh.talonfox.vulpine.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sh.talonfox.vulpine.Vulpine;

import java.util.Objects;

/*
Tame Stages:
0: Wild
1: Trusting to Owner
2: Trusting to Owner and Others
3: Trusting to All
4: Fully Tamed (will fight mobs and is loyal to you)
 */

@Mixin(FoxEntity.class)
@Debug(export = true)
public abstract class FoxEntityMixin {

    @Inject(at = @At("TAIL"), method = "initDataTracker()V")
    protected void addTameProgressTracker(CallbackInfo ci) {
        ((FoxEntity) (Object) this).getDataTracker().startTracking(Vulpine.TAME_PROGRESS, 0);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void tameProgressSave(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("TameProgress", ((FoxEntity) (Object) this).getDataTracker().get(Vulpine.TAME_PROGRESS));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void tameProgressLoad(NbtCompound nbt, CallbackInfo ci) {
        ((FoxEntity) (Object) this).getDataTracker().set(Vulpine.TAME_PROGRESS,nbt.getInt("TameProgress"));
    }

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    public void addAiGoals(CallbackInfo ci) {
        ((FoxEntity) (Object) this).followChickenAndRabbitGoal = new ActiveTargetGoal<AnimalEntity>(((FoxEntity) (Object) this), AnimalEntity.class, 10, false, false, entity -> ((FoxEntity) (Object) this).getDataTracker().get(Vulpine.TAME_PROGRESS) < 3 && (entity instanceof ChickenEntity || entity instanceof RabbitEntity));
        ((FoxEntity) (Object) this).followBabyTurtleGoal = new ActiveTargetGoal<TurtleEntity>(((FoxEntity) (Object) this), TurtleEntity.class, 10, false, false, entity -> TurtleEntity.BABY_TURTLE_ON_LAND_FILTER.test((LivingEntity)entity) && ((FoxEntity) (Object) this).getDataTracker().get(Vulpine.TAME_PROGRESS) < 3);
        ((FoxEntity) (Object) this).followFishGoal = new ActiveTargetGoal<FishEntity>(((FoxEntity) (Object) this), FishEntity.class, 20, false, false, entity -> entity instanceof SchoolingFishEntity && ((FoxEntity) (Object) this).getDataTracker().get(Vulpine.TAME_PROGRESS) < 3);
        ((FoxEntity) (Object) this).goalSelector.add(0, ((FoxEntity) (Object) this).new FoxSwimGoal());
        ((FoxEntity) (Object) this).goalSelector.add(0, new PowderSnowJumpGoal(((FoxEntity) (Object) this), ((FoxEntity) (Object) this).getWorld()));
        ((FoxEntity) (Object) this).goalSelector.add(1, ((FoxEntity) (Object) this).new StopWanderingGoal());
        ((FoxEntity) (Object) this).goalSelector.add(2, ((FoxEntity) (Object) this).new EscapeWhenNotAggressiveGoal(2.2));
        ((FoxEntity) (Object) this).goalSelector.add(3, ((FoxEntity) (Object) this).new MateGoal(1.0));
        ((FoxEntity) (Object) this).goalSelector.add(4, new FleeEntityGoal<PlayerEntity>(((FoxEntity) (Object) this), PlayerEntity.class, 16.0f, 1.6, 1.4, entity -> FoxEntity.NOTICEABLE_PLAYER_FILTER.test((Entity)entity) && ((FoxEntity) (Object) this).getDataTracker().get(Vulpine.TAME_PROGRESS) < 3 && (((FoxEntity) (Object) this).getDataTracker().get(Vulpine.TAME_PROGRESS) == 2 ? !Objects.equals(((FoxEntity) (Object) this).getDataTracker().get(FoxEntity.OWNER).orElse(null), entity.getUuid()) : !((FoxEntity) (Object) this).canTrust(entity.getUuid())) && !((FoxEntity) (Object) this).isAggressive()));
        ((FoxEntity) (Object) this).goalSelector.add(4, new FleeEntityGoal<WolfEntity>(((FoxEntity) (Object) this), WolfEntity.class, 8.0f, 1.6, 1.4, entity -> !((WolfEntity)entity).isTamed() && !((FoxEntity) (Object) this).isAggressive()));
        ((FoxEntity) (Object) this).goalSelector.add(4, new FleeEntityGoal<PolarBearEntity>(((FoxEntity) (Object) this), PolarBearEntity.class, 8.0f, 1.6, 1.4, entity -> !((FoxEntity) (Object) this).isAggressive()));
        ((FoxEntity) (Object) this).goalSelector.add(5, ((FoxEntity) (Object) this).new MoveToHuntGoal());
        ((FoxEntity) (Object) this).goalSelector.add(6, ((FoxEntity) (Object) this).new JumpChasingGoal());
        ((FoxEntity) (Object) this).goalSelector.add(6, ((FoxEntity) (Object) this).new AvoidDaylightGoal(1.25));
        ((FoxEntity) (Object) this).goalSelector.add(7, ((FoxEntity) (Object) this).new AttackGoal((double)1.2f, true));
        ((FoxEntity) (Object) this).goalSelector.add(7, ((FoxEntity) (Object) this).new DelayedCalmDownGoal());
        ((FoxEntity) (Object) this).goalSelector.add(8, ((FoxEntity) (Object) this).new FollowParentGoal(((FoxEntity) (Object) this), 1.25));
        ((FoxEntity) (Object) this).goalSelector.add(9, ((FoxEntity) (Object) this).new GoToVillageGoal(32, 200));
        ((FoxEntity) (Object) this).goalSelector.add(10, ((FoxEntity) (Object) this).new EatBerriesGoal((double)1.2f, 12, 1));
        ((FoxEntity) (Object) this).goalSelector.add(10, new PounceAtTargetGoal(((FoxEntity) (Object) this), 0.4f));
        ((FoxEntity) (Object) this).goalSelector.add(11, new WanderAroundFarGoal(((FoxEntity) (Object) this), 1.0));
        ((FoxEntity) (Object) this).goalSelector.add(11, ((FoxEntity) (Object) this).new PickupItemGoal());
        ((FoxEntity) (Object) this).goalSelector.add(12, ((FoxEntity) (Object) this).new LookAtEntityGoal(((FoxEntity) (Object) this), PlayerEntity.class, 24.0f));
        ((FoxEntity) (Object) this).goalSelector.add(13, ((FoxEntity) (Object) this).new SitDownAndLookAroundGoal());
        ((FoxEntity) (Object) this).targetSelector.add(3, ((FoxEntity) (Object) this).new DefendFriendGoal(LivingEntity.class, false, false, entity -> FoxEntity.JUST_ATTACKED_SOMETHING_FILTER.test((Entity)entity) && !((FoxEntity) (Object) this).canTrust(entity.getUuid())));
        ci.cancel();
    }
}