package com.ankit.funnyboat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class Game implements SurfaceHolder.Callback, Runnable,
		View.OnTouchListener {
	public static Context app;
	public static Game instance; // Yee, singleton!
	public static GameModule current;
	public static Water water;
	public static Canvas canvas;
	public static SurfaceView view;
	public static String TAG = "funnyboat";
	static MainActivity mainActivity;
	Thread thread;

	public Game() {
		loadMainMenu();
		thread = new Thread(this);
		water = new Water();
		view = new SurfaceView(app);
		view.setOnTouchListener(this);
		view.getHolder().addCallback(this);
	}

	public static void loadMainMenu() {
		current = new Menu() {

			@Override
			public String[] getNames() {
				return new String[] { "Story mode", "Endless mode" };
			}

			@Override
			public void call(int i) {
				System.err.println("call " + i);
				if (i == 0) {
					new Gameplay(true,mainActivity).begin();
				} else {
					new Gameplay(false,mainActivity).begin();
				}
			}

		};
	}

	public static void start(Activity applicationContext) {
		app = applicationContext.getApplicationContext();
		instance = new Game();
		applicationContext.setContentView(view);
		// holder.getHolder().lockCanvas();
		mainActivity = (MainActivity) applicationContext;
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Menu.init();
		try {
			thread.start();
		} catch (IllegalThreadStateException ex) {
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	Object resumeBarrier = new Object() {
	};

	public void resume() {
		synchronized (resumeBarrier) {
			resumeBarrier.notifyAll();
		}
	}

	public void waitForResume() throws InterruptedException {
		synchronized (resumeBarrier) {
			resumeBarrier.wait();
		}
	}

	public void run() {
		try {
			while (true) {
				canvas = view.getHolder().lockCanvas();
				if (canvas == null) {
					Thread.sleep(1000); // Ehh...

					continue;
				}
				synchronized (Game.instance) {
					water.height = canvas.getHeight();
					water.width = canvas.getWidth();
					Matrix m = new Matrix();
					m.setValues(new float[] { 1, 0, 0, 0, -1, water.height, 0,
							0, 1 });
					Game.canvas.setMatrix(m);
					water.tick();
					current.tick();
				}
				view.getHolder().unlockCanvasAndPost(canvas);
				Thread.sleep(40);
			}
		} catch (Exception ex) {
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (Game.instance) {
			current.touch(event);
		}
		return false;
	}

}
