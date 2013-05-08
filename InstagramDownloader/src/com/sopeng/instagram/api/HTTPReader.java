package com.sopeng.instagram.api;

import java.io.IOException;
import java.util.ArrayList;
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

import android.util.Log;

public class HTTPReader
{
	private final String TAG = "HTTPReader";
	
	/**
	 * HTTP Get
	 * @param url
	 * @return
	 */
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
//			System.out.println("POST : " + post.getURI());
			if(params != null)
			{
				List<NameValuePair> paramList = convertParam(params);
				post.setEntity(new UrlEncodedFormEntity(paramList, encoding));
			}
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
