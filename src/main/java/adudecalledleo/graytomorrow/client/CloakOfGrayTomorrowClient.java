package adudecalledleo.graytomorrow.client;

import adudecalledleo.graytomorrow.CloakNetworking;
import adudecalledleo.graytomorrow.CloakOfGrayTomorrow;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBind;
import net.minecraft.client.world.ClientWorld;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientWorldTickEvents;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public final class CloakOfGrayTomorrowClient implements ClientModInitializer, ClientWorldTickEvents.End {
	public static final KeyBind keyToggleHood = KeyBindingHelper.registerKeyBinding(
			new KeyBind(CloakOfGrayTomorrow.KEY_TOGGLE_HOOD_ID, GLFW.GLFW_KEY_V, KeyBind.GAMEPLAY_CATEGORY));

	@Override
	public void onInitializeClient(ModContainer mod) {}

	@Override
	public void endWorldTick(MinecraftClient client, ClientWorld world) {
		if (keyToggleHood.wasPressed()) {
			ClientPlayNetworking.send(CloakNetworking.C2S_TOGGLE_CLOAK_HOOD, PacketByteBufs.empty());
		}
	}
}
