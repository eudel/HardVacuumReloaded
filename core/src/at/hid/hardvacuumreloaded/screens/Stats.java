package at.hid.hardvacuumreloaded.screens;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Stats implements Screen {

	private Stage stage;
	private Table table;
	private Skin skin;

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
		HardVacuumReloaded.debug(this.getClass().toString(), "creating Stats screen");
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		Gdx.input.setInputProcessor(stage);

		// creating skin
		HardVacuumReloaded.debug(this.getClass().toString(), "creating skin");
		HardVacuumReloaded.assets.update();
		skin = HardVacuumReloaded.assets.get("ui/gui.json", Skin.class);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Label lblHeading = new Label("", skin, "heading.stats");
		lblHeading.setBounds(0, 665, 1200, 335);

		Label lblHeadingText = new Label(HardVacuumReloaded.getLangBundle().format("Stats.lblHeadingText.text"), skin, "heading.text");
		lblHeadingText.setBounds(20, 815, 1155, 175);
		lblHeadingText.setAlignment(Align.center);

		Label lblMap = new Label("", skin, "map.stats");
		lblMap.setBounds(1200, 665, 400, 335);

		Label lblContent = new Label("", skin, "content.stats");
		lblContent.setBounds(0, 0, 1200, 665);

		Label lblContentText = new Label("", skin);
		lblContentText.setBounds(40, 30, 1125, 610);

		Label lblMenu = new Label("", skin, "menu.stats");
		lblMenu.setBounds(1200, 0, 400, 665);

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
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new MissionMenu());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Options")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Options());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Help")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Help());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Ifc1")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Ifc1());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Ifc2")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Ifc2());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Ifc3")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Ifc3());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Ifc4")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Ifc4());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Buy")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Buy());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("")) {
					if (!HardVacuumReloaded.playerProfile.isOnMission()) {
						((Game) Gdx.app.getApplicationListener()).setScreen(new MissionMenu());
					} else {
						((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
					}
				}
			}
		});

		ImageButton ibtnMenuGrab = new ImageButton(skin, "menu.grab");
		ibtnMenuGrab.setBounds(1290, 100, 115, 90);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnMenuGrab.setDisabled(true);
		} else {
			ibtnMenuGrab.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

				}
			});
		}

		ImageButton ibtnMenuBuy = new ImageButton(skin, "menu.buy");
		ibtnMenuBuy.setBounds(1405, 100, 115, 90);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnMenuBuy.setDisabled(true);
		} else {
			ibtnMenuBuy.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

				}
			});
		}

		ImageButton ibtnMenuStats = new ImageButton(skin, "menu.stats");
		ibtnMenuStats.setBounds(1290, 10, 115, 90);
		ibtnMenuStats.setDisabled(true);

		ImageButton ibtnMenuOptions = new ImageButton(skin, "menu.options");
		ibtnMenuOptions.setBounds(1405, 10, 115, 90);
		ibtnMenuOptions.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("Stats");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Options());
			}
		});
		
		ImageButton ibtnDetails = new ImageButton(skin, "details");
		ibtnDetails.setBounds(20, 660, 160, 135);
		ibtnDetails.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		
		ImageButton ibtnDiplomacy = new ImageButton(skin, "diplomacy");
		ibtnDiplomacy.setBounds(180, 660, 160, 135);
		ibtnDiplomacy.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});

		ImageButton ibtnStructures = new ImageButton(skin, "structures");
		ibtnStructures.setBounds(340, 660, 160, 135);
		ibtnStructures.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		ImageButton ibtnTank = new ImageButton(skin, "tank");
		ibtnTank.setBounds(500, 660, 160, 135);
		ibtnTank.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		ImageButton ibtnAircraft = new ImageButton(skin, "aircraft");
		ibtnAircraft.setBounds(660, 660, 160, 135);
		ibtnAircraft.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		ImageButton ibtnBoat = new ImageButton(skin, "boat");
		ibtnBoat.setBounds(820, 660, 160, 135);
		ibtnBoat.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		
		ImageButton ibtnSelectBoth = new ImageButton(skin, "select.both");
		ibtnSelectBoth.setBounds(1355, 265, 100, 105);
		ibtnSelectBoth.setVisible(false);
		ibtnSelectBoth.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		
		ImageButton ibtnSelectLeft = new ImageButton(skin, "select.left");
		ibtnSelectLeft.setBounds(1355, 265, 100, 105);
		ibtnSelectLeft.setVisible(false);
		ibtnSelectLeft.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		
		ImageButton ibtnSelectRight = new ImageButton(skin, "select.right");
		ibtnSelectRight.setBounds(1355, 265, 100, 105);
		ibtnSelectRight.setVisible(false);
		ibtnSelectRight.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});

		if (HardVacuumReloaded.DEBUG) {
			lblHeading.debug();
			lblHeadingText.debug();
			lblMap.debug();
			lblContent.debug();
			lblContentText.debug();
			lblMenu.debug();
			lblCredits.debug();
			spContent.debug();
			ibtnExit.debug();
			ibtnMenuGrab.debug();
			ibtnMenuBuy.debug();
			ibtnMenuStats.debug();
			ibtnMenuOptions.debug();
			ibtnStructures.debug();
			ibtnTank.debug();
			ibtnDetails.debug();
			ibtnDiplomacy.debug();
			ibtnAircraft.debug();
			ibtnBoat.debug();
			ibtnSelectBoth.debug();
			ibtnSelectLeft.debug();
			ibtnSelectRight.debug();
		}
		stage.addActor(lblHeading);
		stage.addActor(lblHeadingText);
		stage.addActor(lblMap);
		stage.addActor(lblContent);
		stage.addActor(lblContentText);
		stage.addActor(lblMenu);
		stage.addActor(lblCredits);
		stage.addActor(spContent);
		stage.addActor(ibtnExit);
		stage.addActor(ibtnMenuGrab);
		stage.addActor(ibtnMenuBuy);
		stage.addActor(ibtnMenuStats);
		stage.addActor(ibtnMenuOptions);
		stage.addActor(ibtnStructures);
		stage.addActor(ibtnTank);
		stage.addActor(ibtnDetails);
		stage.addActor(ibtnDiplomacy);
		stage.addActor(ibtnAircraft);
		stage.addActor(ibtnBoat);
		stage.addActor(ibtnSelectBoth);
		stage.addActor(ibtnSelectLeft);
		stage.addActor(ibtnSelectRight);
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
		HardVacuumReloaded.debug(this.getClass().toString(), "cleaning up Stats screen");
		stage.dispose();
	}

}
