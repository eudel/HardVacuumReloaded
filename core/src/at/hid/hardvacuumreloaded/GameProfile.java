package at.hid.hardvacuumreloaded;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import at.hid.hardvacuumreloaded.entities.Mine;
import at.hid.hardvacuumreloaded.entities.Miner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Base64Coder;

public class GameProfile {
	private String activeMission = null, map = null;
	private ArrayList<Object> entities = new ArrayList<Object>();

	public String getActiveMission() {
		return activeMission;
	}

	public void setActiveMission(String activeMission) {
		this.activeMission = activeMission;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public ArrayList<Object> getEntities() {
		return entities;
	}

	public Object getEntity(int id) {
		return entities.get(id);
	}

	public Miner getMiner(int id) {
		Miner miner = (Miner) entities.get(id);
		return miner;
	}

	public Mine getMine(int id) {
		Mine mine = (Mine) entities.get(id);
		return mine;
	}

	public void setEntities(ArrayList<Object> entities) {
		this.entities = entities;
	}

	public void setEntity(int pos, Object entity) {
		entities.set(pos, entity);
	}

	public boolean saveProfile() {
		FileHandle fhGameProfile = null;
		if (Gdx.files.isExternalStorageAvailable()) {
			fhGameProfile = Gdx.files.external(".hardvacuumreloaded/gameprofile.dat");
		} else {
			fhGameProfile = Gdx.files.local(".hardvacuumreloaded/gameprofile.dat");
		}
		JSONObject json = new JSONObject();
		try {
			json.put("activeMission", getActiveMission());
			json.put("map", getMap());
			JSONArray jsonEntities = new JSONArray();
			for (int i = 0; i < entities.size(); i++) {
				JSONObject jsonEntity = new JSONObject();
				if (entities.get(i).getClass().equals(Miner.class)) {
					Miner miner = (Miner) entities.get(i);
					jsonEntity.put("class", "miner");
					jsonEntity.put("isSelected", miner.isSelected());
					jsonEntity.put("x", miner.getX());
					jsonEntity.put("y", miner.getY());
					jsonEntity.put("hasTarget", miner.hasTarget());
					jsonEntity.put("targetX", miner.getTargetX());
					jsonEntity.put("targetY", miner.getTargetY());
					jsonEntity.put("direction", miner.getDirection());
				} else if (entities.get(i).getClass().equals(Mine.class)) {
					Mine mine = (Mine) entities.get(i);
					jsonEntity.put("class", "mine");
					jsonEntity.put("isSelected", mine.isSelected());
					jsonEntity.put("x", mine.getX());
					jsonEntity.put("y", mine.getY());
					jsonEntity.put("visible", mine.isVisible());
				}
				jsonEntities.put(jsonEntity);
			}
			json.put("entities", jsonEntities);
			String profileAsText = json.toString();
			String encodedProfile = profileAsText;
			//			String encodedProfile = Base64Coder.encodeString(profileAsText);
			fhGameProfile.writeString(encodedProfile, false, "UTF-8");
			return true;
		} catch (Exception e) {
			HardVacuumReloaded.error(this.getClass().toString(), "error creating GameProfile JSONObject", e);
			return false;
		}
	}

	public GameProfile loadProfile() {
		FileHandle fhGameProfile = null;
		GameProfile gameProfile = null;
		if (Gdx.files.isExternalStorageAvailable()) {
			fhGameProfile = Gdx.files.external(".hardvacuumreloaded/gameprofile.dat");
		} else {
			fhGameProfile = Gdx.files.local(".hardvacuumreloaded/gameprofile.dat");
		}
		try {
			String encodedProfile = fhGameProfile.readString("UTF-8");
			String profileAsText = encodedProfile;
//			String profileAsText = Base64Coder.decodeString(encodedProfile);
			JSONObject json = new JSONObject(profileAsText);

			if (json.has("activeMission"))
				setActiveMission(json.getString("activeMission"));

			if (json.has("map"))
				setMap(json.get("map").toString());

			if (json.has("entities")) {
				JSONArray jsonEntities = new JSONArray(json.getString("entities"));
				for (int i = 0; i < jsonEntities.length(); i++) {
					JSONObject jsonEntity = jsonEntities.getJSONObject(i);
					if (jsonEntity.getString("class").equals("miner")) {
						Sprite iconSelected = new Sprite(Assets.selectedIcon);
						iconSelected.setX(jsonEntity.getInt("x"));
						iconSelected.setY(jsonEntity.getInt("y") + 50);
						iconSelected.setScale(5);
						TiledMap map = HardVacuumReloaded.assets.get(getMap(), TiledMap.class);
						TextureRegion region = null;
						if (jsonEntity.has("direction")) {
							if (jsonEntity.getString("direction").equals("minerN"))
								region = Assets.minerN;
							else if (jsonEntity.getString("direction").equals("minerNE"))
								region = Assets.minerNE;
							else if (jsonEntity.getString("direction").equals("minerE"))
								region = Assets.minerE;
							else if (jsonEntity.getString("direction").equals("minerSE"))
								region = Assets.minerSE;
							else if (jsonEntity.getString("direction").equals("minerS"))
								region = Assets.minerS;
							else if (jsonEntity.getString("direction").equals("minerSW"))
								region = Assets.minerSW;
							else if (jsonEntity.getString("direction").equals("minerW"))
								region = Assets.minerW;
							else if (jsonEntity.getString("direction").equals("minerNW"))
								region = Assets.minerNW;
						} else {
							region = Assets.minerS;
						}

						Miner miner = new Miner(new Sprite(region), (TiledMapTileLayer) (map.getLayers().get("collision")), iconSelected);
						miner.setX(jsonEntity.getInt("x"));
						miner.setY(jsonEntity.getInt("y"));
						if (jsonEntity.has("isSelected"))
							miner.setSelected(jsonEntity.getBoolean("isSelected"));
						miner.setScale(5);
						if (jsonEntity.has("hasTarget")) {
							miner.setTarget(jsonEntity.getBoolean("hasTarget"));
							miner.setTarget(jsonEntity.getInt("targetX"), jsonEntity.getInt("targetY"));
						}

						entities.add(miner);
					} else if (jsonEntity.getString("class").equals("mine")) {
						Sprite iconSelected = new Sprite(Assets.selectedIcon);
						iconSelected.setX(jsonEntity.getInt("x"));
						iconSelected.setY(jsonEntity.getInt("y") + 50);
						iconSelected.setScale(5);

						Mine mine = new Mine(new Sprite(Assets.mine), iconSelected);
						mine.setX(jsonEntity.getInt("x"));
						mine.setY(jsonEntity.getInt("y"));
						if (jsonEntity.has("isSelected"))
							mine.setSelected(jsonEntity.getBoolean("isSelected"));
						mine.setScale(5);
						if (jsonEntity.getString("visible").equals("false"))
							mine.setAlpha(0);
					}
				}
			}

			return gameProfile;
		} catch (Exception e) {
			HardVacuumReloaded.error(this.getClass().toString(), "error loading GameProfile JSONObject", e);
			return null;
		}
	}
}
