package com.ankit.funnyboat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet extends GameObject {

	Bitmap img;
	{
		img = BitmapFactory.decodeResource(Game.app.getResources(),
				R.drawable.kuti);
		waterUp = false;
	}

	Bitmap getImage() {
		return img;
	}

}
