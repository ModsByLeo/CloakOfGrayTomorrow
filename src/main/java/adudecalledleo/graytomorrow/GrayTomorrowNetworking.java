package adudecalledleo.graytomorrow;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public final class GrayTomorrowNetworking {
	private GrayTomorrowNetworking() {}

	public static final Identifier C2S_TOGGLE_CLOAK_HOOD = GrayTomorrow.id("toggle_cloak_hood");

	public static void register() {
		ServerPlayNetworking.registerGlobalReceiver(C2S_TOGGLE_CLOAK_HOOD, GrayTomorrowNetworking::handleToggleCloakHood);
	}

	private static void handleToggleCloakHood(MinecraftServer server, ServerPlayerEntity player,
			ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		server.execute(() -> {
			if (CloakItem.isEquipped(player)) {
				if (GrayTomorrowComponents.HOOD_UP.get(player).toggleValue()) {
					player.sendMessage(
							Text.translatable("message." + GrayTomorrow.NAMESPACE + ".hood_up"),
							true);
					GrayTomorrowStatusEffects.applyChameleon(player);
				} else {
					player.sendMessage(
							Text.translatable("message." + GrayTomorrow.NAMESPACE + ".hood_down"),
							true);
					if (player.hasStatusEffect(GrayTomorrowStatusEffects.CHAMELEON)) {
						GrayTomorrowStatusEffects.removeChameleonAndApplyTrueBlindness(player);
					}
				}
				GrayTomorrowComponents.HOOD_UP.sync(player);
			}
		});
	}
}