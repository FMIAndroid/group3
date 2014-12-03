package com.fmi.andoid.coruse.sampleproject.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomButton extends Button {

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.RED);
		paint.setTextSize(30);
		

}

	
	private Paint paint;

	@Override
	protected void onDraw(Canvas canvas) {
		
		
		RectF rectF = new RectF(0, 0, getWidth(), getHeight());

		canvas.drawOval(rectF, paint);
		//canvas.drawText("Register", 30, 50, paint);
		
		
		 int xPos = (canvas.getWidth()/5);
		 int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ; 

		 canvas.drawText("Register", xPos, yPos, paint);
	}
}
