package adudecalledleo.graytomorrow.client;

import adudecalledleo.graytomorrow.GrayTomorrowComponents;
import adudecalledleo.graytomorrow.util.BooleanComponent;
import dev.emi.trinkets.TrinketFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public final class ReplacementTrinketFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends TrinketFeatureRenderer<T, M> {
	public ReplacementTrinketFeatureRenderer(FeatureRendererContext<T, M> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity,
			float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
			float headPitch) {
		if (BooleanComponent.get(GrayTomorrowComponents.EQUIPMENT_INVISIBLE, entity)) {
			return;
		}

		super.render(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
	}
}
