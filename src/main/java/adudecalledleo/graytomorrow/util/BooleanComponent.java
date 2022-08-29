package adudecalledleo.graytomorrow.util;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class BooleanComponent implements AutoSyncedComponent {
	public static boolean get(ComponentKey<BooleanComponent> key, Object provider) {
		return key.maybeGet(provider).map(BooleanComponent::getValue).orElse(false);
	}

	public static void set(ComponentKey<BooleanComponent> key, Object provider, boolean value) {
		var comp = key.get(provider);
		comp.setValue(value);
		key.sync(provider);
	}

	public static boolean toggle(ComponentKey<BooleanComponent> key, Object provider) {
		var comp = key.get(provider);
		boolean ret = comp.toggleValue();
		key.sync(provider);
		return ret;
	}

	private boolean value;

	public BooleanComponent(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return this.value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public boolean toggleValue() {
		this.value = !this.value;
		return this.value;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		tag.putBoolean("Value", this.value);
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		this.value = tag.getBoolean("Value");
	}

	@Override
	public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
		buf.writeBoolean(this.value);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void applySyncPacket(PacketByteBuf buf) {
		this.value = buf.readBoolean();
	}
}
