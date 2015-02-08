package at.hid.hardvacuumreloaded.screens;

import java.util.ArrayList;

import at.hid.hardvacuumreloaded.Assets;
import at.hid.hardvacuumreloaded.HardVacuumReloaded;
import at.hid.hardvacuumreloaded.entities.Miner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

public class GameScreen implements Screen {
	private Stage stage;
	private Table table;
	private Skin skin;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private ArrayList<Object> entities = new ArrayList<Object>();
	private Miner miner;
//	private Animation iconSelected;
	private Sprite iconSelected;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		renderer.setView(camera);
		renderer.render();
		
		HardVacuumReloaded.engine.update(delta);
		
		renderer.getBatch().begin();
		miner.draw(renderer.getBatch());
		renderer.getBatch().end();

		renderer.getBatch().begin();
		iconSelected.draw(renderer.getBatch());
		renderer.getBatch().end();
		
		if (miner.hasTarget())
			miner.update(Gdx.graphics.getDeltaTime());
		
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			float x = Gdx.input.getX();
			float y = 1000 - Gdx.input.getY();
			
			if ((miner.getX() - 30 < x) && (x < miner.getX() + 50) && (miner.getY() - 30 < y) && (y < miner.getY() + 50)) {
				HardVacuumReloaded.playerProfile.setUnitSelected(true);
				miner.setSelected(true);
				iconSelected.setAlpha(1);
			} else {
				HardVacuumReloaded.playerProfile.setUnitSelected(false);
				miner.setSelected(false);
				iconSelected.setAlpha(0);
			}
		} else if ((Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) && (HardVacuumReloaded.playerProfile.isUnitSelected())) {
			int x = Gdx.input.getX();
			int y = 1000 - Gdx.input.getY();
			
			miner.setTarget(x, y);
		}
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.update();
		table.invalidateHierarchy();
		table.setFillParent(true);
	}
	
	@Override
	public void show() {
		HardVacuumReloaded.debug(this.getClass().toString(), "creating GameScreen screen");
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		Gdx.input.setInputProcessor(stage);
		
		// creating skin
		HardVacuumReloaded.debug(this.getClass().toString(), "creating skin");
		HardVacuumReloaded.assets.update();
		skin = HardVacuumReloaded.assets.get("ui/gui.json", Skin.class);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Label lblHeading = new Label("", skin, "heading.ifc1");
		lblHeading.setBounds(0, 800, 1200, 200);
		lblHeading.setVisible(false);

		Label lblHeadingText = new Label(HardVacuumReloaded.getLangBundle().format("GameScreen.lblHeadingText.text"), skin, "heading.text");
		lblHeadingText.setBounds(20, 815, 1155, 175);
		lblHeadingText.setAlignment(Align.center);

		Label lblMap = new Label("", skin, "map.ifc1");
		lblMap.setBounds(1200, 650, 400, 350);

		Label lblMenu = new Label("", skin, "menu.ifc1");
		lblMenu.setBounds(1200, 0, 400, 650);

		Label lblCredits = new Label(Integer.toString(HardVacuumReloaded.playerProfile.getCredits()), skin);
		lblCredits.setBounds(1260, 297, 290, 80);
		
		ScrollPane spContent = new ScrollPane(null, skin);
		spContent.setBounds(0, 0, 1200, 800);
		
		TiledMap map = HardVacuumReloaded.assets.get("maps/tut1.tmx", TiledMap.class);
		float unitScale = 5f;
		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
		camera = new OrthographicCamera();
		
		iconSelected = new Sprite(Assets.selectedIcon);
//		iconSelected = Assets.selectedIcon;
		miner = new Miner(new Sprite(Assets.minerS), (TiledMapTileLayer) map.getLayers().get("collision"), iconSelected);
		entities.add(miner);

		float x = (Float) map.getLayers().get("event").getObjects().get("miner").getProperties().get("x");
		float y = (Float) map.getLayers().get("event").getObjects().get("miner").getProperties().get("y");
		
		miner.setScale(5);
		miner.setX((x * 5) + 40);
		miner.setY((y * 5) + 40);
		iconSelected.setScale(5);
		iconSelected.setX((x * 5) + 40);
		iconSelected.setY((y * 5) + 90);
		iconSelected.setAlpha(0);
		
		camera.position.set(800, 500, 0);
		renderer.setView(camera);

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
				HardVacuumReloaded.playerProfile.setOldScreen("GameScreen");
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
		
		ImageButton ibtnMenuBuy = new ImageButton(skin, "menu.buy");
		ibtnMenuBuy.setBounds(1465, 105, 115, 90);
		ibtnMenuBuy.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("GameScreen");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Buy());
			}
		});
		
		ImageButton ibtnMenuHelp = new ImageButton(skin, "menu.help");
		ibtnMenuHelp.setBounds(1235, 15, 115, 90);
		ibtnMenuHelp.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("GameScreen");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Help());
			}
		});
		
		ImageButton ibtnMenuDisc = new ImageButton(skin, "menu.disc");
		ibtnMenuDisc.setBounds(1350, 15, 115, 90);
		ibtnMenuDisc.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("GameScreen");
				((Game) Gdx.app.getApplicationListener()).setScreen(new MissionMenu());
			}
		});

		ImageButton ibtnMenuOptions = new ImageButton(skin, "menu.options");
		ibtnMenuOptions.setBounds(1465, 15, 115, 90);
		ibtnMenuOptions.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HardVacuumReloaded.playerProfile.setOldScreen("GameScreen");
				((Game) Gdx.app.getApplicationListener()).setScreen(new Options());
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
			ibtnMenuAirstrike.debug();
			ibtnMenuSelectGroup.debug();
			ibtnMenuBuy.debug();
			ibtnMenuHelp.debug();
			ibtnMenuDisc.debug();
			ibtnMenuOptions.debug();
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
		stage.addActor(ibtnMenuAirstrike);
		stage.addActor(ibtnMenuSelectGroup);
		stage.addActor(ibtnMenuBuy);
		stage.addActor(ibtnMenuHelp);
		stage.addActor(ibtnMenuDisc);
		stage.addActor(ibtnMenuOptions);
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
		HardVacuumReloaded.debug(this.getClass().toString(), "cleaning up GameScreen screen");
		stage.dispose();
	}

}
