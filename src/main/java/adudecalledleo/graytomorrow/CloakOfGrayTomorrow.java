package adudecalledleo.graytomorrow;

import adudecalledleo.graytomorrow.compat.EarsCompat;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public final class CloakOfGrayTomorrow implements ModInitializer, EntityComponentInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Cloak of Gray Tomorrow");

	public static final String NAMESPACE = "cloak_of_gray_tomorrow";

	public static Identifier id(String path) {
		return new Identifier(NAMESPACE, path);
	}

	public static final String KEY_TOGGLE_HOOD_ID = "key." + NAMESPACE + ".toggleHood";

	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registry.ITEM, id("cloak"), CloakItem.INSTANCE);

		Registry.register(Registry.STATUS_EFFECT, id("chameleon"), CloakStatusEffects.CHAMELEON);
		Registry.register(Registry.STATUS_EFFECT, id("true_blindness"), CloakStatusEffects.TRUE_BLINDNESS);

		LOGGER.info("2 YEARS");

		if (QuiltLoader.isModLoaded("ears")) {
			EarsCompat.init();
		}
	}

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(CloakHoodComponent.KEY, CloakHoodComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
