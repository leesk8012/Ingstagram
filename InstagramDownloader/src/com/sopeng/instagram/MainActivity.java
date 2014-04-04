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
 * 처음으로 시작하는 Activity.
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
		// UI 와 분리 필요.
		// 앱 시작시 인증 필요함. -- 웹뷰 로딩전에는 이미지 화면 하나 필요.
		Intent intent = new Intent(getApplicationContext(), LoginView.class);
		intent.putExtra("loginurl", InstagramAPI.serv_auth_addr);
		startActivityForResult(intent, INSTALOGIN);
		
		// TODO Follow
		// 팔로잉하는 유저의 프로필 사진
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
	 * 로그인 성공일 경우, Key 를 얻음
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
