package at.hid.hardvacuumreloaded.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import at.hid.hardvacuumreloaded.HardVacuumReloaded;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = HardVacuumReloaded.TITLE + " " + HardVacuumReloaded.VERSION;
		config.vSyncEnabled = false;
		config.width = 1600;
		config.height = 1000;
		config.x = 0;
		config.y = 0;
		new LwjglApplication(new HardVacuumReloaded(), config);
	}
}
