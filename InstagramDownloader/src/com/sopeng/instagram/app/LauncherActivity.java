package com.sopeng.instagram;

import com.sopeng.instagram.app.account.LoginView;
import com.sopeng.instagram.common.api.INLog;
import com.sopeng.instagram.common.api.InstagramAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LauncherActivity extends Activity
{
	private final String TAG = "InstaLauncherActivity";
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
		
		// Server side Flow. Step one. 
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
					String codeStr = intent.getStringExtra("code");					
					INLog.i(TAG,"Login Success Code : "+codeStr);
					AuthTask authTask = new AuthTask();
					authTask.execute(codeStr);
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
			INLog.i(TAG, "PageFinished "+url);
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
	
	class AuthTask extends AsyncTask<String, Void, Void>
	{

		@Override
		protected Void doInBackground(String... params)
		{
			InstagramAPI api = new InstagramAPI();			
			api.getAccessToken(params[0]);
			return null;
		}
		@Override
		protected void onPostExecute(Void result)
		{
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
			startActivityForResult(intent, INSTAMENU);
			super.onPostExecute(result);
		}
	}
}
