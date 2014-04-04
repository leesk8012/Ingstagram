/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.sopeng.instagram.common.imageview;

import org.json.JSONException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sopeng.instagram.R;
import com.sopeng.instagram.common.api.InstagramAPI;
import com.sopeng.instagram.common.imageview.Constants.Extra;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @modified by leesk.
 * 
 */
public class ImageGridActivity extends AbsListViewBaseActivity
{
	String[] imageUrls;
	DisplayImageOptions options;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_grid);
		
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error)
				.cacheInMemory().cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();

		adapter = new ImageAdapter();
		listView = (GridView) findViewById(R.id.gridview);
		((GridView) listView).setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				startImagePagerActivity(position);
			}
		});
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		loadPictureTask pictures = new loadPictureTask();
		pictures.execute();
		super.onResume();
	}
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	/**
	 * Detail View.
	 * @param position
	 */
	private void startImagePagerActivity(int position)
	{
		Intent intent = new Intent(this, ImagePagerActivity.class);
		intent.putExtra(Extra.IMAGES, imageUrls);
		intent.putExtra(Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}

	public class ImageAdapter extends BaseAdapter
	{
		@Override
		public int getCount()
		{
			if(imageUrls == null)
				return 0;
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position)
		{
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			final ImageView imageView;
			if (convertView == null)
			{
				imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_grid_image,
						parent, false);
			}
			else
			{
				imageView = (ImageView) convertView;
			}

			imageLoader.displayImage(imageUrls[position], imageView, options);

			return imageView;
		}
	}

	private final String TAG = "gridView";
	private ImageAdapter adapter;
		
	/**
	 * retrieve a pictures of instagram profile.
	 * @author leesk
	 *
	 */
	class loadPictureTask extends AsyncTask<Void, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(Void... arg0)
		{
			InstagramAPI api = new InstagramAPI();
			String tempFollow[] = null;
			try
			{
				tempFollow = api.getFollowings();
			}
			catch (JSONException e)
			{
				Log.e(TAG,e.getMessage());
				return false;
			}
			imageUrls = tempFollow;
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result)
		{
			if(result)
				adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
	}
}