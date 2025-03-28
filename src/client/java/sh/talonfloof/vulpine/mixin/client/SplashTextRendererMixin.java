package sh.talonfloof.vulpine.mixin.client;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Ya know why not?
@Mixin(SplashTextRenderer.class)
public class SplashTextRendererMixin {
    @Mutable
    @Shadow
    @Final
    private String text;

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("TAIL"))
    public void foxesArePlayers(String text, CallbackInfo ci) {
        if(this.text.equals("In case it isn't obvious, foxes aren't players.")) {
            this.text = "In case it isn't obvious, foxes are players.";
        }
    }
}
