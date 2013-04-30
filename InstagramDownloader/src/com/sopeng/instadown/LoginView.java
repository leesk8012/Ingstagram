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
	
	WebViewClient webClient = new WebViewClient()
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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insta_login);
		intent = getIntent();
		String serv_addr = intent.getStringExtra("loginurl");
		intent = new Intent(); 
		WebView webView = (WebView) findViewById(R.id.loginView);
//		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(webClient);
		webView.loadUrl(serv_addr);
	}
}
