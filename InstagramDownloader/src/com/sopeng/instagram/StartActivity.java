package com.sopeng.instagram;

import com.sopeng.instagram.api.InstagramAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StartActivity extends Activity
{
	private final String TAG = "Ingstagram StartScreen";
	private static final int INSTALOGIN = 0;
	private static final int INSTAMENU = 1;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_screen);
		
		webView = (WebView) findViewById(R.id.dummyWebView);			
		webView.setWebViewClient(webClient);
		webView.loadUrl(InstagramAPI.serv_auth_addr);
	}
	
	private void startLoginView()
	{
		Intent intent = new Intent(getApplicationContext(), LoginView.class);
		startActivityForResult(intent, INSTALOGIN);		
	}
	
	private void startMenuView(String url)
	{
		String code = url.substring(url.indexOf("code=")+5);
		InstagramAPI api = new InstagramAPI();
		api.getAccessToken(code);
		Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
		startActivityForResult(intent, INSTAMENU);	
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);	 
		switch(requestCode)
		{
			case INSTALOGIN:
				if(resultCode == RESULT_OK)	// Login
				{
					InstagramAPI api = new InstagramAPI();
					String codeStr = intent.getStringExtra("code");
					
					Log.i(TAG,"Login Success Code : "+codeStr);
					// FIXME Thread 로 변경할 필요가 있음. --> AsyncTask.
					api.getAccessToken(codeStr);
					// menu 호출.
					intent = new Intent(getApplicationContext(), MenuActivity.class);
					startActivityForResult(intent, INSTAMENU);
				}
				else	// Exit
				{
					finish();
				}
				break; 
			case INSTAMENU:
				if(intent == null)	// Exit
				{
					finish();
				}
				else	// Logout
				{
					webView.loadUrl(InstagramAPI.logout_addr);
				}
				break;
		}
	}
	
	WebViewClient webClient = new WebViewClient()
	{
		final String TAG = "StartActivity Web";
		public void onPageFinished(WebView view, String url) 
		{
			Log.i(TAG, "PageFinished "+url);
			if(url.contains("code="))	// Login succeed.
			{
				startMenuView(url);
			}
			else
			{
				startLoginView();
			}
		}
	};
}
