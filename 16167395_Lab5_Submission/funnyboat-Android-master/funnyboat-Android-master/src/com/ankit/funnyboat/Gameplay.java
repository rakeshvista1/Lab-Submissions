package com.ankit.funnyboat;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Matrix;
import android.view.MotionEvent;

public class Gameplay implements GameModule {
	List<GameObject> objects = new ArrayList<GameObject>();
	Player player;
	float counter = 0;
	float ccounter = 0;
	boolean gameOver = false;
	boolean storyMode = false;
	float gameOverCounter;
	int score;
	MainActivity mainActivity;

	static Gameplay current;

	public Gameplay(boolean story, MainActivity mainActivity) {
		player = new Player();
		objects.add(player);
		storyMode = story;
		this.mainActivity = mainActivity;
	}

	public void begin() {
		Game.current = this;
		current = this;
	}

	public void collide() {
		ArrayList<GameObject> enemies = new ArrayList<GameObject>();
		ArrayList<GameObject> friends = new ArrayList<GameObject>();
		for (GameObject ob : objects)
			if (!ob.special)
				(ob.enemy ? enemies : friends).add(ob);
		for (GameObject a : enemies) {
			for (GameObject b : friends) {
				boolean intersect = !(a.x > b.x + b.w || a.x + a.w < b.x
						|| a.y > b.y + b.h || a.y + a.h < b.y);
				if (a instanceof Bullet && b instanceof Bullet)
					continue;
				if (intersect) {
					if (a.kill())
						objects.remove(a);
					if (b.kill())
						objects.remove(b);
				}
			}
		}
	}

	@Override
	public void tick() {
		collide();
		ccounter += GameObject.DELTA;
		counter += GameObject.DELTA;
		if (ccounter > 6) {
			ccounter = 0;
			spawn();
		}
		for (GameObject ob : new ArrayList<GameObject>(objects)) {
			ob.tick();
			if (ob.y < -10 || ob.x < -10 || ob.x > Game.water.width
					|| ob.y > Game.water.height) {
				if (ob instanceof Player) {
					ob.y = Game.water.height / 2;
					ob.vy = 0;
				} else {
					objects.remove(ob);
				}
			}
		}
		String text = null;
		if (gameOver) {
			gameOverCounter -= GameObject.DELTA;
			text = "Game Over | Score: " + score;
			if (gameOverCounter < 0) {
				// Game.current
				Game.loadMainMenu();
			}
		}
		if (!storyMode) {
			if (counter < 3) {
				text = "Touch lower part of screen to jump";
			} else if (counter < 5) {
				text = "Avoid mines, sharks and pirates";
			} else if (counter < 8) {
				text = "Touch upper part of screen to shoot";
			} else if (counter < 10) {
				text = "Good luck!";
			}
		} else {
			if (counter < 3) {
				text = "Captain! Minefield ahead!";
			} else if (counter < 6) {
				text = "(Touch lower part of screen to jump)";
			} else if (counter < 34) {
			} else if (counter < 36) {
				text = "Watch out for those angry sharks, captain!";
			} else if (counter < 38) {
				text = "(Touch lower part of screen to shoot)";
			} else if (counter < 69) {
			} else if (counter < 72) {
				text = "Oh no! It's the infamous fleet of pirate Captain Magentabeard!";
			}
		}
		if (text != null) {
			Matrix m = Game.canvas.getMatrix();
			Game.canvas.setMatrix(Menu.ident);
			float size = Menu.textPaint.measureText(text);
			Game.canvas.drawText(text, Game.water.width / 2 - size / 2, 40,
					Menu.textPaint);
			Game.canvas.setMatrix(m);
		}
	}

	private void spawn() {
		Enemy e = new Enemy();
		e.x = Game.water.width;
		e.y = Game.water.getAt((int) e.x);
		objects.add(e);
	}

	void end() {
		gameOver = true;
		gameOverCounter = 6;
	}

	@Override
	public void touch(MotionEvent ev) {
		if ((ev.getAction() & MotionEvent.ACTION_DOWN) != 0
				|| ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (ev.getY() < Game.water.height / 2)
				player.shoot();
			else
				player.jump();
			mainActivity.setPlayer(player);
		}
	}
}
