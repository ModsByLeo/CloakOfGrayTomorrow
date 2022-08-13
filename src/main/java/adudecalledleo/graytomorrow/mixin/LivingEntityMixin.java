package adudecalledleo.graytomorrow.mixin;

import java.util.Map;

import adudecalledleo.graytomorrow.CloakStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow
	@Final
	private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

	@Inject(method = "hasStatusEffect",
			at = @At("HEAD"), cancellable = true)
	private void checkTrueBlindness(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
		if (effect == StatusEffects.BLINDNESS) {
			cir.setReturnValue(this.activeStatusEffects.containsKey(CloakStatusEffects.TRUE_BLINDNESS)
					|| this.activeStatusEffects.containsKey(StatusEffects.BLINDNESS));
		}
	}
}
