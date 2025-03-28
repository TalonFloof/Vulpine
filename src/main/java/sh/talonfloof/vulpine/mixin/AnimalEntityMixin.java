package sh.talonfloof.vulpine.mixin;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin {

    //Overide light spawn restriction of foxes
    @Inject(method = "getPathfindingFavor", at = @At("HEAD"), cancellable = true)
    public void vulpine$overideLightLevel(BlockPos pos, WorldView world, CallbackInfoReturnable<Float> cir){
        AnimalEntity animal = (AnimalEntity) (Object)this;
        if(animal instanceof FoxEntity foxEntity){
            cir.setReturnValue(10.f);
            cir.cancel();
        }
    }
}
