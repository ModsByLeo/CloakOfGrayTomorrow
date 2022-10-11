package adudecalledleo.graytomorrow;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

public final class ChameleonStartingDurationComponent implements Component {
	private int value;

	public ChameleonStartingDurationComponent(int value) {
		this.value = value;
	}

	public static int get(Object provider) {
		return GrayTomorrowComponents.CHAMELEON_STARTING_DURATION.maybeGet(provider)
				.map(ChameleonStartingDurationComponent::getValue).orElse(0);
	}

	public static void set(Object provider, int value) {
		var comp = GrayTomorrowComponents.CHAMELEON_STARTING_DURATION.get(provider);
		comp.setValue(value);
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		this.value = tag.getInt("Value");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("Value", this.value);
	}
}
