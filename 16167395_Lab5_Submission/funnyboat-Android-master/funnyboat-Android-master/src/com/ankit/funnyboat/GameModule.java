package com.ankit.funnyboat;

import android.view.MotionEvent;

public interface GameModule {
	void tick();

	void touch(MotionEvent ev);
}
