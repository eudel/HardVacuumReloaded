package at.hid.hardvacuumreloaded.screens;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sun.javafx.iio.ImageMetadata;

public class Splash implements Screen {
	private SpriteBatch batch;
	private Sprite splash;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		splash.draw(batch);
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		splash.setSize(width, height);
	}

	@Override
	public void show() {
		// apply preferences
		HardVacuumReloaded.debug(this.getClass().toString(), "apply preferences");
		Gdx.graphics.setVSync(Gdx.app.getPreferences(HardVacuumReloaded.TITLE).getBoolean("vsync", false));

		HardVacuumReloaded.debug(this.getClass().toString(), "creating Splash screen");

		HardVacuumReloaded.assets.load("ui/gui.json", Skin.class);
//		HardVacuumReloaded.assets.load("sprites/miner.png", TextureMapObject.class);
		HardVacuumReloaded.assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		HardVacuumReloaded.assets.load("maps/tut1.tmx", TiledMap.class);

		batch = new SpriteBatch();

		Texture splashTexture = HardVacuumReloaded.assets.get("img/splash.jpg", Texture.class);
		splash = new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		HardVacuumReloaded.assets.finishLoading();
		HardVacuumReloaded.debug(this.getClass().toString(), "switching to MainMenu Screen");
		((Game) Gdx.app.getApplicationListener()).setScreen(new MissionMenu());
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
		batch.dispose();
	}

}
