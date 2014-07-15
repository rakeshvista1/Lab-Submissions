package com.ankit.funnyboat;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public abstract class GameObject {
	abstract Bitmap getImage();

	float x = 20, y;
	float vx, vy;
	int w, h;
	boolean enemy;
	boolean special;
	static float DELTA = 0.04f;
	boolean waterUp = true;

	{
		y = Game.water.getAt((int) x) + 30;
	}

	public void tick() {
		if (w == 0) {
			w = getImage().getWidth();
			h = getImage().getHeight();
		}
		int waterY = Game.water.getAt((int) x) - 5;
		int dy = (int) (waterY - y);
		if (Math.abs(dy) < 10 && (vy + vx) < 10 && waterUp) {
			y = waterY;
			vy = 0;
		} else {
			vy += 100 * DELTA
					* (waterUp ? (dy < 0 ? -2 : 4) : (dy < 0 ? -1 : 0));
			y += vy * DELTA;
		}
		x += vx * DELTA;
		vy *= 0.9;
		Game.canvas.drawBitmap(getImage(), x, y, null);
	}

	void jump() {
		int waterY = Game.water.getAt((int) x) - 5;
		int dy = (int) (waterY - y);
		if (Math.abs(dy) < 10 && (vy) < 10 && waterUp) {
			vy = 210;
		}
	}

	boolean kill() {
		if (waterUp) {
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			Bitmap original = getImage();
			Bitmap rotated = Bitmap.createBitmap(original, 0, 0,
					original.getWidth(), original.getHeight(), matrix, true);
			GameObject body = new DeathBody(rotated);
			body.x = x;
			body.y = y;
			Gameplay.current.objects.add(body);
		}
		return true;
	}

}
