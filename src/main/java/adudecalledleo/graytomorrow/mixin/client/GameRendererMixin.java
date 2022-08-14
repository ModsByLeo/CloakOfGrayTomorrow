package adudecalledleo.graytomorrow.mixin.client;

import adudecalledleo.graytomorrow.GrayTomorrowStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Shadow
	private boolean renderHand;
	@Shadow @Final
	private Camera camera;

	@Unique
	private boolean savedRenderHand;

	@Inject(method = "renderWorld",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V",
					ordinal = 1,
					shift = At.Shift.AFTER))
	private void lookAtMyInvisibleHands(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
		this.savedRenderHand = this.renderHand;
		if (this.camera.getFocusedEntity() instanceof LivingEntity living
				&& living.hasStatusEffect(GrayTomorrowStatusEffects.TRUE_BLINDNESS)) {
			this.renderHand = false;
		}
	}

	@Inject(method = "renderWorld", at = @At("TAIL"))
	private void ohRight(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
		this.renderHand = this.savedRenderHand;
	}
}
