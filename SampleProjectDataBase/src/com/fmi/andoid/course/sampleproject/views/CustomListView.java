package com.fmi.andoid.course.sampleproject.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

import com.fmi.andoid.coruse.sampleproject.db.R;

public class CustomListView extends ListView {

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		
		Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
				R.drawable.add_user_image);
		int right = getRight();
		int bottom = getBottom();
		int top = getTop();
		int left = getLeft();
		BitmapDrawable drawable = new BitmapDrawable(getResources(), largeIcon);
		Rect rect = new Rect(left, top, right, bottom);
		drawable.setBounds(rect);
		drawable.draw(canvas);
	}
}
