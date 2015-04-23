package at.hid.hardvacuumreloaded;

import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;

public class PlayerProfile {
	private boolean onMission = false, tut0 = false, tut1 = false, tut2 = false, tut3 = false;
	private int credits = 0;
	private String oldScreen = "";

	public boolean isTut0() {
		return tut0;
	}

	public void setTut0(boolean tut0) {
		this.tut0 = tut0;
		saveProfile();
	}

	public boolean isTut1() {
		return tut1;
	}

	public void setTut1(boolean tut1) {
		this.tut1 = tut1;
		saveProfile();
	}

	public boolean isTut2() {
		return tut2;
	}

	public void setTut2(boolean tut2) {
		this.tut2 = tut2;
		saveProfile();
	}

	public boolean isTut3() {
		return tut3;
	}

	public void setTut3(boolean tut3) {
		this.tut3 = tut3;
		saveProfile();
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
		saveProfile();
	}

	public boolean isOnMission() {
		return onMission;
	}
	
	public void setOnMission(boolean onMission) {
		this.onMission = onMission;
		saveProfile();
	}

	public String getOldScreen() {
		return oldScreen;
	}

	public void setOldScreen(String oldScreen) {
		this.oldScreen = oldScreen;
		saveProfile();
	}
	
	public boolean saveProfile() {
		FileHandle fhPlayerProfile = null;
		if (Gdx.files.isExternalStorageAvailable()) {
			fhPlayerProfile = Gdx.files.external(".hardvacuumreloaded/playerprofile.dat");
		} else {
			fhPlayerProfile = Gdx.files.local(".hardvacuumreloaded/playerprofile.dat");
		}
		JSONObject json = new JSONObject();
		try {
			json.put("onMission", isOnMission());
			json.put("tut0", isTut0());
			json.put("tut1", isTut1());
			json.put("tut2", isTut2());
			json.put("tut3", isTut3());
			json.put("credits", getCredits());
			json.put("oldScreen", getOldScreen());
			String profileAsText = json.toString();
			String encodedProfile = Base64Coder.encodeString(profileAsText);
			fhPlayerProfile.writeString(encodedProfile, false, "UTF-8");
		} catch (Exception e) {
			HardVacuumReloaded.error(this.getClass().toString(), "error creating PlayerProfile JSONObject", e);
			return false;
		}
		return true;
	}
	
	public boolean loadProfile() {
		FileHandle fhPlayerProfile = null;
		if (Gdx.files.isExternalStorageAvailable()) {
			fhPlayerProfile = Gdx.files.external(".hardvacuumreloaded/playerprofile.dat");
		} else {
			fhPlayerProfile = Gdx.files.local(".hardvacuumreloaded/playerprofile.dat");
		}
		try {
			String encodedProfile = fhPlayerProfile.readString("UTF-8");
			String profileAsText = Base64Coder.decodeString(encodedProfile);
			JSONObject json = new JSONObject(profileAsText);
			setOnMission(json.getBoolean("onMission"));
			setTut0(json.getBoolean("tut0"));
			setTut1(json.getBoolean("tut1"));
			setTut2(json.getBoolean("tut2"));
			setTut3(json.getBoolean("tut3"));
			setCredits(json.getInt("credits"));
			setOldScreen(json.getString("oldScreen"));
			return true;
		} catch (Exception e) {
			HardVacuumReloaded.error(this.getClass().toString(), "error creating PlayerProfile JSONObject", e);
			return false;
		}
	}
}
