package ch.epfl.cs107.play.game.superpacman.Collectables;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Cherry extends CollectableAreaEntity {

	Sprite sprite;
	
	public Cherry(Area area, DiscreteCoordinates position) {
		super(area, position, 200);
		sprite = new Sprite("superpacman/cherry", 1, 1, this);
		
	}

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
		
	}
}

