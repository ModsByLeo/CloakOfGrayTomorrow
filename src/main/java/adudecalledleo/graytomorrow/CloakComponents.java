package adudecalledleo.graytomorrow;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

import net.minecraft.entity.LivingEntity;

public final class CloakComponents implements EntityComponentInitializer {
	public static final ComponentKey<BooleanComponent> HOOD_UP =
			ComponentRegistry.getOrCreate(CloakOfGrayTomorrow.id("hood_up"), BooleanComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(LivingEntity.class, HOOD_UP)
				.respawnStrategy(RespawnCopyStrategy.LOSSLESS_ONLY)
				.end(entity -> new BooleanComponent(false));
	}
}
