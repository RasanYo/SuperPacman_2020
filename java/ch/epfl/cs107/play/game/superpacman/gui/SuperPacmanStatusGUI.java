package ch.epfl.cs107.play.game.superpacman.gui;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SuperPacmanStatusGUI implements Graphics {

	private Sprite[] sprites;
	private ImageGraphics life[] = new ImageGraphics[5];
	private Vector anchor;
	private TextGraphics scoreDisplay;
	private float height, width;
	private int chances;
	private int score;

	public SuperPacmanStatusGUI(Canvas canvas, int chances, int score) {
		sprites = RPGSprite.extractSprites("superpacman/lifeDisplay", 2, 1, 1, canvas, 64, 64);
		width = canvas.getScaledWidth();
		height = canvas.getScaledHeight();
		anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2 + 1, height/2));
		this.chances = chances;
		this.score = score;
		draw(canvas);
	}
	
	@Override
	/**
	 * draws score and lifepoints
	 */
	public void draw(Canvas canvas) {
		for (int i = 0; i < 5; ++i) {
			int m = 64;
			if (i < chances) {m = 0; }  // to change chances, update first chances with setChances() before drawing
			drawLife(m, i, i, anchor, height, canvas);	
		}
		drawScore(canvas);
	}

	/**
	 *
	 * @param color (int)
	 * @param vectorShift (Vector)
	 * @param index (int)
	 * @param vect (Vector)
	 * @param height (float)
	 * @param canvas (Canvas)
	 * draws sprites to display lifepoints and sets a space of vectorShift between the sprites
	 */
	public void drawLife(int color, int vectorShift, int index, Vector vect, float height, Canvas canvas) {
		life[index] = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"),
				 0.9f, 0.9f, new RegionOfInterest(color, 0, 64, 64),
				 vect.add(new Vector(1+vectorShift, height - 1.375f)), 1, 5);

		life[index].draw(canvas);
	}

	/**
	 * @param canvas (Canvas)
	 * draws score
	 */
	public void drawScore(Canvas canvas) {
		scoreDisplay = new TextGraphics("SCORE: " + score, 1.f, Color.YELLOW, Color.BLACK, 0.06f, true, false, anchor.add(new Vector(6.5f, height - 1.33f)));
		scoreDisplay.draw(canvas);
	}
	
	public TextGraphics getScoreDisplay() {
		return scoreDisplay;
	}
	
}
