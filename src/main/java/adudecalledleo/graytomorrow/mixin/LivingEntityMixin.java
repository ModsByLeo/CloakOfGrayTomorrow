package adudecalledleo.graytomorrow.mixin;

import java.util.Map;

import adudecalledleo.graytomorrow.GrayTomorrowComponents;
import adudecalledleo.graytomorrow.GrayTomorrowStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(method = "updatePotionVisibility",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setInvisible(Z)V",
					ordinal = 0, shift = At.Shift.AFTER))
	private void graytomorrow$resetArmorVisibility(CallbackInfo ci) {
		GrayTomorrowComponents.ARMOR_INVISIBLE.get(this).setValue(false);
	}

	@Redirect(method = "updatePotionVisibility",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setInvisible(Z)V",
					ordinal = 1))
	private void graytomorrow$updateArmorVisibility(LivingEntity instance, boolean hasInvisibility) {
		boolean hasChameleon = instance.hasStatusEffect(GrayTomorrowStatusEffects.CHAMELEON);
		instance.setInvisible(hasInvisibility || hasChameleon);
		GrayTomorrowComponents.ARMOR_INVISIBLE.get(this).setValue(hasChameleon);
		GrayTomorrowComponents.ARMOR_INVISIBLE.sync(this);
	}

	@Inject(method = "getArmorVisibility", at = @At("HEAD"), cancellable = true)
	private void graytomorrow$forceNoArmorVisibility(CallbackInfoReturnable<Float> cir) {
		if (GrayTomorrowComponents.get(GrayTomorrowComponents.ARMOR_INVISIBLE, this)) {
			cir.setReturnValue(0.0F);
		}
	}

	@Shadow
	@Final
	private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

	@Inject(method = "hasStatusEffect",
			at = @At("HEAD"), cancellable = true)
	private void graytomorrow$checkTrueBlindness(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
		if (effect == StatusEffects.BLINDNESS) {
			cir.setReturnValue(this.activeStatusEffects.containsKey(GrayTomorrowStatusEffects.TRUE_BLINDNESS)
					|| this.activeStatusEffects.containsKey(StatusEffects.BLINDNESS));
		}
	}
}
