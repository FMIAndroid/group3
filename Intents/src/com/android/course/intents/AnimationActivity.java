package com.android.course.intents;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class AnimationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation);
		final ImageView imageView = (ImageView) findViewById(R.id.animation);
		
		android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(null);
                imageView.setBackgroundResource(R.drawable.animated_cat);
                AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getBackground();
                frameAnimation.start();

            }
        }, 200);
	}
}
