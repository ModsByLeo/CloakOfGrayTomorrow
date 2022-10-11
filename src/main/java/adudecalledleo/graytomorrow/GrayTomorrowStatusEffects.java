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

	private GrayTomorrowStatusEffects() {}

	public static @Nullable StatusEffectInstance createTrueBlindness(LivingEntity entity) {
		int duration = LivingEntityExtensions.calculateTrueBlindnessDuration(entity);
		if (duration <= 0) {
			return null;
		} else {
			return new StatusEffectInstance(TRUE_BLINDNESS, duration, 0, false, false, true);
		}
	}
}
