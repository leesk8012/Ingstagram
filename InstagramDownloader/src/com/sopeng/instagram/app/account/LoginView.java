package com.sopeng.instagram.app.account;

import com.sopeng.instagram.R;
import com.sopeng.instagram.common.api.INLog;
import com.sopeng.instagram.common.api.InstagramAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginView extends Activity
{
	private final String TAG = "LoginView";
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
	
	// Service side. Step two.
	WebViewClient webClient = new WebViewClient()
	{
		private String code;
		
		public void terminate()
		{
			Intent intent = new Intent();
			intent.putExtra("code", code);
			setResult(RESULT_OK, intent);
			finish();
		}
		
		public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) 
		{
			INLog.d(TAG,"onPageStarted "+url);
			if(url.contains("code=")) 					// server side authenticate
			{
				view.stopLoading();
				code = url.substring(url.indexOf("code=")+5);
				terminate();
			}
		}
		
		public void onPageFinished(WebView view, String url)
		{
			INLog.d(TAG,"onPageFinished "+url);
			if(url.contains("code=")) 					// server side authenticate
			{
				code = url.substring(url.indexOf("code=")+5);
			}
		}
	};
}
