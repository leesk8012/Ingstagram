package com.sopeng.instadown;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginView extends Activity
{
	private final String TAG = "InstaLogin";
	private Intent intent;
	
	WebViewClient client = new WebViewClient()
	{

		public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) 
		{
			Log.i(TAG,"onPageStarted "+url);
		
			// Access token 얻기.
			if(url.contains("#access_token="))
			{
				String tmp = url.substring(url.indexOf("#access_token="));
				Log.i(TAG,"acctok "+tmp);
				intent.putExtra("token", tmp);
				setResult(RESULT_OK, intent);
				finish();
			}
			// Code 얻기
			else if(url.contains("code="))
			{
				String tmp = url.substring(url.indexOf("code=")+5);
				Log.i(TAG,"Code "+tmp);
				intent.putExtra("code", tmp);
				setResult(RESULT_OK, intent);
				finish();
			}
		}
		
		public void onPageFinished(WebView view, String url) 
		{
			Log.i(TAG,"onPageFinished "+url);
		}
		
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) 
		{
			Log.i(TAG,"onReceivedError "+failingUrl);
		}
	};
	
	final String CLIENT_ID = "aa5dfb579b78421ca8fef6b150204dc6";
	final String REDIRECT_URI = "http://instagram.com";
	String addr = "https://instagram.com/oauth/authorize/?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=token";
	String serv_addr = "https://api.instagram.com/oauth/authorize/?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insta_login);
		intent = new Intent(); 
		WebView webView = (WebView) findViewById(R.id.loginView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(client);
		webView.loadUrl(serv_addr);
	}
}
