package ch.epfl.cs107.play.game.superpacman.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class MenuEnd implements Graphics{

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float heigth = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, heigth/2));
		
		ImageGraphics gameOver = new ImageGraphics(ResourcePath.getSprite("superpacman/GameOver"), width, heigth, null, anchor.add(new Vector(0, 0)));
		gameOver.draw(canvas);
		
	}
}

