package at.hid.hardvacuumreloaded.api;

import java.util.ArrayList;

import org.json.JSONObject;

public interface App42 {
	
	public void initialize(String apiKey, String secretKey);
	public void buildUserService();
	public void buildStorageService();
	public void getUser(String username);
	public void authenticate(String uName, String pwd);
	public void setSessionId(String sid);
	public void storageServiceFindDocumentByKeyValue(String dbName, String collectionName, String key, String value);
	public void storageServiceDeleteDocumentsByKeyValue(String dbName, String collectionName, String key, String value);
	public void storageServiceFindAllDocuments(String dbName, String collectionName);
	public void storageGetJsonDocList();
	public ArrayList<String> storageGetSaveNames();
	public void storageServiceInsertJSONDocument(String dbName, String collectionName, JSONObject json);
	public void storageServiceUpdateDocumentByKeyValue(String dbName, String collectionName, String key, String value, JSONObject newJsonDoc);
	public String storageGetJsonDoc();
}
