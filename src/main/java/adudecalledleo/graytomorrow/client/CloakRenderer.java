package adudecalledleo.graytomorrow.client;

import adudecalledleo.graytomorrow.GrayTomorrow;
import adudecalledleo.graytomorrow.GrayTomorrowComponents;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import org.quiltmc.qsl.resource.loader.api.client.ClientResourceLoaderEvents;

public final class CloakRenderer implements ClientResourceLoaderEvents.EndResourcePackReload, TrinketRenderer {
	public static final CloakRenderer INSTANCE = new CloakRenderer();

	public static final Identifier TEXTURE = GrayTomorrow.id("textures/models/armor/cloak.png");
	public static final Identifier TEXTURE_HOOD_UP = GrayTomorrow.id("textures/models/armor/cloak_hood_up.png");

	private BipedEntityModel<?> model;

	private CloakRenderer() {}

	@Override
	public void onEndResourcePackReload(MinecraftClient client, ResourceManager resourceManager, boolean first,
			@Nullable Throwable error) {
		if (error == null) {
			var entityModelLoader = client.getEntityModelLoader();
			this.model = new BipedEntityModel<>(entityModelLoader.getModelPart(EntityModelLayers.PLAYER_INNER_ARMOR));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
			MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity,
			float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
			float headPitch) {
		if (contextModel instanceof BipedEntityModel<?> bipedModel) {
			renderCloak((BipedEntityModel<LivingEntity>) bipedModel, (BipedEntityModel<LivingEntity>) this.model,
					matrices, vertexConsumers, light, entity);
		}
	}

	private <T extends LivingEntity> void renderCloak(BipedEntityModel<T> contextModel, BipedEntityModel<T> armorModel,
			MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity) {
		contextModel.setAttributes(armorModel);

		armorModel.setVisible(true);

		var buffer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(
				GrayTomorrowComponents.get(GrayTomorrowComponents.HOOD_UP, entity)
						? TEXTURE_HOOD_UP
						: TEXTURE));

		armorModel.render(matrices, buffer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
	}
}
