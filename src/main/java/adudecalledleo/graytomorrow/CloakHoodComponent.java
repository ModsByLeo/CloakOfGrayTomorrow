package adudecalledleo.graytomorrow;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public final class CloakHoodComponent implements Component, AutoSyncedComponent {
	public static final ComponentKey<CloakHoodComponent> KEY =
			ComponentRegistry.getOrCreate(CloakOfGrayTomorrow.id("cloak_hood"), CloakHoodComponent.class);

	private final PlayerEntity player;
	private boolean hoodUp;

	public CloakHoodComponent(PlayerEntity player) {
		this.player = player;
		this.hoodUp = false;
	}

	public PlayerEntity getPlayer() {
		return this.player;
	}

	public boolean isHoodUp() {
		return this.hoodUp;
	}

	public void setHoodUp(boolean hoodUp) {
		this.hoodUp = hoodUp;
		KEY.sync(this.player);
	}

	public boolean toggleHoodUp() {
		this.hoodUp = !this.hoodUp;
		KEY.sync(this.player);
		return this.hoodUp;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		this.hoodUp = tag.getBoolean("HoodUp");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putBoolean("HoodUp", this.hoodUp);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void applySyncPacket(PacketByteBuf buf) {
		this.hoodUp = buf.readBoolean();
	}

	@Override
	public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
		buf.writeBoolean(this.hoodUp);
	}
}
