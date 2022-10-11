package adudecalledleo.graytomorrow;

import net.minecraft.entity.LivingEntity;

public interface LivingEntityExtensions {
	static int calculateTrueBlindnessDuration(LivingEntity entity) {
		int chameleonStartingDuration = ChameleonStartingDurationComponent.get(entity);
		int chameleonCurrentDuration = ((LivingEntityExtensions) entity).graytomorrow$getChameleonCurrentDuration();
		return Math.max(0, chameleonStartingDuration - chameleonCurrentDuration);
	}

	int graytomorrow$getChameleonCurrentDuration();
}
