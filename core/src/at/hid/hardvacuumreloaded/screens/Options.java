package at.hid.hardvacuumreloaded.screens;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Options implements Screen {

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
		HardVacuumReloaded.debug(this.getClass().toString(), "creating Options screen");
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		Gdx.input.setInputProcessor(stage);

		// creating skin
		HardVacuumReloaded.debug(this.getClass().toString(), "creating skin");
		HardVacuumReloaded.assets.update();
		skin = HardVacuumReloaded.assets.get("ui/gui.json", Skin.class);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Label lblHeading = new Label("", skin, "heading.options");
		lblHeading.setBounds(0, 655, 1205, 345);

		final Label lblHeadingText = new Label(HardVacuumReloaded.getLangBundle().format("Options.lblHeadingText.text"), skin, "heading.text");
		lblHeadingText.setBounds(30, 815, 1155, 175);
		lblHeadingText.setAlignment(Align.center);

		Label lblMap = new Label("", skin, "map.options");
		lblMap.setBounds(1205, 655, 395, 345);

		Label lblContent = new Label("", skin, "content.options");
		lblContent.setBounds(0, 0, 1205, 655);

		final Label lblContentText = new Label("", skin);
		lblContentText.setBounds(40, 30, 1130, 440);
		lblContentText.setWrap(true);

		Label lblMenu = new Label("", skin, "menu.options");
		lblMenu.setBounds(1205, 0, 395, 655);

		Label lblCredits = new Label(Integer.toString(HardVacuumReloaded.playerProfile.getCredits()), skin);
		lblCredits.setBounds(1260, 200, 290, 80);

		ScrollPane spContent = new ScrollPane(null, skin);
		spContent.setBounds(20, 20, 1185, 620);

		ImageButton ibtnExit = new ImageButton(skin, "exit");
		ibtnExit.setBounds(1000, 660, 190, 140);
		ibtnExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (HardVacuumReloaded.playerProfile.getOldScreen().equals("MainMenu")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Stats")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Stats());
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("Buy")) {
					HardVacuumReloaded.playerProfile.setOldScreen("");
					((Game) Gdx.app.getApplicationListener()).setScreen(new Buy());
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
				} else if (HardVacuumReloaded.playerProfile.getOldScreen().equals("")) {
					if (!HardVacuumReloaded.playerProfile.isOnMission()) {
						((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
					} else {
						((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
					}
				}
			}
		});

		ImageButton ibtnMenuInfo = new ImageButton(skin, "menu.info");
		ibtnMenuInfo.setBounds(1350, 105, 115, 90);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnMenuInfo.setDisabled(true);
		} else {
			ibtnMenuInfo.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

				}
			});
		}

		ImageButton ibtnMenuBuy = new ImageButton(skin, "menu.buy");
		ibtnMenuBuy.setBounds(1465, 105, 115, 90);
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
		ibtnMenuStats.setBounds(1350, 15, 115, 90);
		ibtnMenuStats.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("Options");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Stats());
			}
		});

		ImageButton ibtnMenuOptions = new ImageButton(skin, "menu.options");
		ibtnMenuOptions.setBounds(1465, 15, 115, 90);
		ibtnMenuOptions.setDisabled(true);

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

		ImageButton ibtnMenuMap = new ImageButton(skin, "menu.map");
		ibtnMenuMap.setBounds(1235, 15, 115, 90);
		if (!HardVacuumReloaded.playerProfile.isOnMission()) {
			ibtnMenuMap.setDisabled(true);
		} else {
			ibtnMenuMap.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

				}
			});
		}
		final ImageButton ibtnSound = new ImageButton(skin, "sound");
		;
		final ImageButton ibtnMusic = new ImageButton(skin, "music");

		ImageButton ibtnSave = new ImageButton(skin, "save");
		ibtnSave.setBounds(15, 660, 160, 140);
		ibtnSave.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ibtnMusic.setDisabled(false);
				ibtnSound.setDisabled(false);
				lblHeadingText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblHeadingText.ibtnSave.text"));
				lblContentText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblContentText.ibtnSave.text"));
			}
		});

		ImageButton ibtnLoad = new ImageButton(skin, "load");
		ibtnLoad.setBounds(190, 660, 160, 140);
		ibtnLoad.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ibtnMusic.setDisabled(false);
				ibtnSound.setDisabled(false);
				lblHeadingText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblHeadingText.ibtnLoad.text"));
				lblContentText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblContentText.ibtnLoad.text"));
			}
		});

		ImageButton ibtnDelete = new ImageButton(skin, "delete");
		ibtnDelete.setBounds(365, 660, 160, 140);
		ibtnDelete.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ibtnMusic.setDisabled(false);
				ibtnSound.setDisabled(false);
				lblHeadingText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblHeadingText.ibtnDelete.text"));
				lblContentText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblContentText.ibtnDelete.text"));
			}
		});

		ImageButton ibtnControl = new ImageButton(skin, "control");
		ibtnControl.setBounds(600, 660, 160, 140);
		ibtnControl.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ibtnMusic.setDisabled(false);
				ibtnSound.setDisabled(false);
				lblHeadingText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblHeadingText.ibtnControl.text"));
				lblContentText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblContentText.ibtnControl.text"));
			}
		});

		ImageButton ibtnKill = new ImageButton(skin, "kill");
		ibtnKill.setBounds(775, 660, 160, 140);
		ibtnKill.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				ibtnMusic.setDisabled(false);
				ibtnSound.setDisabled(false);
				lblHeadingText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblHeadingText.ibtnKill.text"));
				lblContentText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblContentText.ibtnKill.text"));
			}
		});

		final Slider slideVolume = new Slider(1, 100, 10, false, skin, "volume");
		slideVolume.setBounds(620, 550, 560, 50);
		slideVolume.getStyle().knobBefore.setMinHeight(50);
		slideVolume.setValue(100);
		slideVolume.setDisabled(true);
		slideVolume.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (ibtnMusic.isDisabled())
					Gdx.app.getPreferences(HardVacuumReloaded.TITLE).putFloat("music", slideVolume.getValue());
				else if (ibtnSound.isDisabled())
					Gdx.app.getPreferences(HardVacuumReloaded.TITLE).putFloat("sound", slideVolume.getValue());
				Gdx.app.getPreferences(HardVacuumReloaded.TITLE).flush();
				lblContentText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblContentText.slideVolume.text")+ " " + (int) slideVolume.getValue());
			}
		});

		ibtnMusic.setBounds(15, 500, 160, 140);
		ibtnMusic.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ibtnMusic.setDisabled(true);
				ibtnSound.setDisabled(false);
				slideVolume.setValue(Gdx.app.getPreferences(HardVacuumReloaded.TITLE).getFloat("music"));
				slideVolume.setDisabled(false);
				lblHeadingText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblHeadingText.ibtnMusic.text"));
				lblContentText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblContentText.ibtnMusic.text"));
			}
		});

		ibtnSound.setBounds(190, 500, 160, 140);
		ibtnSound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ibtnSound.setDisabled(true);
				ibtnMusic.setDisabled(false);
				slideVolume.setValue(Gdx.app.getPreferences(HardVacuumReloaded.TITLE).getFloat("sound"));
				slideVolume.setDisabled(false);
				lblHeadingText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblHeadingText.ibtnSound.text"));
				lblContentText.setText(HardVacuumReloaded.getLangBundle().format("Options.lblContentText.ibtnSound.text"));
			}
		});

		ImageButton ibtnSelectBoth = new ImageButton(skin, "select.both");
		ibtnSelectBoth.setBounds(1355, 280, 100, 105);
		ibtnSelectBoth.setVisible(false);
		ibtnSelectBoth.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		ImageButton ibtnSelectLeft = new ImageButton(skin, "select.left");
		ibtnSelectLeft.setBounds(1355, 280, 100, 105);
		ibtnSelectLeft.setVisible(false);
		ibtnSelectLeft.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		ImageButton ibtnSelectRight = new ImageButton(skin, "select.right");
		ibtnSelectRight.setBounds(1355, 280, 100, 105);
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
			lblCredits.debug();
			lblMenu.debug();
			ibtnExit.debug();
			ibtnMenuInfo.debug();
			ibtnMenuBuy.debug();
			ibtnMenuStats.debug();
			ibtnMenuOptions.debug();
			ibtnMenuMap.debug();
			ibtnMenuAirstrike.debug();
			ibtnSave.debug();
			ibtnLoad.debug();
			ibtnDelete.debug();
			ibtnControl.debug();
			ibtnKill.debug();
			ibtnMusic.debug();
			ibtnSound.debug();
			ibtnSelectBoth.debug();
			ibtnSelectLeft.debug();
			ibtnSelectRight.debug();
			spContent.debug();
			slideVolume.debug();
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
		stage.addActor(ibtnMenuInfo);
		stage.addActor(ibtnMenuBuy);
		stage.addActor(ibtnMenuStats);
		stage.addActor(ibtnMenuOptions);
		stage.addActor(ibtnMenuMap);
		stage.addActor(ibtnMenuAirstrike);
		stage.addActor(ibtnSave);
		stage.addActor(ibtnLoad);
		stage.addActor(ibtnDelete);
		stage.addActor(ibtnControl);
		stage.addActor(ibtnKill);
		stage.addActor(ibtnMusic);
		stage.addActor(ibtnSound);
		stage.addActor(ibtnSelectBoth);
		stage.addActor(ibtnSelectLeft);
		stage.addActor(ibtnSelectRight);
		stage.addActor(slideVolume);
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
		HardVacuumReloaded.debug(this.getClass().toString(), "cleaning up Options screen");
		stage.dispose();
	}

}
