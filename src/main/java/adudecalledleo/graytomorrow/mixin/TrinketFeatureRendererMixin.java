package adudecalledleo.graytomorrow.mixin;

import adudecalledleo.graytomorrow.GrayTomorrowComponents;
import dev.emi.trinkets.TrinketFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Mixin(value = TrinketFeatureRenderer.class, remap = false)
public abstract class TrinketFeatureRendererMixin {
	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
			at = @At("HEAD"), cancellable = true, remap = false)
	private void sorryEmiButThisMustBeDone(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
			LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
			float headPitch, CallbackInfo ci) {
		if (GrayTomorrowComponents.get(GrayTomorrowComponents.ARMOR_INVISIBLE, entity)) {
			ci.cancel();
		}
	}
}
