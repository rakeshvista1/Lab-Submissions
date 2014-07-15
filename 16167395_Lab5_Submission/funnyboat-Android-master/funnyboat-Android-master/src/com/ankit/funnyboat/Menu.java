package com.ankit.funnyboat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

public abstract class Menu implements GameModule {
	public abstract String[] getNames();

	public abstract void call(int i);

	String[] names;
	static Paint textPaint = new Paint();
	static Paint smallTextPaint = new Paint();
	Rect tmp;
	static Matrix ident = new Matrix();
	static Typeface tf;
	static int startY = 100;
	static Bitmap logo;

	static void init() {
		tf = Typeface.createFromAsset(Game.app.getAssets(), "Cosmetica.ttf");
		logo = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				Game.app.getResources(), R.drawable.logo), 240, 100, true);
		textPaint.setARGB(0xff, 0x0, 0x0, 0x50);
		textPaint.setTextSize(30);
		textPaint.setTypeface(tf);
		textPaint.setAntiAlias(true);
		smallTextPaint.setARGB(0xff, 0x0, 0x0, 0x0);
		smallTextPaint.setTextSize(20);
		smallTextPaint.setTypeface(tf);
		smallTextPaint.setAntiAlias(true);
	}

	{
		names = getNames();
		lastT = System.currentTimeMillis();
	}
	long lastT;

	public void tick() {
		GameObject.DELTA = (float) ((System.currentTimeMillis() - lastT) / 1000.);
		lastT = System.currentTimeMillis();
		int center = Game.water.width / 2;
		int y = startY;

		Matrix m = Game.canvas.getMatrix();
		Game.canvas.setMatrix(ident);
		Game.canvas.drawBitmap(logo, center - 120, 10, textPaint);
		for (String n : names) {
			y += 50;
			int width = (int) textPaint.measureText(n);
			int x = center - width / 2;

			Game.canvas.drawText(n, x, y, textPaint);
		}
		Game.canvas.drawText("@Electromedica.in Ankit Prasad", 20,
				Game.water.height - 30, smallTextPaint);
		Game.canvas.setMatrix(m);
	}

	@Override
	public void touch(MotionEvent ev) {
		System.err.println("ev " + ev + " " + ev.getAction() + " "
				+ MotionEvent.ACTION_DOWN + " " + ev.getY());
		if ((ev.getAction() & MotionEvent.ACTION_DOWN) != 0
				|| ev.getAction() == MotionEvent.ACTION_DOWN) {
			System.err.println("touch! " + ev.getY());
			int item = (int) ((ev.getY() - 60) / startY);
			if (item >= 0 && item < names.length)
				call(item);
		}
	}
}
