package adudecalledleo.graytomorrow.mixin;

import java.util.Map;

import adudecalledleo.graytomorrow.CloakComponents;
import adudecalledleo.graytomorrow.CloakStatusEffects;
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
	private void resetArmorVisibility(CallbackInfo ci) {
		CloakComponents.ARMOR_INVISIBLE.get(this).setValue(false);
	}

	@Redirect(method = "updatePotionVisibility",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setInvisible(Z)V",
					ordinal = 1))
	private void updateArmorVisibility(LivingEntity instance, boolean hasInvisibility) {
		boolean hasChameleon = instance.hasStatusEffect(CloakStatusEffects.CHAMELEON);
		instance.setInvisible(hasInvisibility || hasChameleon);
		CloakComponents.ARMOR_INVISIBLE.get(this).setValue(hasChameleon);
	}

	@Inject(method = "getArmorVisibility", at = @At("HEAD"), cancellable = true)
	private void forceNoArmorVisibility(CallbackInfoReturnable<Float> cir) {
		if (CloakComponents.get(CloakComponents.ARMOR_INVISIBLE, this)) {
			cir.setReturnValue(0.0F);
		}
	}

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
