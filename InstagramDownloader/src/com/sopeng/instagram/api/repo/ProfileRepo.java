package com.sopeng.instagram.api.repo;

import java.util.Vector;

import org.json.JSONObject;


public class ProfileRepo
{
	private static ProfileRepo infoRepository;
	private Vector<ProfilePictureData> datas;
	
	// Áß¿ä.
//	private String accessToken;
//	private JSONObject selfJSON;

	private ProfileRepo()
	{
		datas = new Vector<ProfilePictureData>();
	}
	
	public static ProfileRepo getInstance()
	{
		if(infoRepository == null)
		{
			infoRepository = new ProfileRepo();
		}
		return infoRepository;
	}
	
	public void add(String userID, String url)
	{
		if(datas != null)
		{
			datas.add(new ProfilePictureData(userID, url));
		}
	}
	
	public void removeAll()
	{
		datas = new Vector<ProfilePictureData>();
	}
	
	public Vector<ProfilePictureData> getDatas()
	{
		return datas;
	}

//	public String getAccessToken()
//	{
//		return accessToken;
//	}
//
//	public void setAccessToken(String accessToken)
//	{
//		this.accessToken = accessToken;
//	}
//
//	public JSONObject getSelfJSON()
//	{
//		return selfJSON;
//	}
//
//	public void setSelfJSON(JSONObject selfJSON)
//	{
//		this.selfJSON = selfJSON;
//	}
}
