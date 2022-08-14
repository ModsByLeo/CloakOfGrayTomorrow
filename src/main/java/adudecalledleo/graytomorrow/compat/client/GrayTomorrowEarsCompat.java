package adudecalledleo.graytomorrow.compat.client;

import adudecalledleo.graytomorrow.CloakItem;
import adudecalledleo.graytomorrow.GrayTomorrow;
import com.unascribed.ears.api.EarsFeatureType;
import com.unascribed.ears.api.registry.EarsInhibitorRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public final class GrayTomorrowEarsCompat {
	private GrayTomorrowEarsCompat() {}

	public static void init() {
		EarsInhibitorRegistry.register(GrayTomorrow.NAMESPACE,
				((type, peer) -> shouldInhibit(type, (PlayerEntity) peer)));
	}

	public static boolean shouldInhibit(EarsFeatureType type, PlayerEntity player) {
		var state = CloakItem.getState(player);
		switch (state) {
			case NOT_EQUIPPED:
				return false;
			case HOOD_UP:
				if (type == EarsFeatureType.EARS) {
					return true;
				}
				// fallthrough
			case HOOD_DOWN:
				return type == EarsFeatureType.WINGS || type == EarsFeatureType.CAPE;
			default:
				throw new IllegalStateException("unhandled CloakState " + state);
		}
	}
}
