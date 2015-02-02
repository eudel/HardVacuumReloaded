package at.hid.hardvacuumreloaded;

public class PlayerProfile {
	private boolean onMission, unitSelected, tut1, tut2, tut3;
	private int credits;
	private String oldScreen;

	public boolean isTut1() {
		return tut1;
	}

	public void setTut1(boolean tut1) {
		this.tut1 = tut1;
	}

	public boolean isTut2() {
		return tut2;
	}

	public void setTut2(boolean tut2) {
		this.tut2 = tut2;
	}

	public boolean isTut3() {
		return tut3;
	}

	public void setTut3(boolean tut3) {
		this.tut3 = tut3;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public boolean isOnMission() {
		return onMission;
	}
	
	public void setOnMission(boolean onMission) {
		this.onMission = onMission;
	}

	public boolean isUnitSelected() {
		return unitSelected;
	}

	public void setUnitSelected(boolean unitSelected) {
		this.unitSelected = unitSelected;
	}

	public String getOldScreen() {
		return oldScreen;
	}

	public void setOldScreen(String oldScreen) {
		this.oldScreen = oldScreen;
	}
	
}
