package com.ankit.funnyboat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;

public class Water {
	Paint waterPaint = new Paint();
	Paint skyPaint = new Paint();
	float time = 0;

	int amplitude = 40;
	int waveHeight = 120;
	double waveLength = 170 * 6.0;
	double waveTime = 1 * 6.0;

	final double getWaveSpeed() {
		return waveLength / waveTime;
	}

	Bitmap[] clouds = new Bitmap[4];
	float[] cloudsX = new float[4];
	int[] cloudsVX = new int[] { 20, 30, 40, 50 };

	int width = 480;
	int height = 800;
	{
		clouds[0] = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.cloud1);
		clouds[1] = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.cloud2);
		clouds[2] = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.cloud3);
		clouds[3] = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.cloud4);
		cloudsX[0] = 30;
		cloudsX[1] = 430;
		cloudsX[2] = 40;
		cloudsX[3] = 500;
		skyPaint.setARGB(0xff, 0xa0, 0xa0, 0xe0);
		waterPaint.setARGB(0xff, 100, 110, 200);
	}

	void tick() {
		time += GameObject.DELTA;
		Game.canvas.drawRect(new Rect(0, 0, width, height), skyPaint);
		for (int i = 0; i < 4; i++) {
			cloudsX[i] += cloudsVX[i] * GameObject.DELTA;
			cloudsX[i] %= width * 1.3 + 100;
			Game.canvas.drawBitmap(clouds[i], cloudsX[i] - 120, height - 60,
					null);
		}
		int skip = 2;
		for (int x = 0; x < width; x += skip) {
			int y = getAt(x);
			Game.canvas.drawRect(x, 0, x + skip, y, waterPaint);
		}
		// Game.canvas.drawLine(20, 20, 50, 50, waterPaint);
	}

	final int getAt(int x) {
		double val = Math.sin((time / waveTime + x / waveLength) * 2 * Math.PI);
		return (int) (val * amplitude) + waveHeight;
	}
}
