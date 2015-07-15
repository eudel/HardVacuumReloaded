package at.hid.hardvacuumreloaded.android.api;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;
import at.hid.hardvacuumreloaded.api.App42;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.UserService;

public class AndroidApp42 implements App42 {
	private UserService userService;
	private StorageService storageService;
	private User user;
	private Storage storage = null;
	ArrayList<Storage.JSONDocument> jsonDocList = null;

	public AndroidApp42() {
	}

	@Override
	public void initialize(String apiKey, String secretKey) {
		App42API.initialize(App42API.appContext, apiKey, secretKey);

	}

	@Override
	public void buildUserService() {
		userService = App42API.buildUserService();
	}

	@Override
	public void buildStorageService() {
		storageService = App42API.buildStorageService();
	}

	@Override
	public void getUser(String username) {
		user = userService.getUser(username);
	}
	
	@Override
	public void authenticate(String uName, String pwd) {
		user = userService.authenticate(uName, pwd);
	}

	@Override
	public void setSessionId(String sid) {
		user.setSessionId(sid);
		userService.setSessionId(sid);
		storageService.setSessionId(sid);
	}

	@Override
	public void storageServiceFindDocumentByKeyValue(String dbName, String collectionName, String key, String value) {
		storage = storageService.findDocumentByKeyValue(dbName, collectionName, key, value);
	}

	@Override
	public void storageServiceDeleteDocumentsByKeyValue(String dbName, String collectionName, String key, String value) {
		storageService.deleteDocumentsByKeyValue(dbName, collectionName, key, value);
	}

	@Override
	public void storageServiceFindAllDocuments(String dbName, String collectionName) {
		storage = storageService.findAllDocuments(dbName, collectionName);
	}

	@Override
	public void storageGetJsonDocList() {
		if (storage != null) {
			jsonDocList = storage.getJsonDocList();
		}
	}
	
	@Override
	public ArrayList<String> storageGetSaveNames() {
		ArrayList<String> saveNames = new ArrayList<String>();
		for (int i = 0; i < jsonDocList.size(); i++) {
			JSONObject json;
			try {
				json = new JSONObject(jsonDocList.get(i).getJsonDoc());
				saveNames.add(json.getString("name"));
			} catch (JSONException e) {
				HardVacuumReloaded.error(this.getClass().toString(), "error reading profile JSONObject", e);
			}
		}
		return saveNames;
	}
	
	@Override
	public void storageServiceInsertJSONDocument(String dbName, String collectionName, JSONObject json) {
		storageService.insertJSONDocument(dbName, collectionName, json);
	}
	
	@Override
	public void storageServiceUpdateDocumentByKeyValue(String dbName, String collectionName, String key, String value, JSONObject newJsonDoc) {
		storageService.updateDocumentByKeyValue(dbName, collectionName, key, value, newJsonDoc);
	}

	@Override
	public String storageGetJsonDoc() {
		return storage.getJsonDocList().get(0).getJsonDoc();
	}
	
	public UserService getUserService() {
		return userService;
	}

	public StorageService getStorageService() {
		return storageService;
	}

	public User getUser() {
		return user;
	}

	public Storage getStroage() {
		return storage;
	}
}
