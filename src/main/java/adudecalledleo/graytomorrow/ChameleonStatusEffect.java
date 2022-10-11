package adudecalledleo.graytomorrow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public final class ChameleonStatusEffect extends StatusEffect {
	public ChameleonStatusEffect(StatusEffectType type, int color) {
		super(type, color);
	}

	@Override
	public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onRemoved(entity, attributes, amplifier);
		// NOTE: will not get applied if clearStatusEffects was called
		// this is intentional
		var trueBlindnessEffect = GrayTomorrowStatusEffects.createTrueBlindness(entity);
		ChameleonStartingDurationComponent.set(entity, 0);
		if (trueBlindnessEffect != null) {
			entity.addStatusEffect(trueBlindnessEffect);
		}
	}
}
