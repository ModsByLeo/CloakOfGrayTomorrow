package adudecalledleo.graytomorrow.mixin;

import java.util.Iterator;

import adudecalledleo.graytomorrow.GrayTomorrowStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.MilkBucketItem;

@Mixin(MilkBucketItem.class)
public abstract class MilkBucketItemMixin {
	@Redirect(method = "finishUsing", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
	private boolean graytomorrow$excludeOurStatusEffects(LivingEntity instance) {
		boolean removedAny = false;
		Iterator<StatusEffectInstance> effectIt = instance.getStatusEffects().iterator();
		while (effectIt.hasNext()) {
			var effect = effectIt.next();
			if (!GrayTomorrowStatusEffects.ALL.contains(effect.getEffectType())) {
				effect.getEffectType().onRemoved(instance, instance.getAttributes(), effect.getAmplifier());
				effectIt.remove();
				removedAny = true;
			}
		}
		return removedAny;
	}
}
