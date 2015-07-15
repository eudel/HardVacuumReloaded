package at.hid.hardvacuumreloaded;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONObject;

import at.hid.hardvacuumreloaded.api.App42;
import at.hid.hardvacuumreloaded.screens.Splash;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.I18NBundle;

public class HardVacuumReloaded extends Game {
	public static final String TITLE = "Hard Vacuum Reloaded", VERSION = "0.0.1-alpha";
	public static boolean DEBUG;

	public static I18NBundle langBundle;
	public static final AssetManager assets = new AssetManager();
	public static final Random random = new Random();
	public static PlayerProfile playerProfile = new PlayerProfile();
	public static GameProfile gameProfile = new GameProfile();
	public static Engine engine = new Engine();
	public static App42 app42 = null;
	
	public HardVacuumReloaded(App42 app42) {
		HardVacuumReloaded.app42 = app42;
	}
	
	/**
	 * creates the language bundle
	 */
	public static void createLangBundle(String lang) {
		FileHandle fhLang = Gdx.files.internal("lang/Language");
		Locale locale = new Locale(lang);
		langBundle = I18NBundle.createBundle(fhLang, locale);
	}

	/**
	 * @return the langBundle
	 */
	public static I18NBundle getLangBundle() {
		return langBundle;
	}

	public static void log(String tag, String message) {
		FileHandle fhLog = null;
		if (Gdx.files.isExternalStorageAvailable()) {
			fhLog = Gdx.files.external(".hidlauncher/" + TITLE + "/logs/latest.log");
		} else {
			fhLog = Gdx.files.internal(".hidlauncher/" + TITLE + "/logs/latest.log");
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss: ");
		Gdx.app.log(tag, message);
		fhLog.writeString(sdf.format(date) + tag + ": " + message + "\n", true, "UTF-8");
	}

	public static void debug(String tag, String message) {
		FileHandle fhLog = null; 
		if (Gdx.files.isExternalStorageAvailable()) {
			fhLog = Gdx.files.external(".hidlauncher/" + TITLE + "/logs/latest.log");
		} else {
			fhLog = Gdx.files.internal(".hidlauncher/" + TITLE + "/logs/latest.log");
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss: ");
		Gdx.app.debug(tag, message);
		if (DEBUG) {
			fhLog.writeString(sdf.format(date) + tag + ": Debug: " + message + "\n", true, "UTF-8");
		}
	}

	public static void error(String tag, String message, Throwable t) {
		FileHandle fhLog = null;
		if (Gdx.files.isExternalStorageAvailable()) {
			fhLog = Gdx.files.external(".hidlauncher/" + TITLE + "/logs/latest.log");
		} else {
			fhLog = Gdx.files.internal(".hidlauncher/" + TITLE + "/logs/latest.log");
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss: ");
		Gdx.app.error(tag, message, t);
		fhLog.writeString(sdf.format(date) + tag + ": ERROR: " + message + "\n", true, "UTF-8");
		fhLog.writeString(t + "\n", true, "UTF-8");
	}

	public void logrotate() {
		FileHandle fhLog = null;
		if (Gdx.files.isExternalStorageAvailable()) {
			fhLog = Gdx.files.external(".hidlauncher/" + TITLE + "/logs/latest.log");
		} else {
			fhLog = Gdx.files.internal(".hidlauncher/" + TITLE + "/logs/latest.log");
		}
		fhLog.parent().mkdirs();
		if (fhLog.exists()) {
			byte[] buffer = new byte[1024];
			try {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
				FileOutputStream fos = null;
				if (Gdx.files.isExternalStorageAvailable()) {
					fos = new FileOutputStream(Gdx.files.external(".hidlauncher/" + TITLE + "/logs/" + sdf.format(date) + ".zip").file());
				} else {
					fos = new FileOutputStream(Gdx.files.internal(".hidlauncher/" + TITLE + "/logs/" + sdf.format(date) + ".zip").file());
				}
				ZipOutputStream zos = new ZipOutputStream(fos);
				ZipEntry ze = new ZipEntry("latest.log");
				zos.putNextEntry(ze);
				FileInputStream in = null;
				if (Gdx.files.isExternalStorageAvailable()) {
					in = new FileInputStream(Gdx.files.external(".hidlauncher/" + TITLE + "/logs/latest.log").file());
				} else {
					in = new FileInputStream(Gdx.files.internal(".hidlauncher/" + TITLE + "/logs/latest.log").file());
				}

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
				zos.closeEntry();
				zos.close();
				fhLog.delete();
			} catch (Exception e) {
				error(this.getClass().getName(), "error rotating log file", e);
			}
		}
	}
	
	@Override
	public void create () {
		app42.initialize("5a648eddecc5f7329670a3d435a9daef100f2d62ac937bd2e1bf659b7eee091d", "050720a0c872c6103432575d5e5873b4468a1e9336d48908b1ce831ea6cc8395");
		app42.buildUserService();
		app42.buildStorageService();
		
		String uName = "";
		String pwd = "";
		if (Gdx.files.external(".hidlauncher/launcher_profiles.json").exists()) {
			try {
				JSONObject json = new JSONObject(Gdx.files.external(".hidlauncher/launcher_profiles.json").readString("UTF-8"));
				uName = json.getString("selectedUser");
				pwd = Base64Coder.decodeString(Gdx.app.getPreferences("HIDLauncher").getString("pass"));
			} catch (Exception e) {
				HardVacuumReloaded.error(this.getClass().toString(), "error reading launcher_profiles", e);
			}
		}
//		String sid = Gdx.app.getPreferences("HIDLauncher").getString("sessionIdTts");

		if (uName != "") {
			if (inetConnection()) {
				app42.authenticate(uName, pwd);
//				app42.getUser(username);
			}
//			app42.setSessionId(sid);
		}
		
		DEBUG = Gdx.app.getPreferences(TITLE).getBoolean("debug");
		logrotate();

		if (DEBUG) {
			Gdx.app.setLogLevel(Application.LOG_DEBUG); // show debug logs 
		} else {
			Gdx.app.setLogLevel(Application.LOG_INFO);
		}

		if (Gdx.app.getPreferences(TITLE).contains("lang")) { // load saved language preferences
			String[] data = Gdx.app.getPreferences(TITLE).getString("lang").split("_");
			Locale.setDefault(new Locale(data[0], data[1]));
		} else {
			Gdx.app.getPreferences(TITLE).putString("lang", Locale.getDefault().toString()); // create preferences file
			Gdx.app.getPreferences(TITLE).putBoolean("debug", false);
			Gdx.app.getPreferences(TITLE).putBoolean("fullscreen", false);
			Gdx.app.getPreferences(TITLE).putBoolean("vsync", true);
			Gdx.app.getPreferences(TITLE).putFloat("music", 100);
			Gdx.app.getPreferences(TITLE).putFloat("sound", 100);
			Gdx.app.getPreferences(TITLE).flush();
		}

		createLangBundle(Locale.getDefault().toString());

		Texture.setAssetManager(assets);
		assets.load("img/splash.jpg", Texture.class);
		assets.finishLoading();
		
		FileHandle fhPlayerProfile;
		if (Gdx.files.isExternalStorageAvailable()) {
			fhPlayerProfile = Gdx.files.external(".hardvacuumreloaded/playerprofile.dat");
		} else {
			fhPlayerProfile = Gdx.files.local(".hardvacuumreloaded/playerprofile.dat");
		}
		if (!fhPlayerProfile.exists()) {
			playerProfile.saveProfile();
		} else {
			playerProfile.loadProfile();
		}

		setScreen(new Splash());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
	public boolean inetConnection() {
		try {
			final URLConnection connection = new URL("http://api.shephertz.com/").openConnection();
			connection.connect();
			log(this.getClass().toString(), "Internet connection available.");
			return true;
		} catch (final Exception e) {
			return false;
		}
	}
}
