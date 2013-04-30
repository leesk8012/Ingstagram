package com.sopeng.instadown;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class InstagramAPI
{
	private static final String TAG = "InstagramAPI";

	private static final String CLIENT_ID = "aa5dfb579b78421ca8fef6b150204dc6";	
	private static final String CLIENT_SECRET = "d9223750ce304811be17d85c5fb25a9c";
	private static final String REDIRECT_URI = "http://instagram.com";
	private static final String YOUR_REDIRECT_URI = "http://instagram.com";
	
	public static final String clnt_auth_addr = "https://instagram.com/oauth/authorize/?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=token";
	public static final String serv_auth_addr = "https://api.instagram.com/oauth/authorize/?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
	
	private String accessToken;
	
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
		}
		catch (JSONException e)
		{
			Log.e(TAG,e.getMessage(),e);
		}
	    Log.i(TAG, "Access Token = "+accessToken);
	}
	
	public void getPopular()
	{
		String data = gets("https://api.instagram.com/v1/media/popular?access_token="+accessToken);
		Log.i(TAG,"GetPopular \n"+data);
	}
	
	public String gets(String url)
	{
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try
		{
			ResponseHandler<String> rh = new BasicResponseHandler();
			return client.execute(get, rh);
		}
		catch (ClientProtocolException e)
		{
			Log.e(TAG,e.getMessage(),e);
		}
		catch (IOException e)
		{
			Log.e(TAG,e.getMessage(),e);
		}
		return "error";
	}
	
	/**
	 * HTTP POST
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 */
	public String post(String url, Map<String, String> params, String encoding)
	{
		HttpClient client = new DefaultHttpClient();
		try
		{
			HttpPost post = new HttpPost(url);
			System.out.println("POST : " + post.getURI());
			List<NameValuePair> paramList = convertParam(params);
			post.setEntity(new UrlEncodedFormEntity(paramList, encoding));
			// Handler 에서 구체적으로 실행하는 것이 무엇인지 알아보아야 함.
			ResponseHandler<String> rh = new BasicResponseHandler();
			return client.execute(post, rh);
		}
		catch (Exception e)
		{
			Log.e(TAG,e.getMessage(),e);
		}
		finally
		{
			client.getConnectionManager().shutdown();
		}
		return "error";
	}

	/**
	 * POST 하기 위한 NameValuePair 로 변경함.
	 * @param params
	 * @return
	 */
	private List<NameValuePair> convertParam(Map<String, String> params)
	{
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext())
		{
			String key = keys.next();
			paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
		}
		return paramList;
	}
	
	/**
	 * HTTP POST
	 * @param url
	 * @param params
	 * @return
	 */
	public String post(String url, Map<String, String> params)
	{
		return post(url, params, "UTF-8");
	}
}
