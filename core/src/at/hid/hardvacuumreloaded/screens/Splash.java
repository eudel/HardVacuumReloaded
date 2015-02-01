package at.hid.hardvacuumreloaded.screens;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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

		batch = new SpriteBatch();

		Texture splashTexture = HardVacuumReloaded.assets.get("img/splash.jpg", Texture.class);
		splash = new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		HardVacuumReloaded.assets.finishLoading();
		HardVacuumReloaded.debug(this.getClass().toString(), "switching to MainMenu Screen");
		((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
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
