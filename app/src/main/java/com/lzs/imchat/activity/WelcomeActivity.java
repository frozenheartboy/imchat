package com.lzs.imchat.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.lzs.imchat.MainActivity;
import com.lzs.imchat.R;

public class WelcomeActivity extends Activity {
	private Context mContext;
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mContext = this;
		findView();
		init();
	}
	private void findView() {
		mImageView = (ImageView) findViewById(R.id.img_welcome);
	}

	private void init() {
		mImageView.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(mContext, MainActivity.class);
				startActivity(intent);
				finish();
			}
		},1000);
		
	}
}
