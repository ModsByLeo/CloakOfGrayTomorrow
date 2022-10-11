package adudecalledleo.graytomorrow;

import adudecalledleo.graytomorrow.util.BooleanComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

import net.minecraft.entity.LivingEntity;

public final class GrayTomorrowComponents implements EntityComponentInitializer {
	public static final ComponentKey<BooleanComponent> EQUIPMENT_INVISIBLE =
			ComponentRegistry.getOrCreate(GrayTomorrow.id("equipment_invisible"), BooleanComponent.class);
	public static final ComponentKey<BooleanComponent> HOOD_UP =
			ComponentRegistry.getOrCreate(GrayTomorrow.id("hood_up"), BooleanComponent.class);
	public static final ComponentKey<ChameleonStartingDurationComponent> CHAMELEON_STARTING_DURATION =
			ComponentRegistry.getOrCreate(GrayTomorrow.id("chameleon_starting_duration"), ChameleonStartingDurationComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(LivingEntity.class, EQUIPMENT_INVISIBLE)
				.respawnStrategy(RespawnCopyStrategy.LOSSLESS_ONLY)
				.end(entity -> new BooleanComponent(false));
		registry.beginRegistration(LivingEntity.class, HOOD_UP)
				.respawnStrategy(RespawnCopyStrategy.LOSSLESS_ONLY)
				.end(entity -> new BooleanComponent(false));
		registry.beginRegistration(LivingEntity.class, CHAMELEON_STARTING_DURATION)
				.respawnStrategy(RespawnCopyStrategy.LOSSLESS_ONLY)
				.end(entity -> new ChameleonStartingDurationComponent(0));
	}
}
