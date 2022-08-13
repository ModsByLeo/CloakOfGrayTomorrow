package adudecalledleo.graytomorrow;

import java.util.Set;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public final class CloakStatusEffects {
	// TODO colors
	public static final StatusEffect CHAMELEON = new ChameleonStatusEffect(StatusEffectType.BENEFICIAL, 0xFFFFFF);
	public static final StatusEffect TRUE_BLINDNESS = new StatusEffect(StatusEffectType.HARMFUL, 0x00000) {};

	public static final Set<StatusEffect> ALL = Set.of(CHAMELEON, TRUE_BLINDNESS);

	public static final int CHAMELEON_LENGTH = 20 * 60; // 1 minute

	private CloakStatusEffects() {}
}
