package com.sopeng.instagram;

import org.json.JSONException;

import com.sopeng.instadown.R;
import com.sopeng.instagram.api.InstagramAPI;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{
	private final String TAG = "MAIN";
	private static final int INSTALOGIN = 0;
	private static final int INSTAPROFILE = 1;
	
	private InstagramAPI api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		// �� ���۽� ���� �ʿ���. -- ���� �ε������� �̹��� ȭ�� �ϳ� �ʿ�.
		Intent intent = new Intent(getApplicationContext(), LoginView.class);
		intent.putExtra("loginurl", InstagramAPI.serv_auth_addr);
		startActivityForResult(intent, INSTALOGIN);
		
		
		// Temp -- start login activity.
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				try
				{
//					api.getSelfFeed();
					Intent intent = new Intent(getApplicationContext(), InstaProfileView.class);
					startActivityForResult(intent, INSTAPROFILE);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				try
				{
					api.getPopularFeed();
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);	 
		switch(requestCode)
		{
			case INSTALOGIN: // requestCode�� B_ACTIVITY�� ���̽�
				if(resultCode == RESULT_OK)
				{
					String codeStr = intent.getStringExtra("code");
					Log.i(TAG,"coddd "+codeStr);
					api = new InstagramAPI();
					api.getAccessToken(codeStr);
					// ������ �Ϸ�� ����
					
				}
				break;
		}
	}
}
