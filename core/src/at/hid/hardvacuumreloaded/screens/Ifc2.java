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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Ifc2 implements Screen {
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
		HardVacuumReloaded.debug(this.getClass().toString(), "creating Ifc2 screen");
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		Gdx.input.setInputProcessor(stage);

		// creating skin
		HardVacuumReloaded.debug(this.getClass().toString(), "creating skin");
		HardVacuumReloaded.assets.update();
		skin = HardVacuumReloaded.assets.get("ui/gui.json", Skin.class);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Label lblHeading = new Label("", skin, "heading.ifc2");
		lblHeading.setBounds(0, 800, 1200, 200);

		Label lblHeadingText = new Label(HardVacuumReloaded.getLangBundle().format("Ifc2.lblHeadingText.text"), skin, "heading.text");
		lblHeadingText.setBounds(20, 815, 1155, 175);
		lblHeadingText.setAlignment(Align.center);

		Label lblMap = new Label("", skin, "map.ifc2");
		lblMap.setBounds(1200, 650, 400, 350);

		Label lblMenu = new Label("", skin, "menu.ifc2");
		lblMenu.setBounds(1200, 0, 400, 650);

		Label lblCredits = new Label(Integer.toString(HardVacuumReloaded.playerProfile.getCredits()), skin);
		lblCredits.setBounds(1260, 197, 290, 80);
		
		ScrollPane spContent = new ScrollPane(null, skin);
		spContent.setBounds(0, 0, 1200, 800);

		ImageButton ibtnMenuGrab = new ImageButton(skin, "menu.grab");
		ibtnMenuGrab.setBounds(1235, 105, 115, 90);
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

		ImageButton ibtnMenuStats = new ImageButton(skin, "menu.stats");
		ibtnMenuStats.setBounds(1350, 15, 115, 90);
		ibtnMenuStats.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("Ifc2");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Stats());
			}
		});
		
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
		
		ImageButton ibtnMenuBuy = new ImageButton(skin, "menu.buy");
		ibtnMenuBuy.setBounds(1465, 105, 115, 90);
		ibtnMenuBuy.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("Ifc2");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Buy());
			}
		});

		ImageButton ibtnMenuOptions = new ImageButton(skin, "menu.options");
		ibtnMenuOptions.setBounds(1465, 15, 115, 90);
		ibtnMenuOptions.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("Ifc2");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Options());
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
			lblMenu.debug();
			lblCredits.debug();
			spContent.debug();
			ibtnMenuGrab.debug();
			ibtnMenuMap.debug();
			ibtnMenuStats.debug();
			ibtnMenuSelectGroup.debug();
			ibtnMenuBuy.debug();
			ibtnMenuOptions.debug();
			ibtnSelectBoth.debug();
			ibtnSelectLeft.debug();
			ibtnSelectRight.debug();
		}
		stage.addActor(lblHeading);
		stage.addActor(lblHeadingText);
		stage.addActor(lblMap);
		stage.addActor(lblMenu);
		stage.addActor(lblCredits);
		stage.addActor(spContent);
		stage.addActor(ibtnMenuGrab);
		stage.addActor(ibtnMenuMap);
		stage.addActor(ibtnMenuStats);
		stage.addActor(ibtnMenuSelectGroup);
		stage.addActor(ibtnMenuBuy);
		stage.addActor(ibtnMenuOptions);
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
		HardVacuumReloaded.debug(this.getClass().toString(), "cleaning up Ifc2 screen");
		stage.dispose();
	}

}
