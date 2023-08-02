package sh.talonfox.vulpine.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.talonfox.vulpine.Vulpine;

@Mixin(FoxEntity.GoToVillageGoal.class)
public class FoxEntityGotoVillageGoalMixin {
    @Inject(method = "canStart", at = @At("RETURN"), cancellable = true)
    public void canStart(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() && ((FoxEntity)(Object)this).getDataTracker().get(Vulpine.TAME_PROGRESS) < 3);
    }
}
