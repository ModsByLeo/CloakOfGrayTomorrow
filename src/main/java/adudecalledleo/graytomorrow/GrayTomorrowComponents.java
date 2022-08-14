package adudecalledleo.graytomorrow;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

import net.minecraft.entity.LivingEntity;

public final class GrayTomorrowComponents implements EntityComponentInitializer {
	public static final ComponentKey<BooleanComponent> ARMOR_INVISIBLE =
			ComponentRegistry.getOrCreate(GrayTomorrow.id("armor_invisible"), BooleanComponent.class);
	public static final ComponentKey<BooleanComponent> HOOD_UP =
			ComponentRegistry.getOrCreate(GrayTomorrow.id("hood_up"), BooleanComponent.class);

	public static boolean get(ComponentKey<BooleanComponent> key, Object provider) {
		return key.maybeGet(provider).map(BooleanComponent::getValue).orElse(false);
	}

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(LivingEntity.class, ARMOR_INVISIBLE)
				.respawnStrategy(RespawnCopyStrategy.LOSSLESS_ONLY)
				.end(entity -> new BooleanComponent(false));
		registry.beginRegistration(LivingEntity.class, HOOD_UP)
				.respawnStrategy(RespawnCopyStrategy.LOSSLESS_ONLY)
				.end(entity -> new BooleanComponent(false));
	}
}
