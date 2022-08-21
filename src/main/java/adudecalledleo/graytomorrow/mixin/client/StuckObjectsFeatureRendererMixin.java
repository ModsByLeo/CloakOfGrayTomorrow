package adudecalledleo.graytomorrow.mixin.client;

import adudecalledleo.graytomorrow.GrayTomorrowComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.StuckObjectsFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Mixin(StuckObjectsFeatureRenderer.class)
public abstract class StuckObjectsFeatureRendererMixin {
	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
			at = @At("HEAD"), cancellable = true)
	private void graytomorrow$skipRenderingIfFullyInvisible(MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance,
			float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
		if (entity.isInvisible() && GrayTomorrowComponents.get(GrayTomorrowComponents.EQUIPMENT_INVISIBLE, entity)) {
			ci.cancel();
		}
	}
}
