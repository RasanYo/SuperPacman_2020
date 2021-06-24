package ch.epfl.cs107.play.game.superpacman.actor.ghost;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Blinky extends Ghost {
	
	private int MAX = 4;
	
	public Blinky(Area owner , Orientation orientation , DiscreteCoordinates coordinates, String sprite){
		super(owner, orientation, coordinates, sprite);
	}
	@Override
    Orientation getNextOrientation() {
		return randomOrientation();
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	public void respawn() {
		super.respawn();
	}

	public void setAfraid(boolean bool) {
		super.setAfraid(bool);
	}

}
