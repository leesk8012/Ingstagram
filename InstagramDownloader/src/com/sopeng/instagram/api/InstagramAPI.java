package com.sopeng.instagram.api;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sopeng.instagram.api.repo.ProfileRepo;

import android.util.Log;

public class InstagramAPI extends HTTPReader
{
	private static final String TAG = "InstagramAPI";

	private static final String CLIENT_ID = "aa5dfb579b78421ca8fef6b150204dc6";	
	private static final String CLIENT_SECRET = "d9223750ce304811be17d85c5fb25a9c";
	private static final String REDIRECT_URI = "http://instagram.com";
	private static final String YOUR_REDIRECT_URI = "http://instagram.com";
	
	public static final String clnt_auth_addr = "https://instagram.com/oauth/authorize/?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=token";
	public static final String serv_auth_addr = "https://api.instagram.com/oauth/authorize/?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
	
	public static String accessToken;
	public static JSONObject selfJSON;
	
	/**
	 * Code 를 통해서 Access Token 얻음.
	 * @param code
	 */
	public void getAccessToken(String code)
	{
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("client_id",CLIENT_ID);
		params.put("client_secret",CLIENT_SECRET);
	    params.put("grant_type","authorization_code");
	    params.put("redirect_uri",YOUR_REDIRECT_URI);
	    params.put("code",code);
	    String temp = this.post("https://api.instagram.com/oauth/access_token", params);
	    
	    try
		{
			JSONObject jsonObject = new JSONObject(temp);
			accessToken = jsonObject.getString("access_token");
			
//			printKey(jsonObject);
			selfJSON = jsonObject.getJSONObject("user");
			printKey(selfJSON);
		}
		catch (JSONException e)
		{
			Log.e(TAG,e.getMessage(),e);
		}
	    Log.i(TAG, "Access Token = "+accessToken);
	}
	
	public void getPopularFeed() throws JSONException
	{
		String data = gets("https://api.instagram.com/v1/media/popular?access_token="+accessToken);
//		Log.i(TAG,"GetPopular \n"+data);
		printKeys(data);
	}

	public void getSelfFeed() throws JSONException
	{
		String data = gets("https://api.instagram.com/v1/users/self/feed?access_token="+accessToken);
//		Log.i(TAG,"Self feed \n"+data);
//		printKeys(data);
		getData(data);
	}
	
	public void getFollowings() throws JSONException
	{
		String userid, url;
		String data = gets("https://api.instagram.com/v1/users/"+getUserID()+"/follows?access_token="+accessToken);
		JSONObject jsonObject = new JSONObject(data);
		JSONArray array = jsonObject.getJSONArray("data");
		int length = array.length();
		for(int i=0;i<length;i++)
		{
			jsonObject = array.getJSONObject(i);
			if(jsonObject != null)
			{
				userid = jsonObject.getString("id");
				url = jsonObject.getString("profile_picture");
				Log.i(TAG,userid+" "+url);
				ProfileRepo.getInstance().add(userid, url);
			}
		}
	}
	
	
	//============= HELPER ==============//
	private String getUserID() throws JSONException
	{
		return selfJSON.getString("id");
	}
	
	
	private void getData(String input) throws JSONException
	{
		JSONObject jsonObject = new JSONObject(input);
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		int length = jsonArray.length();
		printKey(jsonArray.getJSONObject(0));
		for(int i=0;i<length;i++)
		{
			jsonObject = jsonArray.getJSONObject(i);
			
		}
	}
	
	// Debug
	private void printKey(JSONObject jsonObject)
	{
		Iterator<?> iterator = jsonObject.keys();
		while(iterator.hasNext())
		{
			Log.i(TAG,"Key "+iterator.next());
		}
	}
	// Debug
	private void printKeys(String data) throws JSONException
	{
		JSONObject jsonObject = new JSONObject(data);
		printKey(jsonObject);
	}
}
