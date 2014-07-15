package com.ankit.funnyboat;

import android.graphics.Bitmap;

public class DeathBody extends GameObject {
	Bitmap img;

	public DeathBody(Bitmap img) {
		this.img = img;
		waterUp = false;
		vy = -100;
		special = true;
	}

	public void tick() {
		super.tick();
		vy = -80;
	}

	@Override
	Bitmap getImage() {
		return img;
	}

}
