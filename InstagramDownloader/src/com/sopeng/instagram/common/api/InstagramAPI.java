package com.sopeng.instagram.common.api;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sopeng.instagram.common.api.repo.InstagramMediaRepo;

import android.os.Handler;

public class InstagramAPI extends HTTPReader
{
	private final String TAG = "InstagramAPI";
	private static final String CLIENT_ID = "f0c8cfa947474700b085b51c342050c6";	
	private static final String CLIENT_SECRET = "076c23f972c74b0cbef61cef7588c6af";
	public static final String REDIRECT_URI = "http://instagram.com";
	private static final String YOUR_REDIRECT_URI = "http://instagram.com";
	
	public static final String clnt_auth_addr = "https://instagram.com/oauth/authorize/?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=token";
	public static final String serv_auth_addr = "https://api.instagram.com/oauth/authorize/?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
	public static final String logout_addr = "https://instagram.com/accounts/logout/";
	
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
			selfJSON = jsonObject.getJSONObject("user");
			printKeys(selfJSON);
		}
		catch (JSONException e)
		{
			INLog.e(TAG,e.getMessage(),e);
		}
	    INLog.i(TAG, "Access Token = "+accessToken);
	}
	
	public void logout()
	{
		gets(logout_addr);
	}
	
	/**
	 * TODO
	 * 현재 제일 잘나가는 피드 모음.
	 * @throws JSONException
	 */
	public void getPopularFeed(Handler handler) throws JSONException
	{
		String data = gets("https://api.instagram.com/v1/media/popular?access_token="+accessToken);
		JSONObject jsonObject = new JSONObject(data);
		JSONArray array = jsonObject.getJSONArray("data");
		int length = array.length();
		InstagramMediaRepo.getInstance().removeAll();
		for(int i=0;i<length;i++)
		{
			jsonObject = array.getJSONObject(i);
			if(jsonObject != null)
			{
				String id = jsonObject.getJSONObject("user").getString("id");
				String profile_pic = jsonObject.getJSONObject("user").getString("profile_picture");
				String thumb = jsonObject.getJSONObject("images").getJSONObject("thumbnail").getString("url");
				String standard = jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
				String low = jsonObject.getJSONObject("images").getJSONObject("low_resolution").getString("url");
				
				// FIXME
				InstagramMediaRepo.getInstance().addProfile(id, profile_pic, thumb, low, standard);
				
				
				handler.sendMessage(null);
				
//				Log.i(TAG,"-- Image["+i+"]---");
//				Log.i(TAG,"thumbnail "+thumb);
//				Log.i(TAG,"standard "+standard);
//				Log.i(TAG,"low "+low);
				
			}
		}
	}

	// TODO
	public void getSelfFeed() throws JSONException
	{
		String data = gets("https://api.instagram.com/v1/users/self/feed?access_token="+accessToken);
//		Log.i(TAG,"Self feed \n"+data);
//		printKeys(data);
		getData(data);
	}
	
	public String[] getFollowings() throws JSONException
	{
		String userid, url;
		String data = gets("https://api.instagram.com/v1/users/"+getSelfUserID()+"/follows?access_token="+accessToken);
		INLog.d(TAG, "data "+data);
		JSONObject jsonObject = new JSONObject(data);
		JSONArray array = jsonObject.getJSONArray("data");
		int length = array.length();
		String [] imageURL = new String[length];
		for(int i=0;i<length;i++)
		{
			jsonObject = array.getJSONObject(i);
			if(jsonObject != null)
			{
				userid = jsonObject.getString("id");
				url = jsonObject.getString("profile_picture");
				INLog.i(TAG,userid+" "+url);
//				ProfileRepo.getInstance().add(userid, url);
				imageURL[i] = url;
			}
		}
		return imageURL;
	}
	
	
	//============= HELPER ==============//
	private String getSelfUserID() throws JSONException
	{
		if(selfJSON != null)
			return selfJSON.getString("id");
		else
			return null;
	}
	
	
	private void getData(String input) throws JSONException
	{
		JSONObject jsonObject = new JSONObject(input);
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		int length = jsonArray.length();
		printKeys(jsonArray.getJSONObject(0));
		for(int i=0;i<length;i++)
		{
			jsonObject = jsonArray.getJSONObject(i);
			
		}
	}
	
	// Debug
	private void printKeys(JSONObject jsonObject)
	{
		String key;
		Iterator<?> iterator = jsonObject.keys();
		while(iterator.hasNext())
		{
			key = (String) iterator.next();
			try
			{
				jsonObject.getJSONArray(key);
			}
			catch (JSONException e)
			{
				INLog.i(TAG,"Key "+key);
				continue;		
			}
			INLog.i(TAG,"Key "+key+" [jsonArray]");
		}
	}
	// Debug
	private void printKeys(String data) throws JSONException
	{
		JSONObject jsonObject = new JSONObject(data);
		printKeys(jsonObject);
	}
}
