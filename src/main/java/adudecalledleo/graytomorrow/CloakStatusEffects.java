package adudecalledleo.graytomorrow;

import java.util.Set;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;

public final class CloakStatusEffects {
	// TODO colors
	public static final StatusEffect CHAMELEON = new ChameleonStatusEffect(StatusEffectType.BENEFICIAL, 0xFFFFFF);
	public static final StatusEffect TRUE_BLINDNESS = new StatusEffect(StatusEffectType.HARMFUL, 0x00000) {};

	public static final Set<StatusEffect> ALL = Set.of(CHAMELEON, TRUE_BLINDNESS);

	public static final int CHAMELEON_LENGTH = 20 * 60; // 1 minute

	private CloakStatusEffects() {}

	public static void applyChameleon(LivingEntity entity) {
		if (!entity.hasStatusEffect(TRUE_BLINDNESS)) {
			entity.addStatusEffect(new StatusEffectInstance(CHAMELEON,
					CHAMELEON_LENGTH, 0, false, false, true));
		}
	}

	public static StatusEffectInstance createTrueBlindness(@Nullable StatusEffectInstance chameleonInstance) {
		int duration = CHAMELEON_LENGTH;
		if (chameleonInstance != null) {
			duration -= chameleonInstance.getDuration();
		}
		return new StatusEffectInstance(TRUE_BLINDNESS, duration, 0, false, false, true);
	}

	public static void removeChameleonAndApplyTrueBlindness(LivingEntity entity) {
		entity.addStatusEffect(createTrueBlindness(entity.getStatusEffect(CHAMELEON)));
		entity.removeStatusEffect(CHAMELEON);
	}
}
