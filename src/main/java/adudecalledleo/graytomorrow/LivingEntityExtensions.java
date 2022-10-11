package adudecalledleo.graytomorrow;

import net.minecraft.entity.LivingEntity;

public interface LivingEntityExtensions {
	static int calculateTrueBlindnessDuration(LivingEntity entity) {
		return ChameleonStartingDurationComponent.get(entity) - ((LivingEntityExtensions) entity).graytomorrow$getChameleonCurrentDuration();
	}

	int graytomorrow$getChameleonCurrentDuration();
}
