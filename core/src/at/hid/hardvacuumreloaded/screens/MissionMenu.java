package at.hid.hardvacuumreloaded.screens;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;
import at.hid.hardvacuumreloaded.missions.Tut0;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MissionMenu implements Screen {

	private Stage stage;
	private Skin skin;
	private Table table;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();
		table.setFillParent(true);
	}

	@Override
	public void show() {
		HardVacuumReloaded.debug(this.getClass().toString(), "creating MissionMenu screen");
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		Gdx.input.setInputProcessor(stage);

		// creating skin
		HardVacuumReloaded.debug(this.getClass().toString(), "creating skin");
		HardVacuumReloaded.assets.update();
		skin = HardVacuumReloaded.assets.get("ui/gui.json", Skin.class);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Label lblHeading = new Label("", skin, "heading.mission." + HardVacuumReloaded.scale);
		lblHeading.setBounds(0, 161 * HardVacuumReloaded.scale, 241 * HardVacuumReloaded.scale, 39 * HardVacuumReloaded.scale);

		Label lblHeadingText = new Label(HardVacuumReloaded.getLangBundle().format("MissionMenu.lblHeadingText.text"), skin, "heading.text");
		lblHeadingText.setBounds(6 * HardVacuumReloaded.scale, 163 * HardVacuumReloaded.scale, 231 * HardVacuumReloaded.scale, 35 * HardVacuumReloaded.scale);
		lblHeadingText.setAlignment(Align.center);

		Label lblMap = new Label("", skin, "map.mission." + HardVacuumReloaded.scale);
		lblMap.setBounds(241 * HardVacuumReloaded.scale, 135 * HardVacuumReloaded.scale, 79 * HardVacuumReloaded.scale, 65);

		Label lblContent = new Label("", skin, "content.mission." + HardVacuumReloaded.scale);
		lblContent.setBounds(0, 0, 241 * HardVacuumReloaded.scale, 161 * HardVacuumReloaded.scale);

		Label lblMenu = new Label("", skin, "menu.mission." + HardVacuumReloaded.scale);
		lblMenu.setBounds(241 * HardVacuumReloaded.scale, 0, 79 * HardVacuumReloaded.scale, 135 * HardVacuumReloaded.scale);

		Label lblCredits = new Label(Integer.toString(HardVacuumReloaded.playerProfile.getCredits()), skin,  "" + HardVacuumReloaded.scale);
		lblCredits.setBounds(252 * HardVacuumReloaded.scale, 37 * HardVacuumReloaded.scale, 58 * HardVacuumReloaded.scale, 16 * HardVacuumReloaded.scale);

		ScrollPane spContent = new ScrollPane(null, skin);
		spContent.setBounds(8 * HardVacuumReloaded.scale, 8 * HardVacuumReloaded.scale, 233 * HardVacuumReloaded.scale, 120 * HardVacuumReloaded.scale);

		ArrayList<String> missions = new ArrayList<String>();
		FileHandle fhMissionsList = null;
		if (!HardVacuumReloaded.playerProfile.isTut0()) {
			fhMissionsList = Gdx.files.internal("missions/missions_tut0.json");
		} else if (!HardVacuumReloaded.playerProfile.isTut1()) {
			fhMissionsList = Gdx.files.internal("missions/missions_tut1.json");
		}
		try {
			JSONObject missionsList = new JSONObject(fhMissionsList.readString());
			for (int i = 0; i < missionsList.length(); i++) {
				JSONObject json = missionsList.getJSONObject(Integer.toString(i));
				missions.add(json.getString("class") + " (" + json.getString("employer") + ")");
			}
		} catch (JSONException e) {
			HardVacuumReloaded.error(this.getClass().toString(), "", e);
		}
		String[] newItems = new String[missions.size()];
		newItems = missions.toArray(newItems);
		final List<String> listMissions = new List<String>(skin);
		listMissions.setItems(newItems);
		listMissions.setSelectedIndex(-1);
		spContent.setWidget(listMissions);

		ImageButton ibtnExit = new ImageButton(skin, "exit");
		ibtnExit.setBounds(200 * HardVacuumReloaded.scale, 132 * HardVacuumReloaded.scale, 38 * HardVacuumReloaded.scale, 28 * HardVacuumReloaded.scale);
		ibtnExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!HardVacuumReloaded.playerProfile.isOnMission() || !HardVacuumReloaded.playerProfile.isTut0()) {
					DialogSmall dialogExit = new DialogSmall(skin) {
						@Override
						public void result() {
							Gdx.app.exit();
						}
					};
					dialogExit.text("Are you sure?");
					dialogExit.show(stage);
				} else {
					((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
				}
			}
		});

		ImageButton ibtnInfo = new ImageButton(skin, "menu.info");
		ibtnInfo.setBounds(258 * HardVacuumReloaded.scale, 20 * HardVacuumReloaded.scale, 23 * HardVacuumReloaded.scale, 18 * HardVacuumReloaded.scale);
		ibtnInfo.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (listMissions.getSelectedIndex() != -1) {
					HardVacuumReloaded.playerProfile.setOldScreen("MissionMenu");
					((Game) Gdx.app.getApplicationListener()).setScreen(new MissionDetail(listMissions.getSelectedIndex()));
				}
			}
		});

		ImageButton ibtnBuy = new ImageButton(skin, "menu.buy");
		ibtnBuy.setBounds(281 * HardVacuumReloaded.scale, 20 * HardVacuumReloaded.scale, 23 * HardVacuumReloaded.scale, 18 * HardVacuumReloaded.scale);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnBuy.setDisabled(true);
		} else {
			ibtnBuy.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					HardVacuumReloaded.playerProfile.setOldScreen("MissionMenu");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Buy());
					//					((Game) Gdx.app.getApplicationListener()).setScreen(new Ifc2());
				}
			});
		}

		ImageButton ibtnStats = new ImageButton(skin, "menu.stats");
		ibtnStats.setBounds(258 * HardVacuumReloaded.scale, 2 * HardVacuumReloaded.scale, 23 * HardVacuumReloaded.scale, 18 * HardVacuumReloaded.scale);
		ibtnStats.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("MissionMenu");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Stats());
			}
		});

		ImageButton ibtnOptions = new ImageButton(skin, "menu.options");
		ibtnOptions.setBounds(281 * HardVacuumReloaded.scale, 2 * HardVacuumReloaded.scale, 23 * HardVacuumReloaded.scale, 18 * HardVacuumReloaded.scale);
		ibtnOptions.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("MissionMenu");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Options());
			}
		});

		ImageButton ibtnEnterMission = new ImageButton(skin, "enterMission");
		ibtnEnterMission.setBounds(4 * HardVacuumReloaded.scale, 132 * HardVacuumReloaded.scale, 94 * HardVacuumReloaded.scale, 27 * HardVacuumReloaded.scale);
		if (HardVacuumReloaded.playerProfile.isOnMission() && HardVacuumReloaded.playerProfile.isTut0()) {
			ibtnEnterMission.setDisabled(true);
		} else {
			ibtnEnterMission.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (listMissions.getSelectedIndex() != -1) {
						FileHandle fhMissionsList = null;
						JSONObject json = null;
						if (!HardVacuumReloaded.playerProfile.isTut0()) {
							fhMissionsList = Gdx.files.internal("missions/missions_tut0.json");
						} else if (!HardVacuumReloaded.playerProfile.isTut1()) {
							fhMissionsList = Gdx.files.internal("missions/missions_tut1.json");
						}
						try {
							JSONObject missionsList = new JSONObject(fhMissionsList.readString());
							json = missionsList.getJSONObject(Integer.toString(listMissions.getSelectedIndex()));
							HardVacuumReloaded.gameProfile.setActiveMission(json.getString("id"));
							HardVacuumReloaded.gameProfile.saveProfile();
						} catch (JSONException e) {
							HardVacuumReloaded.error(this.getClass().toString(), "", e);
						}
						
						if (HardVacuumReloaded.playerProfile.isTut0()) {
							((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
						} else {
							new Tut0(stage, skin);
						}
					}
				}
			});
		}

		if (HardVacuumReloaded.DEBUG) {
			lblHeading.debug();
			lblHeadingText.debug();
			lblMap.debug();
			lblContent.debug();
			lblCredits.debug();
			lblMenu.debug();
			ibtnExit.debug();
			ibtnInfo.debug();
			ibtnBuy.debug();
			ibtnStats.debug();
			ibtnOptions.debug();
			ibtnEnterMission.debug();
			spContent.debug();
			listMissions.debug();
		}
		stage.addActor(lblHeading);
		stage.addActor(lblHeadingText);
		stage.addActor(lblMap);
		stage.addActor(lblContent);
		stage.addActor(lblMenu);
		stage.addActor(lblCredits);
		stage.addActor(spContent);
		stage.addActor(ibtnExit);
		stage.addActor(ibtnInfo);
		stage.addActor(ibtnBuy);
		stage.addActor(ibtnStats);
		stage.addActor(ibtnOptions);
		stage.addActor(ibtnEnterMission);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		HardVacuumReloaded.debug(this.getClass().toString(), "cleaning up MissionMenu screen");
		stage.dispose();
	}

}
