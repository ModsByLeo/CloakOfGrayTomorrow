package adudecalledleo.graytomorrow.mixin.client;

import adudecalledleo.graytomorrow.GrayTomorrowStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Matrix4f;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Inject(method = "render",
			at = @At(value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderSystem;clear(IZ)V", remap = false,
					shift = At.Shift.AFTER),
			cancellable = true)
	private void actuallyDoNotRender(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline,
			Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager,
			Matrix4f projectionMatrix, CallbackInfo ci) {
		if (camera.getFocusedEntity() instanceof LivingEntity living
				&& living.hasStatusEffect(GrayTomorrowStatusEffects.TRUE_BLINDNESS)) {
			ci.cancel();
		}
	}
}
