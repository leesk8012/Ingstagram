package com.sopeng.instadown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class InstagramAPI
{
	private static final Object CLIENT_SECRET = "d9223750ce304811be17d85c5fb25a9c";
	private static final Object YOUR_REDIRECT_URI = "http://instagram.com";
	private static final String TAG = "API";
	private final String CLIENT_ID = "aa5dfb579b78421ca8fef6b150204dc6";
	private final String REDIRECT_URI = "http://instagram.com";

	public void getAccessToken(String code)
	{
		Map params = new HashMap<String, String>();
		params.put("client_id",CLIENT_ID);
		params.put("client_secret",CLIENT_SECRET);
	    params.put("grant_type","authorization_code");
	    params.put("redirect_uri",YOUR_REDIRECT_URI);
	    params.put("code",code);
	    String retStr = this.post("https://api.instagram.com/oauth/access_token", params);
	    Log.i(TAG,retStr);
	}
	
	
	
	public String post(String url, Map params, String encoding)
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
			e.printStackTrace();
		}
		finally
		{
			client.getConnectionManager().shutdown();
		}
		return "error";
	}

	private List<NameValuePair> convertParam(Map params)
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

	public String post(String url, Map params)
	{
		return post(url, params, "UTF-8");
	}
}
