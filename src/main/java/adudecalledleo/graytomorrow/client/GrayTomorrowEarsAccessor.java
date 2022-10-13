package adudecalledleo.graytomorrow.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public interface GrayTomorrowEarsAccessor {
	default float getChestSize(PlayerEntity player) {
		return 0;
	}
}
