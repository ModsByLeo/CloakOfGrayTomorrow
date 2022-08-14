package adudecalledleo.graytomorrow.mixin.client;

import adudecalledleo.graytomorrow.GrayTomorrowComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {
	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
			at = @At("HEAD"), cancellable = true)
	private void imNotNakedMyClothesAreJustInvisible(MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int i, LivingEntity entity, float f, float g, float h, float j,
			float k, float l, CallbackInfo ci) {
		if (GrayTomorrowComponents.get(GrayTomorrowComponents.ARMOR_INVISIBLE, entity)) {
			ci.cancel();
		}
	}
}
