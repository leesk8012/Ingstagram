package com.sopeng.instagram;

import com.sopeng.instagram.api.InstagramAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginView extends Activity
{
	private final String TAG = "InstaLogin";
	private static WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insta_login);
				
		webView = (WebView) findViewById(R.id.loginView);			
		webView.getSettings().setSavePassword(false);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(webClient);
		webView.loadUrl(InstagramAPI.serv_auth_addr);
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
	}
	
	WebViewClient webClient = new WebViewClient()
	{
		public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) 
		{
			Log.i(TAG,"onPageStarted "+url);
			if(url.contains("code=")) 					// server side authenticate
			{
				view.stopLoading();
				String code = url.substring(url.indexOf("code=")+5);
				Intent intent = new Intent();
				intent.putExtra("code", code);
				setResult(RESULT_OK, intent);
				finish();
			}
		}
	};
}
