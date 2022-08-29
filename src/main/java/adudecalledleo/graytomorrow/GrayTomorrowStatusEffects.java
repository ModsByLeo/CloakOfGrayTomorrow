package adudecalledleo.graytomorrow;

import java.util.Set;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;

public final class GrayTomorrowStatusEffects {
	public static final StatusEffect CHAMELEON = new ChameleonStatusEffect(StatusEffectType.BENEFICIAL, StatusEffects.INVISIBILITY.getColor());
	public static final StatusEffect TRUE_BLINDNESS = new StatusEffect(StatusEffectType.HARMFUL, StatusEffects.BLINDNESS.getColor()) {};

	public static final Set<StatusEffect> ALL = Set.of(CHAMELEON, TRUE_BLINDNESS);

	public static final int CHAMELEON_LENGTH = 20 * 30; // 30 seconds

	private GrayTomorrowStatusEffects() {}

	public static boolean applyChameleon(LivingEntity entity) {
		if (!entity.hasStatusEffect(TRUE_BLINDNESS)) {
			return entity.addStatusEffect(new StatusEffectInstance(CHAMELEON,
					CHAMELEON_LENGTH, 0, false, false, true));
		}
		return false;
	}

	public static StatusEffectInstance createTrueBlindness(@Nullable StatusEffectInstance chameleonEffect) {
		int duration = CHAMELEON_LENGTH;
		if (chameleonEffect != null) {
			duration -= chameleonEffect.getDuration();
		}
		return new StatusEffectInstance(TRUE_BLINDNESS, duration, 0, false, false, true);
	}

	public static boolean removeChameleonAndApplyTrueBlindness(LivingEntity entity) {
		var chameleonEffect = entity.getStatusEffect(CHAMELEON);
		if (chameleonEffect == null) {
			return false;
		}

		if (entity.addStatusEffect(createTrueBlindness(entity.getStatusEffect(CHAMELEON)))) {
			entity.removeStatusEffect(CHAMELEON);
			return true;
		} else {
			return false;
		}
	}
}
