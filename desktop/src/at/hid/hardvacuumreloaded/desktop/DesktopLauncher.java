package at.hid.hardvacuumreloaded.desktop;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;
import at.hid.hardvacuumreloaded.desktop.api.DesktopApp42;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = HardVacuumReloaded.TITLE + " " + HardVacuumReloaded.VERSION;
		config.vSyncEnabled = false;
		config.x = 0;
		config.y = 0;
		config.resizable = false;
		
		GraphicsDevice monitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		Dimension screenResolution = new Dimension(monitor.getDisplayMode().getWidth(), monitor.getDisplayMode().getHeight());
		
		int scale = 0;
		if (screenResolution.width >= 1600 && screenResolution.height >= 1080) {
			config.width = 1600;
			config.height = 1000;
			scale = 5;
		} else if (screenResolution.width >= 1280 && screenResolution.height >= 864) {
			config.width = 1280;
			config.height = 800;
			scale = 4;
		} else if (screenResolution.width >= 960 && screenResolution.height >= 648) {
			config.width = 960;
			config.height = 600;
			scale = 3;
		} else if (screenResolution.width >= 640 && screenResolution.height >= 432) {
			config.width = 640;
			config.height = 400;
			scale = 2;
		} else {
			config.width = 320;
			config.height = 200;
			scale = 1;
		}
		
		new LwjglApplication(new HardVacuumReloaded(new DesktopApp42(), scale), config);
	}
}
