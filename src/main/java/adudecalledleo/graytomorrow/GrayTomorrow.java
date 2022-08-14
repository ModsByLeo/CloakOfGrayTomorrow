package adudecalledleo.graytomorrow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public final class GrayTomorrow implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Cloak of Gray Tomorrow");

	public static final String NAMESPACE = "graytomorrow";

	public static Identifier id(String path) {
		return new Identifier(NAMESPACE, path);
	}

	public static final CloakItem CLOAK_ITEM = new CloakItem(new Item.Settings().group(ItemGroup.COMBAT));

	public static final String KEY_TOGGLE_HOOD_ID = "key." + NAMESPACE + ".toggleHood";

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("2 YEARS");

		Registry.register(Registry.ITEM, id("cloak"), CLOAK_ITEM);

		Registry.register(Registry.STATUS_EFFECT, id("chameleon"), GrayTomorrowStatusEffects.CHAMELEON);
		Registry.register(Registry.STATUS_EFFECT, id("true_blindness"), GrayTomorrowStatusEffects.TRUE_BLINDNESS);

		GrayTomorrowNetworking.register();
	}
}
