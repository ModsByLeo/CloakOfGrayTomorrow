package adudecalledleo.graytomorrow.compat.client;

import adudecalledleo.graytomorrow.CloakItem;
import adudecalledleo.graytomorrow.GrayTomorrow;
import adudecalledleo.graytomorrow.client.GrayTomorrowClient;
import adudecalledleo.graytomorrow.client.GrayTomorrowEarsAccessor;
import com.unascribed.ears.api.EarsAnchorPart;
import com.unascribed.ears.api.EarsFeatureType;
import com.unascribed.ears.api.features.EarsFeatures;
import com.unascribed.ears.api.registry.EarsInhibitorRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public final class GrayTomorrowEarsCompat implements GrayTomorrowEarsAccessor {
	private GrayTomorrowEarsCompat() {}

	public static void init() {
		GrayTomorrowClient.earsAccess = new GrayTomorrowEarsCompat();

		EarsInhibitorRegistry.register(GrayTomorrow.NAMESPACE,
				((type, peer) -> shouldInhibit(type, (PlayerEntity) peer)));
	}

	public static boolean shouldInhibit(EarsFeatureType type, PlayerEntity player) {
		var state = CloakItem.getState(player);
		switch (state) {
			case NOT_EQUIPPED:
				return false;
			case HOOD_UP:
				if (type != EarsFeatureType.SNOUT && type.isAnchoredTo(EarsAnchorPart.HEAD)) {
					return true;
				}
				// fallthrough
			case HOOD_DOWN:
				return type != EarsFeatureType.TAIL && type.isAnchoredTo(EarsAnchorPart.TORSO);
			default:
				throw new IllegalStateException("unhandled CloakState " + state);
		}
	}

	@Override
	public float getChestSize(PlayerEntity player) {
		return EarsFeatures.getById(player.getGameProfile().getId()).chestSize;
	}
}
