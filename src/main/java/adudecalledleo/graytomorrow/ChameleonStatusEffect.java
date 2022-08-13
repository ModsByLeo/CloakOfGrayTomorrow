package adudecalledleo.graytomorrow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class ChameleonStatusEffect extends StatusEffect {
	public ChameleonStatusEffect(StatusEffectType type, int color) {
		super(type, color);
	}

	@Override
	public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onRemoved(entity, attributes, amplifier);
		// NOTE: will not get applied if clearStatusEffects was called
		// this is intentional
		if (!entity.hasStatusEffect(CloakStatusEffects.TRUE_BLINDNESS)) {
			entity.addStatusEffect(CloakStatusEffects.createTrueBlindness(null));
		}
	}
}
