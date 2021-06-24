package ch.epfl.cs107.play.game.superpacman.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class PauseMenu implements Graphics {

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float heigth = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, heigth/2));
		
		ImageGraphics pause = new ImageGraphics(ResourcePath.getSprite("superpacman/pause"), width, heigth, null, anchor.add(new Vector(0, 0)));
		pause.draw(canvas);
	}
	
}
