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
		if (instance.world.isClient) {
			return false;
		} else {
			boolean removedAny = false;
			var access = (LivingEntityAccessor) instance;
			Iterator<StatusEffectInstance> effectIt = access.getActiveStatusEffects().values().iterator();
			while (effectIt.hasNext()) {
				var effect = effectIt.next();
				if (!GrayTomorrowStatusEffects.ALL.contains(effect.getEffectType())) {
					access.callOnStatusEffectRemoved(effect);
					effectIt.remove();
					removedAny = true;
				}
			}
			return removedAny;
		}
	}
}
