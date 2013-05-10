package com.sopeng.instagram;

import org.json.JSONException;

import com.sopeng.instagram.Constants.Extra;
import com.sopeng.instagram.api.InstagramAPI;
import com.sopeng.instagram.api.repo.InstagramMediaRepo;
import com.sopeng.instagram.imageview.BaseActivity;
import com.sopeng.instagram.imageview.ImageGridActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
		profileButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
					Intent intent = new Intent(getApplicationContext(), ImageGridActivity.class);
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
				try
				{
					api.getPopularFeed();
					Intent intent = new Intent(getApplicationContext(), ImageGridActivity.class);
					intent.putExtra(Extra.IMAGES, InstagramMediaRepo.getInstance().getThumbPics());
					startActivity(intent);
				}
				catch (JSONException e)
				{
					Log.e(TAG,e.getMessage(),e);
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
