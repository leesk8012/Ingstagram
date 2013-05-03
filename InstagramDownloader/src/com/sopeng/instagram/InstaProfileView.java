package com.sopeng.instagram;
/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import org.json.JSONException;

import com.sopeng.instadown.R;
import com.sopeng.instagram.api.InstagramAPI;
import com.sopeng.instagram.api.repo.ProfileRepo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * A grid that displays a set of framed photos.
 * 
 */
public class InstaProfileView extends Activity
{
	private String TAG = "InstaProfileView";
	
	private Vector<Bitmap> bitmaps;
	
	private final int imgvWidth = 110; 
	private final int imgvHeight = 110; 
	
	private ImageAdapter adapter;
	private InstagramAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_2);

		api = new InstagramAPI();
		try
		{
			api.getFollowings();
		}
		catch (JSONException e)
		{
			Log.e(TAG,e.getMessage(),e);
		}
		
		GridView g = (GridView) findViewById(R.id.myGrid);
		adapter = new ImageAdapter(getApplicationContext());
		g.setAdapter(adapter);
		g.setOnItemClickListener(itemClickListener);
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
		{
			// TODO Image Open
//			Intent intent = new Intent(getApplicationContext(), DetailView.class);
//			intent.putExtra("ImageIndex", position);
//			startActivity(intent);
		}
	};
	
	@Override
	protected void onResume()
	{
		super.onResume();
	}
	@Override
	protected void onPause()
	{
		super.onPause();
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
	
	/***
	* Method reference 
	* http://thinkandroid.wordpress.com/2009/12/25/converting-image-url-to-bitmap/
	*/
	private static Bitmap getBitmapFromURL(String src)
	{
		try
		{
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			Bitmap myBitmap = BitmapFactory.decodeStream(input,null,options);
			return resizeBitmap(myBitmap, 100);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	// Resize along to width.
	private static Bitmap resizeBitmap(Bitmap bitmap, int limitWid)
	{
		double width = bitmap.getWidth();	
		if(width == limitWid)
			return bitmap;
		else
		{
			double resizeRatio = (limitWid - width) / width;
			int changedWidth = (int) (width * ( 1.0 + resizeRatio));
			int changedHeight = (int) (bitmap.getHeight() * ( 1.0 + resizeRatio));
			return Bitmap.createScaledBitmap(bitmap, changedWidth, changedHeight, false);
		}
	}
	
	// Adapter TODO
	public class ImageAdapter extends BaseAdapter
	{
		public ImageAdapter(Context c)
		{
			mContext = c;
		}
		// 현재 Item 의 개수.
		public int getCount()
		{
			return ProfileRepo.getInstance().getDatas().size();
		}
		// Item(Bitmap) 가져옴.
		public Object getItem(int position)
		{
			String url = ProfileRepo.getInstance().getDatas().get(position).getPicURL();
			return getBitmapFromURL(url);
		}
		// Item ID 는 위치
		public long getItemId(int position)
		{
			return position;
		}
		// 각 위치에 ImageView 불러옴
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView imageView;
			if (convertView == null)
			{
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(imgvWidth, imgvHeight));
				imageView.setAdjustViewBounds(false);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			}
			else
			{
				imageView = (ImageView) convertView;
			}
			
			Bitmap bm;
			if(bitmaps == null)
			{
				
			}
			else
			{
				bm = bitmaps.get(position);
			}
			
			// TODO
			bm = getBitmapFromURL(ProfileRepo.getInstance().getDatas().get(position).getPicURL()); // = ProfileFileRepository.getInstance().getDatas().elementAt(position).getBitmap();
			if(bm != null)
			{
				imageView.setImageBitmap(bm);
			}
			return imageView;
		}
		private Context mContext;
	}

}
