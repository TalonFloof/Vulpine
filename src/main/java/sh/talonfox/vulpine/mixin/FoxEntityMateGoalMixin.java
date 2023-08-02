package sh.talonfox.vulpine.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sh.talonfox.vulpine.FoxFollowPlayerGoal;
import sh.talonfox.vulpine.FoxSitGoal;
import sh.talonfox.vulpine.Vulpine;

@Mixin(FoxEntity.MateGoal.class)
@SuppressWarnings("unused")
public class FoxEntityMateGoalMixin {
    @Inject(method = "breed", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnEntityAndPassengers(Lnet/minecraft/entity/Entity;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void increaseTameProgress(CallbackInfo ci, ServerWorld serverWorld, FoxEntity foxEntity) {
        int prog1 = ((FoxEntity)((FoxEntity.MateGoal)(Object)this).animal).getDataTracker().get(Vulpine.TAME_PROGRESS);
        int prog2 = ((FoxEntity)((FoxEntity.MateGoal)(Object)this).mate).getDataTracker().get(Vulpine.TAME_PROGRESS);
        int newStage = Math.min(4, Math.min(prog1, prog2) + 1);
        foxEntity.getDataTracker().set(Vulpine.TAME_PROGRESS, newStage);
        Vulpine.addFoxGoals(foxEntity,newStage);
    }
}
