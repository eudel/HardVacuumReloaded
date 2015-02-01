package at.hid.hardvacuumreloaded;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import at.hid.hardvacuumreloaded.screens.Splash;

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
		FileHandle fhLog = Gdx.files.external(".hardvacuumreloaded/logs/latest.log");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss: ");
		Gdx.app.log(tag, message);
		fhLog.writeString(sdf.format(date) + tag + ": " + message + "\n", true, "UTF-8");
	}

	public static void debug(String tag, String message) {
		FileHandle fhLog = Gdx.files.external(".hardvacuumreloaded/logs/latest.log");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss: ");
		Gdx.app.debug(tag, message);
		if (DEBUG) {
			fhLog.writeString(sdf.format(date) + tag + ": Debug: " + message + "\n", true, "UTF-8");
		}
	}

	public static void error(String tag, String message, Throwable t) {
		FileHandle fhLog = Gdx.files.external(".hardvacuumreloaded/logs/latest.log");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss: ");
		Gdx.app.error(tag, message, t);
		fhLog.writeString(sdf.format(date) + tag + ": ERROR: " + message + "\n", true, "UTF-8");
		fhLog.writeString(t + "\n", true, "UTF-8");
	}

	public void logrotate() {
		FileHandle fhLog = Gdx.files.external(".hardvacuumreloaded/logs/latest.log");
		fhLog.parent().mkdirs();
		if (fhLog.exists()) {
			byte[] buffer = new byte[1024];
			try {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
				FileOutputStream fos = new FileOutputStream(Gdx.files.external(".hardvacuumreloaded/logs/" + sdf.format(date) + ".zip").file());
				ZipOutputStream zos = new ZipOutputStream(fos);
				ZipEntry ze = new ZipEntry("latest.log");
				zos.putNextEntry(ze);
				FileInputStream in = new FileInputStream(Gdx.files.external(".hardvacuumreloaded/logs/latest.log").file());

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
			Gdx.app.getPreferences(TITLE).putString("lang", Locale.getDefault().toString());
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
		
		FileHandle fhPlayerProfile = Gdx.files.external(".hardvacuumreloaded/playerprofile.dat");
		if (!fhPlayerProfile.exists()) { // TODO Revisit
			JSONObject json = new JSONObject();
			try {
				json.put("tut1", false);
				json.put("tut2", false);
				json.put("tut3", false);
			} catch (JSONException e) {
				error(this.getClass().toString(), "error creating PlayerProfile JSONObject", e);
			}
			String profileAsText = json.toString();
			String encodedProfile = Base64Coder.encodeString(profileAsText);
			fhPlayerProfile.writeString(encodedProfile, false, "UTF-8");
		} else {
			String encodedProfile = fhPlayerProfile.readString("UTF-8");
			String profileAsText = Base64Coder.decodeString(encodedProfile);
			try {
				JSONObject json = new JSONObject(profileAsText);
				playerProfile.setTut1(json.getBoolean("tut1"));
				playerProfile.setTut2(json.getBoolean("tut2"));
				playerProfile.setTut3(json.getBoolean("tut3"));
			} catch (JSONException e) {
				error(this.getClass().toString(), "error creating PlayerProfile JSONObject", e);
			}
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
}
