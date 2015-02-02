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

public class Buy implements Screen {

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
		HardVacuumReloaded.debug(this.getClass().toString(), "creating Buy screen");
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		Gdx.input.setInputProcessor(stage);

		// creating skin
		HardVacuumReloaded.debug(this.getClass().toString(), "creating skin");
		HardVacuumReloaded.assets.update();
		skin = HardVacuumReloaded.assets.get("ui/gui.json", Skin.class);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Label lblHeading = new Label("", skin, "heading.buy");
		lblHeading.setBounds(0, 805, 1205, 195);

		Label lblHeadingText = new Label(HardVacuumReloaded.getLangBundle().format("Buy.lblHeadingText.text"), skin, "heading.text");
		lblHeadingText.setBounds(20, 815, 1155, 175);
		lblHeadingText.setAlignment(Align.center);

		Label lblMap = new Label("", skin, "map.buy");
		lblMap.setBounds(1205, 650, 395, 350);

		Label lblContent = new Label("", skin, "content.buy");
		lblContent.setBounds(0, 0, 1205, 805);

		Label lblMenu = new Label("", skin, "menu.buy");
		lblMenu.setBounds(1205, 0, 395, 650);

		Label lblCredits = new Label(Integer.toString(HardVacuumReloaded.playerProfile.getCredits()), skin);
		lblCredits.setBounds(1260, 297, 290, 80);
		
		Label lblCount = new Label("", skin);
		lblCount.setBounds(250, 720, 100, 60);
		
		Label lblName = new Label("", skin);
		lblName.setBounds(60, 625, 490, 60);

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
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Stats")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Stats());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Help")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Help());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("GameScreen")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
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
		ibtnMenuGrab.setBounds(1235, 195, 115, 90);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnMenuGrab.setDisabled(true);
		} else {
			ibtnMenuGrab.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

				}
			});
		}

		ImageButton ibtnMenuMap = new ImageButton(skin, "menu.map");
		ibtnMenuMap.setBounds(1350, 195, 115, 90);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnMenuMap.setDisabled(true);
		} else {
			ibtnMenuMap.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

				}
			});
		}

		ImageButton ibtnMenuStats = new ImageButton(skin, "menu.stats");
		ibtnMenuStats.setBounds(1465, 195, 115, 90);
		ibtnMenuStats.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("Buy");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Stats());
			}
		});
		
		ImageButton ibtnMenuAirstrike = new ImageButton(skin, "menu.airstrike");
		ibtnMenuAirstrike.setBounds(1235, 105, 115, 90);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnMenuAirstrike.setDisabled(true);
		} else {
			ibtnMenuAirstrike.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

				}
			});
		}
		
		ImageButton ibtnMenuSelectGroup = new ImageButton(skin, "menu.selectGroup");
		ibtnMenuSelectGroup.setBounds(1350, 105, 115, 90);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnMenuSelectGroup.setDisabled(true);
		} else {
			ibtnMenuSelectGroup.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

				}
			});
		}
		
		ImageButton ibtnMenuHelp = new ImageButton(skin, "menu.help");
		ibtnMenuHelp.setBounds(1235, 15, 115, 90);
		ibtnMenuHelp.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("Buy");
//				((Game) Gdx.app.getApplicationListener()).setScreen(new Help());
			}
		});
		
		ImageButton ibtnMenuDisc = new ImageButton(skin, "menu.disc");
		ibtnMenuDisc.setBounds(1350, 15, 115, 90);
		ibtnMenuDisc.setDisabled(true);
//		ibtnMenuDisc.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				HardVacuumReloaded.playerProfile.setOldScreen("Buy");
//				((Game) Gdx.app.getApplicationListener()).setScreen(new Disc());
//			}
//		});

		ImageButton ibtnMenuOptions = new ImageButton(skin, "menu.options");
		ibtnMenuOptions.setBounds(1465, 15, 115, 90);
		ibtnMenuOptions.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("Buy");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Options());
			}
		});
		
		ImageButton ibtnCategoryVehicles = new ImageButton(skin, "category.vehicles");
		ibtnCategoryVehicles.setBounds(610, 735, 380, 65);
		ibtnCategoryVehicles.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		
		ImageButton ibtnCategoryStructures = new ImageButton(skin, "category.structures");
		ibtnCategoryStructures.setBounds(610, 670, 380, 65);
		ibtnCategoryStructures.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		
		ImageButton ibtnCategoryMisc = new ImageButton(skin, "category.misc");
		ibtnCategoryMisc.setBounds(610, 605, 380, 65);
		ibtnCategoryMisc.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		
		ImageButton ibtnMinus = new ImageButton(skin, "minus");
		ibtnMinus.setBounds(25, 700, 200, 90);
		ibtnMinus.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		
		ImageButton ibtnPlus = new ImageButton(skin, "plus");
		ibtnPlus.setBounds(375, 700, 200, 90);
		ibtnPlus.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		
		Field1 f1 = new Field1(skin);
		f1.show(stage, 0, 400);
		f1.setField1Red(true);
		f1.setField2Red(true);
		f1.setField1(99);
		f1.setField2(99999);
		
		if (HardVacuumReloaded.DEBUG) {
			lblHeading.debug();
			lblHeadingText.debug();
			lblMap.debug();
			lblContent.debug();
			lblMenu.debug();
			lblCredits.debug();
			lblCount.debug();
			lblName.debug();
			spContent.debug();
			ibtnExit.debug();
			ibtnMenuGrab.debug();
			ibtnMenuMap.debug();
			ibtnMenuStats.debug();
			ibtnMenuAirstrike.debug();
			ibtnMenuSelectGroup.debug();
			ibtnMenuHelp.debug();
			ibtnMenuDisc.debug();
			ibtnMenuOptions.debug();
			ibtnCategoryVehicles.debug();
			ibtnCategoryStructures.debug();
			ibtnCategoryMisc.debug();
			ibtnMinus.debug();
			ibtnPlus.debug();
		}
		stage.addActor(lblHeading);
		stage.addActor(lblHeadingText);
		stage.addActor(lblMap);
		stage.addActor(lblContent);
		stage.addActor(lblMenu);
		stage.addActor(lblCredits);
		stage.addActor(lblCount);
		stage.addActor(lblName);
		stage.addActor(spContent);
		stage.addActor(ibtnExit);
		stage.addActor(ibtnMenuGrab);
		stage.addActor(ibtnMenuMap);
		stage.addActor(ibtnMenuStats);
		stage.addActor(ibtnMenuAirstrike);
		stage.addActor(ibtnMenuSelectGroup);
		stage.addActor(ibtnMenuHelp);
		stage.addActor(ibtnMenuDisc);
		stage.addActor(ibtnMenuOptions);
		stage.addActor(ibtnCategoryVehicles);
		stage.addActor(ibtnCategoryStructures);
		stage.addActor(ibtnCategoryMisc);
		stage.addActor(ibtnMinus);
		stage.addActor(ibtnPlus);
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
		HardVacuumReloaded.debug(this.getClass().toString(), "cleaning up Buy screen");
		stage.dispose();
	}

}
