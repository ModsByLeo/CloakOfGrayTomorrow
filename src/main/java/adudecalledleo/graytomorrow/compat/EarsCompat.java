package adudecalledleo.graytomorrow.compat;

import adudecalledleo.graytomorrow.CloakItem;
import adudecalledleo.graytomorrow.CloakOfGrayTomorrow;
import com.unascribed.ears.api.EarsFeatureType;
import com.unascribed.ears.api.registry.EarsInhibitorRegistry;

import net.minecraft.entity.player.PlayerEntity;

public final class EarsCompat {
	private EarsCompat() {}

	public static void init() {
		EarsInhibitorRegistry.register(CloakOfGrayTomorrow.NAMESPACE,
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
