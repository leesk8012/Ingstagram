package com.sopeng.instagram;

import com.sopeng.instagram.app.account.LoginView;
import com.sopeng.instagram.common.api.INLog;
import com.sopeng.instagram.common.api.InstagramAPI;
import com.sopeng.instagram.common.imageview.BaseActivity;
import com.sopeng.instagram.common.imageview.ImageGridActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * ó������ �����ϴ� Activity.
 * 
 * @author leesk
 *
 */
public class MainActivity extends BaseActivity
{
	private final String TAG = "MAIN";
	private static final int INSTALOGIN = 0;
	
	private InstagramAPI api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		api = new InstagramAPI();
		// UI �� �и� �ʿ�.
		// �� ���۽� ���� �ʿ���. -- ���� �ε������� �̹��� ȭ�� �ϳ� �ʿ�.
		Intent intent = new Intent(getApplicationContext(), LoginView.class);
		intent.putExtra("loginurl", InstagramAPI.serv_auth_addr);
		startActivityForResult(intent, INSTALOGIN);
		
		// TODO Follow
		// �ȷ����ϴ� ������ ������ ����
		Button profileButton = (Button) findViewById(R.id.button_profile);
		profileButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
//					String [] arr = api.getFollowings();
					Intent intent = new Intent(getApplicationContext(), ImageGridActivity.class);
//					intent.putExtra(Extra.IMAGES, arr);
					startActivity(intent);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		// TODO Popular
		Button popularButton = (Button) findViewById(R.id.button_popular);
		popularButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
//				try
//				{
//					// TODO
//					api.getPopularFeed();
//					Intent intent = new Intent(getApplicationContext(), ImageGridActivity.class);
//					intent.putExtra(Extra.IMAGES, InstagramMediaRepo.getInstance().getThumbPics());
//					startActivity(intent);
//				}
//				catch (JSONException e)
//				{
//					Log.e(TAG,e.getMessage(),e);
//				}
			}
		});
	}

	/**
	 * �α��� ������ ���, Key �� ����
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);	 
		switch(requestCode)
		{
			case INSTALOGIN:
				if(resultCode == RESULT_OK)
				{
					String codeStr = intent.getStringExtra("code");
					INLog.i(TAG,"Login Success Code : "+codeStr);
					api.getAccessToken(codeStr);
				}
				break;
		}
	}
}
