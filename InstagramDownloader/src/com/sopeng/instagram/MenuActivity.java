package com.sopeng.instagram;

import org.json.JSONException;

import com.sopeng.instagram.app.imageview.ProfilePicsActivity;
import com.sopeng.instagram.common.api.INLog;
import com.sopeng.instagram.common.api.InstagramAPI;
import com.sopeng.instagram.common.api.repo.InstagramMediaRepo;
import com.sopeng.instagram.common.imageview.BaseActivity;
import com.sopeng.instagram.common.imageview.ImageGridActivity;
import com.sopeng.instagram.common.imageview.Constants.Extra;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends BaseActivity
{
	private final String TAG = "MAIN";
	private InstagramAPI api;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_main_menu);
		api = new InstagramAPI();		
		
		// TODO Follow
		// 팔로잉하는 유저의 프로필 사진
		Button profileButton = (Button) findViewById(R.id.button_profile);
		profileButton.setOnClickListener(profileClicked);
		// TODO Popular
		Button popularButton = (Button) findViewById(R.id.button_popular);
		popularButton.setOnClickListener(popularClicked);
	}


	OnClickListener profileClicked = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			try
			{
				Intent intent = new Intent(getApplicationContext(), ProfilePicsActivity.class); //ProfilePicsActivity.class);
				startActivity(intent);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	};
	
	OnClickListener popularClicked = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			try
			{
				api.getPopularFeed(handler);
				
				Intent intent = new Intent(getApplicationContext(), ImageGridActivity.class);
				intent.putExtra(Extra.IMAGES, InstagramMediaRepo.getInstance().getThumbPics());
				startActivity(intent);
			
			}
			catch (JSONException e)
			{
				INLog.e(TAG,e.getMessage(),e);
			}
		}
	};
	
	static Handler handler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			
		}
	};
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
			case R.id.item_logout:
				intent = new Intent();
				intent.putExtra("LOG", 1);
				setResult(RESULT_OK, intent);
				finish();
				break;
			case R.id.item_about_app:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// FIXME
	@Override
	public void onBackPressed() 
	{
		imageLoader.stop();
		super.onBackPressed();
	}
}
