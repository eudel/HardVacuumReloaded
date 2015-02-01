package at.hid.hardvacuumreloaded.screens;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;

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
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MainMenu implements Screen {

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
		HardVacuumReloaded.debug(this.getClass().toString(), "creating MainMenu screen");
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		Gdx.input.setInputProcessor(stage);

		// creating skin
		HardVacuumReloaded.debug(this.getClass().toString(), "creating skin");
		HardVacuumReloaded.assets.update();
		skin = HardVacuumReloaded.assets.get("ui/gui.json", Skin.class);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Label lblHeading = new Label("", skin, "heading.mission");
		lblHeading.setBounds(0, 805, 1205, 195);

		Label lblHeadingText = new Label("", skin, "heading.text");
		lblHeadingText.setBounds(30, 815, 1155, 175);

		Label lblMap = new Label("", skin, "map.mission");
		lblMap.setBounds(1205, 675, 395, 325);

		Label lblContent = new Label("", skin, "content.mission");
		lblContent.setBounds(0, 0, 1205, 805);

		Label lblMenu = new Label("", skin, "menu.mission");
		lblMenu.setBounds(1205, 0, 395, 675);

		Label lblCredits = new Label(Integer.toString(HardVacuumReloaded.playerProfile.getCredits()), skin);
		lblCredits.setBounds(1260, 185, 290, 80);

		ScrollPane spContent = new ScrollPane(null, skin);
		spContent.setBounds(40, 40, 1165, 600);

		ArrayList<String> missions = new ArrayList<String>();
		FileHandle fhMissionsList = null;
		if (!HardVacuumReloaded.playerProfile.isTut1()) {
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
		ibtnExit.setBounds(1000, 660, 190, 140);
		ibtnExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				DialogSmall dialogExit = new DialogSmall(skin) {
					@Override
					public void result() {
						Gdx.app.exit();
					}
				};
				dialogExit.text("Are you sure?");
				dialogExit.show(stage);
			}
		});

		ImageButton ibtnInfo = new ImageButton(skin, "menu.info");
		ibtnInfo.setBounds(1290, 100, 115, 90);
		ibtnInfo.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (listMissions.getSelectedIndex() != -1) {
					HardVacuumReloaded.playerProfile.setOldScreen("MainMenu");
					((Game) Gdx.app.getApplicationListener()).setScreen(new MissionDetail(listMissions.getSelectedIndex()));
				}
			}
		});

		ImageButton ibtnBuy = new ImageButton(skin, "menu.buy");
		ibtnBuy.setBounds(1405, 100, 115, 90);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnBuy.setDisabled(true);
		} else {
			ibtnBuy.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					HardVacuumReloaded.playerProfile.setOldScreen("MainMenu");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Buy());
//					((Game) Gdx.app.getApplicationListener()).setScreen(new Ifc2());
				}
			});
		}

		ImageButton ibtnStats = new ImageButton(skin, "menu.stats");
		ibtnStats.setBounds(1290, 10, 115, 90);
		ibtnStats.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("MainMenu");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Stats());
			}
		});

		ImageButton ibtnOptions = new ImageButton(skin, "menu.options");
		ibtnOptions.setBounds(1405, 10, 115, 90);
		ibtnOptions.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("MainMenu");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Options());
			}
		});

		ImageButton ibtnEnterMission = new ImageButton(skin, "enterMission");
		ibtnEnterMission.setBounds(20, 660, 470, 135);
		if (HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnEnterMission.setDisabled(true);
		} else {
			ibtnEnterMission.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

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
		HardVacuumReloaded.debug(this.getClass().toString(), "cleaning up MainMenu screen");
		stage.dispose();
	}

}
