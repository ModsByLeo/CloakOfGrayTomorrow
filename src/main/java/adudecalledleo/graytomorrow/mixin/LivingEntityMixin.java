package adudecalledleo.graytomorrow.mixin;

import java.util.Map;

import adudecalledleo.graytomorrow.GrayTomorrowComponents;
import adudecalledleo.graytomorrow.GrayTomorrowStatusEffects;
import adudecalledleo.graytomorrow.LivingEntityExtensions;
import adudecalledleo.graytomorrow.util.BooleanComponent;
import adudecalledleo.graytomorrow.ChameleonStartingDurationComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityExtensions {
	@SuppressWarnings("ConstantConditions")
	private LivingEntityMixin() {
		super(null, null);
		throw new IllegalStateException("Mixin ctor called?!");
	}

	@Shadow
	@Final
	private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

	@Shadow
	public abstract boolean hasStatusEffect(StatusEffect effect);

	@Inject(method = "updatePotionVisibility",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setInvisible(Z)V",
					ordinal = 0, shift = At.Shift.AFTER))
	private void graytomorrow$resetEquipmentVisibility(CallbackInfo ci) {
		BooleanComponent.set(GrayTomorrowComponents.EQUIPMENT_INVISIBLE, this, false);
	}

	@Inject(method = "updatePotionVisibility",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setInvisible(Z)V",
					ordinal = 1, shift = At.Shift.AFTER))
	private void graytomorrow$updateEquipmentVisibility(CallbackInfo ci) {
		boolean hasChameleon = hasStatusEffect(GrayTomorrowStatusEffects.CHAMELEON);
		if (hasChameleon) {
			setInvisible(true);
		}
		BooleanComponent.set(GrayTomorrowComponents.EQUIPMENT_INVISIBLE, this, hasChameleon);
	}

	@Inject(method = "getArmorVisibility", at = @At("HEAD"), cancellable = true)
	private void graytomorrow$forceNoArmorVisibility(CallbackInfoReturnable<Float> cir) {
		if (BooleanComponent.get(GrayTomorrowComponents.EQUIPMENT_INVISIBLE, this)) {
			cir.setReturnValue(0.0F);
		}
	}

	@Inject(method = "hasStatusEffect",
			at = @At("HEAD"), cancellable = true)
	private void graytomorrow$checkTrueBlindness(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
		if (effect == StatusEffects.BLINDNESS) {
			cir.setReturnValue(this.activeStatusEffects.containsKey(GrayTomorrowStatusEffects.TRUE_BLINDNESS)
					|| this.activeStatusEffects.containsKey(StatusEffects.BLINDNESS));
		}
	}

	@Unique
	private int chameleonCurrentDuration;

	@Override
	public int graytomorrow$getChameleonCurrentDuration() {
		return chameleonCurrentDuration;
	}

	@Inject(method = "onStatusEffectApplied", at = @At("HEAD"))
	private void graytommorrow$recordChameleonStartingDurationOnApply(StatusEffectInstance effect, Entity source, CallbackInfo ci) {
		if (!this.world.isClient && effect.getEffectType() == GrayTomorrowStatusEffects.CHAMELEON) {
			ChameleonStartingDurationComponent.set(this, effect.getDuration());
		}
	}

	@Inject(method = "onStatusEffectUpgraded", at = @At("HEAD"))
	private void graytommorrow$recordChameleonStartingDurationOnUpgrade(StatusEffectInstance effect, boolean reapplyEffect, Entity source, CallbackInfo ci) {
		if (!this.world.isClient && effect.getEffectType() == GrayTomorrowStatusEffects.CHAMELEON) {
			ChameleonStartingDurationComponent.set(this, effect.getDuration());
		}
	}

	@Inject(method = "onStatusEffectRemoved", at = @At("HEAD"))
	private void graytomorrow$recordChameleonCurrentDurationOnRemove(StatusEffectInstance effect, CallbackInfo ci) {
		if (!this.world.isClient && effect.getEffectType() == GrayTomorrowStatusEffects.CHAMELEON) {
			this.chameleonCurrentDuration = effect.getDuration();
		}
	}
}
