package com.sopeng.instadown;

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
	private InstagramAPI api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		// Temp -- start login activity.
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), LoginView.class);
				intent.putExtra("loginurl", InstagramAPI.serv_auth_addr);
				startActivityForResult(intent, INSTALOGIN);
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				api.getPopular();
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
