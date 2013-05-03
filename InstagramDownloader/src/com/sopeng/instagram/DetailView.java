package com.sopeng.instagram;
//package com.sopeng.instadown;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class DetailView extends Activity
//{
//	private final String TAG = "DetailView";
//	private int index;
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.detail_view);
//		
//		// Intent get
//		Intent intent = new Intent(this.getIntent());
//		index = intent.getIntExtra("ImageIndex", 0);
//		Log.i(TAG,""+index);
//		TextView textView = (TextView) findViewById(R.id.textView1);
//		textView.setText(ProfileFileRepository.getInstance().getDatas().get(index).getFilepath());
//	
//		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
//		Bitmap bm = BitmapFactory.decodeFile(ProfileFileRepository.getInstance().getDatas().get(index).getFilepath());
//		imageView.setImageBitmap(bm);
//	}
//}
