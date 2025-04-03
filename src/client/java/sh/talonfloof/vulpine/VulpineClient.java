package sh.talonfloof.vulpine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;
import sh.talonfloof.vulpine.registry.ItemRegistration;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class VulpineClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register((ctx, tickCounter) -> {
			var instance = MinecraftClient.getInstance();
			if(instance.currentScreen != null)
				return;
			if(instance.player.getMainHandStack().isOf(ItemRegistration.FOX_O_METER)) {
				var raycast = instance.crosshairTarget;
				if (raycast instanceof EntityHitResult) {
					if (((EntityHitResult) raycast).getEntity() instanceof FoxEntity fop) {
						ctx.drawTooltip(instance.textRenderer, List.of(Text.of(WordUtils.capitalize(fop.getVariant().asString())+" Fox").getWithStyle(Style.EMPTY.withBold(true)).getFirst(),Text.of("Trusts you? "+(fop.getTrustedUuids().contains(instance.player.getUuid())?"Yes":"No")).getWithStyle(Style.EMPTY.withColor(Formatting.GRAY)).getFirst(),Text.of("Tame Stage: "+fop.getDataTracker().get(Vulpine.TAME_PROGRESS)+"/4").getWithStyle(Style.EMPTY.withColor(Formatting.GRAY)).getFirst()),ctx.getScaledWindowWidth()/2,ctx.getScaledWindowHeight()/2);
					}
				}
			}
		});
	}
}