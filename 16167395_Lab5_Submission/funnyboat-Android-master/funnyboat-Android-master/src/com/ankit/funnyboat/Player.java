package com.ankit.funnyboat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Player extends GameObject {
	Bitmap img;
	Bitmap heartFull;
	Bitmap heartEmpty;
	{
		img = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.laiva);
		heartFull = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.sydan_f);
		heartEmpty = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.sydan_tyhja);
	}

	Bitmap getImage() {
		return img;
	}

	long lastShoot = 0;
	int life = 5;
	int maxLife = 5;

	public void tick() {
		super.tick();
		for (int i = 0; i < 5; i++) {
			int y = Game.water.height - 20;
			int x = Game.water.width - 30 - i * 20;
			Bitmap img = i < life ? heartFull : heartEmpty;
			Game.canvas.drawBitmap(img, x, y, null);
		}
		Matrix m = Game.canvas.getMatrix();
		Game.canvas.setMatrix(Menu.ident);
		Game.canvas.drawText("Score: " + Gameplay.current.score, 5, 50,
				Menu.smallTextPaint);
		Game.canvas.setMatrix(m);
	}

	void shoot() {
		if (lastShoot + 700 > System.currentTimeMillis()) {
			return;
		}
		lastShoot = System.currentTimeMillis();
		Bullet bullet = new Bullet();
		bullet.x = this.x + 34;
		bullet.y = this.y + 15;
		int speed = 100;
		bullet.vx = (float) (speed * 2);
		bullet.vy = (float) (speed * 1.5);
		Gameplay.current.objects.add(bullet);
	}

	boolean kill() {
		life--;
		if (life < 1) {
			Gameplay.current.end();
			return super.kill();
		}
		return false;
	}
}
