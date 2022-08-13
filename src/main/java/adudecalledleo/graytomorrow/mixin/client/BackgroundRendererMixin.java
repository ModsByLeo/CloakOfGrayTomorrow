package adudecalledleo.graytomorrow.mixin.client;

import adudecalledleo.graytomorrow.CloakStatusEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
	@Shadow
	private static float red;
	@Shadow
	private static float green;
	@Shadow
	private static float blue;

	@Shadow
	private static long lastWaterFogColorUpdateTime;

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private static void blindPeopleSeeBlackProbably(Camera camera, float tickDelta, ClientWorld world, int viewDistance,
			float skyDarkness, CallbackInfo ci) {
		if (camera.getFocusedEntity() instanceof LivingEntity living
				&& living.hasStatusEffect(CloakStatusEffects.TRUE_BLINDNESS)) {
			red = 0;
			green = 0;
			blue = 0;
			lastWaterFogColorUpdateTime = -1;
			RenderSystem.clearColor(red, green, blue, 0);
			ci.cancel();
		}
	}
}
