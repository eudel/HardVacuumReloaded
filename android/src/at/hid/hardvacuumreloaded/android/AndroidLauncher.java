package at.hid.hardvacuumreloaded.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;
import at.hid.hardvacuumreloaded.android.api.AndroidApp42;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;
		initialize(new HardVacuumReloaded(new AndroidApp42(), 5), config);
	}
}
