package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.solidEntities.Gate;
import ch.epfl.cs107.play.game.superpacman.Collectables.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level0 extends SuperPacmanArea{

	private Key key = new Key(this, new DiscreteCoordinates(3,4));
	
	public String getTitle() {
		return "superpacman/Level0";
	}
	
	
	public void createArea() {
		super.createArea();
		registerActor(new Door("superpacman/Level1", new DiscreteCoordinates(15, 6), Logic.TRUE, this, Orientation.UP, new DiscreteCoordinates(5, 9), new  DiscreteCoordinates(6, 9)));
		registerActor(key);
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(5, 8), key));
		registerActor(new Gate(this, Orientation.LEFT, new DiscreteCoordinates(6, 8),  key));

	}
	
	
}
