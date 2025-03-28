package sh.talonfloof.vulpine.mixin.client;

import net.minecraft.client.render.entity.FoxEntityRenderer;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.talonfloof.vulpine.FoxVariantTexture;

@Mixin(FoxEntityRenderer.class)
public class FoxEntityRendererMixin {
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    public void getTexture(FoxEntity foxEntity, CallbackInfoReturnable<Identifier> info) {
        Identifier textures = FoxVariantTexture.getTexture(foxEntity);
        if (textures != null)
            info.setReturnValue(textures);
    }
}
