package at.hid.hardvacuumreloaded;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Animation; // TODO unitSelected animation
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static TextureRegion minerN, minerNE, minerE, minerSE, minerS, minerSW, minerW, minerNW;
	public static TextureRegion minerNUp, minerNEUp, minerEUp, minerSEUp, minerSUp, minerSWUp, minerWUp, minerNWUp;
	public static TextureRegion minerNDown, minerNEDown, minerEDown, minerSEDown, minerSDown, minerSWDown, minerWDown, minerNWDown;
	public static TextureRegion selectedIcon;
//	public static Animation selectedIcon;

	public static void load() {
		HardVacuumReloaded.assets.load("sprites/miner.png", Texture.class);
		HardVacuumReloaded.assets.load("sprites/selected.png", Texture.class);
		HardVacuumReloaded.assets.finishLoading();
		
		HardVacuumReloaded.assets.load("ui/gui.json", Skin.class);
		
		HardVacuumReloaded.assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		HardVacuumReloaded.assets.load("maps/tut1.tmx", TiledMap.class);
		
		minerN = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 0, 0, 20, 20);
		minerNDown = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 0, 20, 20, 20);
		minerNUp = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 0, 40, 20, 20);
		minerNW = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 20, 0, 20, 20);
		minerNWDown = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 20, 20, 20, 20);
		minerNWUp = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 20, 40, 20, 20);
		minerW = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 40, 0, 20, 20);
		minerWDown = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 40, 20, 20, 20);
		minerWUp = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 40, 40, 20, 20);
		minerSW = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 60, 0, 20, 20);
		minerSWDown = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 60, 20, 20, 20);
		minerSWUp = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 60, 40, 20, 20);
		minerS = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 80, 0, 20, 20);
		minerSDown = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 80, 20, 20, 20);
		minerSUp = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 80, 40, 20, 20);
		minerSE = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 100, 0, 20, 20);
		minerSEDown = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 100, 20, 20, 20);
		minerSEUp = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 100, 40, 20, 20);
		minerE = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 120, 0, 20, 20);
		minerEDown = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 120, 20, 20, 20);
		minerEUp = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 120, 40, 20, 20);
		minerNE = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 140, 0, 20, 20);
		minerNEDown = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 140, 20, 20, 20);
		minerNEUp = new TextureRegion(HardVacuumReloaded.assets.get("sprites/miner.png", Texture.class), 140, 40, 20, 20);
		
		selectedIcon = new TextureRegion(HardVacuumReloaded.assets.get("sprites/selected.png", Texture.class), 0, 60, 20, 20);
//		selectedIcon = new Animation(0.2f, new TextureRegion(HardVacuumReloaded.assets.get("sprites/selected.png", Texture.class), 0, 60, 20, 20), new TextureRegion(HardVacuumReloaded.assets.get("sprites/selected.png", Texture.class), 20, 60, 20, 20), new TextureRegion(HardVacuumReloaded.assets.get("sprites/selected.png", Texture.class), 40, 60, 20, 20));
	}
	
	public static void playSound(Sound sound) {
		if (Gdx.app.getPreferences(HardVacuumReloaded.TITLE).getFloat("sound") != 0) sound.play(Gdx.app.getPreferences(HardVacuumReloaded.TITLE).getFloat("sound"));
	}
	
	public static void playMusic(Music music) {
		if (Gdx.app.getPreferences(HardVacuumReloaded.TITLE).getFloat("music") != 0) music.play();
	}
}
