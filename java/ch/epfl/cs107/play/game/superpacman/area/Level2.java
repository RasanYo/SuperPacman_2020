package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.Collectables.Heart;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.Spooky;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.playableEnemy;
import ch.epfl.cs107.play.game.superpacman.solidEntities.Gate;
import ch.epfl.cs107.play.game.superpacman.Collectables.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.And;

public class Level2 extends SuperPacmanArea{

	private final DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 29);
	private playableEnemy playableEnem;

	private final int lvlIndex = 2;

	
	
	public String getTitle() {
		return "superpacman/Level2";
	}


	
	public void createArea() {
		Key key1 = new Key(this, new DiscreteCoordinates(3, 16));
		Key key2 = new Key(this, new DiscreteCoordinates(26, 16));
		Key key3 = new Key(this, new DiscreteCoordinates(2, 8));
		Key key4 = new Key(this, new DiscreteCoordinates(27, 8));
		And mutlipleKeys = new And(key3, key4);
		registerActor(key1);
		registerActor(key2);
		registerActor(key3);
		registerActor(key4);
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 14), key1));
		registerActor(new Gate(this, Orientation.DOWN, new DiscreteCoordinates(5, 12), key1));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 10), key1));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 8), key1));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 14), key2));
		registerActor(new Gate(this, Orientation.DOWN, new DiscreteCoordinates(24, 12), key2));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 10), key2));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 8), key2));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(10, 2), mutlipleKeys));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(19, 2), mutlipleKeys));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(12, 8), mutlipleKeys));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(17, 8), mutlipleKeys));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14, 3), this));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15, 3), this));
		registerActor(new Heart(this, new DiscreteCoordinates(15, 16), 0));
		registerActor(new Heart(this, new DiscreteCoordinates(1, 28), 0));
		registerActor(new Heart(this, new DiscreteCoordinates(28, 28), 0));
		registerActor(new Heart(this, new DiscreteCoordinates(1, 15), 0));
		registerActor(new Heart(this, new DiscreteCoordinates(28, 15), 0));
		registerActor(new Heart(this, new DiscreteCoordinates(2, 5), 0));
		registerActor(new Heart(this, new DiscreteCoordinates(27, 5), 0));
		Spooky spooky = new Spooky(this, Orientation.DOWN, new DiscreteCoordinates(1, 15), "superpacman/ghost.spooky");
		playableEnem = new playableEnemy(this, Orientation.DOWN, new DiscreteCoordinates(14, 12), "superpacman/ghost.clyde");
		this.registerActor(playableEnem);
		playableEnem.setSpawnPosition(new DiscreteCoordinates(14, 12));
		addGhost(this, spooky, 1, 15);



		super.createArea();

	}
	
	
}
