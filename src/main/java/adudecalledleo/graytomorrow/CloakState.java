package adudecalledleo.graytomorrow;

public enum CloakState {
	NOT_EQUIPPED(false), HOOD_DOWN(true), HOOD_UP(true);

	private final boolean equipped;

	CloakState(boolean equipped) {
		this.equipped = equipped;
	}

	public boolean isEquipped() {
		return equipped;
	}
}
