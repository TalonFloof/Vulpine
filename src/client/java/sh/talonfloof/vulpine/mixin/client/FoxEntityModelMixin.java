package sh.talonfloof.vulpine.mixin.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.FoxEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FoxEntityModel.class)
public class FoxEntityModelMixin {
    @Inject(method = "getTexturedModelData", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void addAntlers(CallbackInfoReturnable<TexturedModelData> cir, ModelData modelData, ModelPartData modelPartData, ModelPartData modelPartData2, ModelPartData modelPartData3, Dilation dilation, ModelPartBuilder modelPartBuilder, ModelPartBuilder modelPartBuilder2) {
        modelPartData2.addChild("antelerLeft", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -6.0F, 0.0F, 4.0F, 4.0F, 0.0F), ModelTransform.rotation(0,(float)Math.toRadians(90),0));
        modelPartData2.addChild("antelerRight", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -6.0F, 2.0F, 4.0F, 4.0F, 0.0F), ModelTransform.rotation(0,(float)Math.toRadians(90),0));
    }
}
