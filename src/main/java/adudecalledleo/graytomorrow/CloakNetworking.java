package adudecalledleo.graytomorrow;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public final class CloakNetworking {
	private CloakNetworking() {}

	public static final Identifier C2S_TOGGLE_CLOAK_HOOD = CloakOfGrayTomorrow.id("toggle_cloak_hood");

	public static void register() {
		ServerPlayNetworking.registerGlobalReceiver(C2S_TOGGLE_CLOAK_HOOD, CloakNetworking::handleToggleCloakHood);
	}

	private static void handleToggleCloakHood(MinecraftServer server, ServerPlayerEntity player,
			ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		server.execute(() -> {
			if (CloakItem.isEquipped(player)) {
				if (CloakHoodComponent.KEY.get(player).toggleHoodUp()) {
					CloakItem.applyChameleon(player);
				} else if (player.hasStatusEffect(CloakStatusEffects.CHAMELEON)) {
					CloakItem.removeChameleonAndApplyTrueBlindness(player);
				}
			}
		});
	}
}
