package at.hid.hardvacuumreloaded.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MissionDetail implements Screen {

	private Stage stage;
	private Skin skin;
	private Table table;
	private int mission;

	public MissionDetail(int mission) {
		this.mission = mission;
	}

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

		FileHandle fhMissionsList = null;
		JSONObject json = null;
		if (!HardVacuumReloaded.playerProfile.isTut0()) {
			fhMissionsList = Gdx.files.internal("missions/missions_tut0.json");
		} else if (!HardVacuumReloaded.playerProfile.isTut1()) {
			fhMissionsList = Gdx.files.internal("missions/missions_tut1.json");
		}
		try {
			JSONObject missionsList = new JSONObject(fhMissionsList.readString());
			json = missionsList.getJSONObject(Integer.toString(mission));
		} catch (JSONException e) {
			HardVacuumReloaded.error(this.getClass().toString(), "", e);
		}

		Label lblHeading = new Label("", skin, "heading.mission");
		lblHeading.setBounds(0, 805, 1205, 195);

		Label lblHeadingText = new Label(HardVacuumReloaded.getLangBundle().format("MissionDetail.lblHeadingText.text"), skin, "heading.text");
		lblHeadingText.setBounds(30, 815, 1155, 175);
		lblHeadingText.setAlignment(Align.center);

		Label lblMap = new Label("", skin, "map.mission");
		lblMap.setBounds(1205, 675, 395, 325);

		Label lblContent = new Label("", skin, "content.mission");
		lblContent.setBounds(0, 0, 1205, 805);

		Label lblMenu = new Label("", skin, "menu.mission");
		lblMenu.setBounds(1205, 0, 395, 675);

		Label lblCredits = new Label(Integer.toString(HardVacuumReloaded.playerProfile.getCredits()), skin);
		lblCredits.setBounds(1260, 185, 290, 80);

		ScrollPane spContent = new ScrollPane(null, skin);
		spContent.setBounds(20, 20, 1185, 620);

		ImageButton ibtnExit = new ImageButton(skin, "exit");
		ibtnExit.setBounds(1000, 660, 190, 140);
		ibtnExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (HardVacuumReloaded.playerProfile.getOldScreen().equals("MainMenu")) {
					((Game) Gdx.app.getApplicationListener()).setScreen(new MissionMenu());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("")) {
					if (!HardVacuumReloaded.playerProfile.isOnMission()) {
						((Game) Gdx.app.getApplicationListener()).setScreen(new MissionMenu());
					} else {
						((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
					}
				}
			}
		});

		ImageButton ibtnInfo = new ImageButton(skin, "menu.info");
		ibtnInfo.setBounds(1290, 100, 115, 90);
		ibtnInfo.setDisabled(true);

		ImageButton ibtnBuy = new ImageButton(skin, "menu.buy");
		ibtnBuy.setBounds(1405, 100, 115, 90);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnBuy.setDisabled(true);
		} else {
			ibtnBuy.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

				}
			});
		}

		ImageButton ibtnStats = new ImageButton(skin, "menu.stats");
		ibtnStats.setBounds(1290, 10, 115, 90);
		ibtnStats.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Stats());
			}
		});

		ImageButton ibtnOptions = new ImageButton(skin, "menu.options");
		ibtnOptions.setBounds(1405, 10, 115, 90);
		ibtnOptions.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Options());
			}
		});

		ImageButton ibtnEnterMission = new ImageButton(skin, "enterMission");
		ibtnEnterMission.setBounds(20, 660, 470, 135);
		ibtnEnterMission.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		Label lblMissionInfo1Employer = new Label("", skin, "lblEmployer");
		Label lblMissionInfo1EmployerText = null;
		Label lblMissionInfo1Class = new Label("", skin, "lblClass");
		Label lblMissionInfo1ClassText = null;
		Label lblMissionInfo1Payment = new Label("", skin, "lblPayment");
		Label lblMissionInfo1PaymentText = null;
		Label lblMissionInfo1Objective = new Label("", skin, "lblObjective");
		Label lblMissionInfo1ObjectiveText = null;
		Label lblMissionInfo1ObjectiveText2 = null;
		try {
			lblMissionInfo1EmployerText = new Label(json.getString("employer"), skin, "lblSmall.up");
			lblMissionInfo1ClassText = new Label(json.getString("class"), skin, "lblSmall.up");
			lblMissionInfo1PaymentText = new Label(Integer.toString(json.getInt("payment")), skin, "lblSmall.up");
			lblMissionInfo1ObjectiveText = new Label(json.getString("objective1"), skin, "lblSmall.up");
			lblMissionInfo1ObjectiveText2 = new Label(json.getString("objective2"), skin, "lblBig.up");
		} catch (JSONException e) {
			HardVacuumReloaded.error(this.getClass().toString(), "", e);
		}

		Table tblMissionInfo1 = new Table(skin);
		tblMissionInfo1.add(lblMissionInfo1Employer).width(350).height(75);
		tblMissionInfo1.add(lblMissionInfo1EmployerText).width(665).height(75).row();
		tblMissionInfo1.add(lblMissionInfo1Class).width(350).height(75);
		tblMissionInfo1.add(lblMissionInfo1ClassText).width(665).height(75).row();
		tblMissionInfo1.add(lblMissionInfo1Payment).width(350).height(75);
		tblMissionInfo1.add(lblMissionInfo1PaymentText).width(665).height(75).row();
		tblMissionInfo1.add(lblMissionInfo1Objective).width(350).height(75);
		tblMissionInfo1.add(lblMissionInfo1ObjectiveText).width(665).height(75).row();
		tblMissionInfo1.add(lblMissionInfo1ObjectiveText2).colspan(2).width(1015).height(75);

		spContent.setWidget(tblMissionInfo1);

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
			tblMissionInfo1.debug();
			spContent.debug();
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
		HardVacuumReloaded.debug(this.getClass().toString(), "cleaning up MissionDetails screen");
		stage.dispose();
	}

}
