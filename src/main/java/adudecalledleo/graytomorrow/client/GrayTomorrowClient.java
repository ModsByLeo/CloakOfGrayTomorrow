package adudecalledleo.graytomorrow.client;

import adudecalledleo.graytomorrow.GrayTomorrow;
import adudecalledleo.graytomorrow.GrayTomorrowNetworking;
import adudecalledleo.graytomorrow.compat.client.GrayTomorrowEarsCompat;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBind;
import net.minecraft.client.world.ClientWorld;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientWorldTickEvents;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public final class GrayTomorrowClient implements ClientModInitializer, ClientWorldTickEvents.End {
	public static final Logger LOGGER = LoggerFactory.getLogger("Cloak of Gray Tomorrow|Client");

	public static final KeyBind keyToggleHood =
			new KeyBind(GrayTomorrow.KEY_TOGGLE_HOOD_ID, GLFW.GLFW_KEY_V, KeyBind.GAMEPLAY_CATEGORY);

	@SuppressWarnings("unused")
	public static final GrayTomorrowClient INSTANCE = new GrayTomorrowClient();

	private GrayTomorrowClient() {}

	@Override
	public void onInitializeClient(ModContainer mod) {
		LOGGER.info("[Cloak of Gray Tomorrow|Client] Again, I would like to emphasise: 2 YEARS");

		KeyBindingHelper.registerKeyBinding(keyToggleHood);

		TrinketRendererRegistry.registerRenderer(GrayTomorrow.CLOAK_ITEM, CloakRenderer.INSTANCE);

		if (QuiltLoader.isModLoaded("ears")) {
			GrayTomorrowEarsCompat.init();
		}
	}

	@Override
	public void endWorldTick(MinecraftClient client, ClientWorld world) {
		if (keyToggleHood.wasPressed()) {
			ClientPlayNetworking.send(GrayTomorrowNetworking.C2S_TOGGLE_CLOAK_HOOD, PacketByteBufs.empty());
		}
	}
}
