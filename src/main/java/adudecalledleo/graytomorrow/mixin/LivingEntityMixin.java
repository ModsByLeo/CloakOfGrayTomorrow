package adudecalledleo.graytomorrow.mixin;

import java.util.Map;

import adudecalledleo.graytomorrow.CloakStatusEffects;
import adudecalledleo.graytomorrow.duck.LivingEntityExtensions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityExtensions {
	@SuppressWarnings("WrongEntityDataParameterClass")
	@Unique
	private static final TrackedData<Boolean> ARMOR_INVISIBLE =
			DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private LivingEntityMixin() {
		//noinspection ConstantConditions
		super(null, null);
		throw new IllegalStateException("Mixin ctor called???");
	}

	@Override
	public boolean graytomorrow$isArmorInvisible() {
		return this.dataTracker.get(ARMOR_INVISIBLE);
	}

	@Override
	public void graytomorrow$setArmorInvisible(boolean armorInvisible) {
		this.dataTracker.set(ARMOR_INVISIBLE, armorInvisible);
	}

	@Inject(method = "initDataTracker",
	at = @At("TAIL"))
	private void initArmorInvisible(CallbackInfo ci) {
		this.dataTracker.startTracking(ARMOR_INVISIBLE, false);
	}

	@Inject(method = "updatePotionVisibility",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setInvisible(Z)V",
					ordinal = 0, shift = At.Shift.AFTER))
	private void resetArmorVisibility(CallbackInfo ci) {
		this.graytomorrow$setArmorInvisible(false);
	}

	@Redirect(method = "updatePotionVisibility",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setInvisible(Z)V",
					ordinal = 1))
	private void updateArmorVisibility(LivingEntity instance, boolean hasInvisibility) {
		boolean hasChameleon = instance.hasStatusEffect(CloakStatusEffects.CHAMELEON);
		instance.setInvisible(hasInvisibility || hasChameleon);
		((LivingEntityExtensions) instance).graytomorrow$setArmorInvisible(hasChameleon);
	}

	@Inject(method = "getArmorVisibility", at = @At("HEAD"), cancellable = true)
	private void forceNoArmorVisibility(CallbackInfoReturnable<Float> cir) {
		if (this.graytomorrow$isArmorInvisible()) {
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
