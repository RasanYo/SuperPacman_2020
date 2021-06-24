package ch.epfl.cs107.play.game.superpacman.Collectables;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Diamond extends CollectableAreaEntity {
	
	Sprite sprite;
	
	public Diamond(Area area, DiscreteCoordinates position) {
		super(area, position, 10);
		sprite = new Sprite("superpacman/diamond", 1, 1, this);
		
	}

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
		
	}
}
