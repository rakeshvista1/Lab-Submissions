package com.ankit.funnyboat;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

public class Enemy extends GameObject {

	static Bitmap sharkImg;
	static Bitmap mineImg;
	static Bitmap shipImg;
	boolean isMine = false;
	boolean isShark = false;
	boolean isShip = false;
	float shootCounter;
	float jumpCounter;
	static Random rand = new Random(13);
	static Paint linePaint;
	static {
		linePaint = new Paint();
		linePaint.setARGB(0xff, 0, 0, 0);
		shipImg = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.merkkari);
		sharkImg = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.hai);
		mineImg = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.miina);
	}
	{
		waterUp = true;
		if (Gameplay.current.storyMode) {
			if (Gameplay.current.counter < 36) {
				isMine = true;
			} else if (Gameplay.current.counter < 70) {
				isMine = rand.nextBoolean();
				isShark = !isMine;
			} else {
				isMine = rand.nextBoolean();
				isShark = isMine ? false : rand.nextInt(3) != 1;
			}
		} else {
			isMine = rand.nextBoolean();
			isShark = isMine ? false : rand.nextInt(3) != 1;
		}
		isShip = !isMine && !isShark;
		if (isMine)
			waterUp = false;
		vx = (float) -(Game.water.getWaveSpeed() * 0.5);
		enemy = true;
	}

	Bitmap getImage() {
		if (isMine)
			return mineImg;
		if (isShark)
			return sharkImg;
		return shipImg;
	}

	@Override
	public void tick() {
		if (isMine) {
			y = Game.water.getAt((int) x);
			x += vx * DELTA;
			Game.canvas.drawLine(x, y, x, 0, linePaint);
			Game.canvas.drawBitmap(mineImg, x - mineImg.getWidth() / 2, y - 7,
					null);
			return;
		}
		super.tick();

		shootCounter += DELTA;
		if (isShip) {
			if (shootCounter > 3.5) {
				shootCounter = 0;
				shoot();
			}
		}
		jumpCounter += DELTA;
		if (isShip || isShark) {
			if (jumpCounter > 4.5) {
				jumpCounter = 0;
				jump();
			}
		}
	}

	void shoot() {
		Bullet bullet = new Bullet();
		bullet.enemy = true;
		bullet.x = this.x;
		bullet.y = this.y + 15;
		int speed = 100;
		bullet.vx = -(float) (speed * 2);
		bullet.vy = (float) (speed * 1.5);
		Gameplay.current.objects.add(bullet);
	}

	boolean kill() {
		Gameplay.current.score += (isMine ? 20 : (isShip ? 30 : 20));
		return super.kill();
	}
}
