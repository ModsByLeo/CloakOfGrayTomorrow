package adudecalledleo.graytomorrow.mixin.client;

import adudecalledleo.graytomorrow.client.ReplacementTrinketFeatureRenderer;
import dev.emi.trinkets.TrinketFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> implements FeatureRendererContext<T, M> {
	@ModifyArg(method = "addFeature", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private Object graytomorrow$replaceTrinketFeatureRenderer(Object original) {
		if (original.getClass() == TrinketFeatureRenderer.class) {
			return new ReplacementTrinketFeatureRenderer<>(this);
		}
		return original;
	}
}
