package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.Collectables.Heart;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.Spooky;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.playableEnemy;
import ch.epfl.cs107.play.game.superpacman.solidEntities.Gate;
import ch.epfl.cs107.play.game.superpacman.solidEntities.Portal;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level1 extends SuperPacmanArea{

	public String getTitle() {
		return "superpacman/Level1";
	}
	private playableEnemy playableEnem;


	private final int lvlIndex = 1;

	public void createArea() {
		registerActor(new Door("superpacman/Level2", new DiscreteCoordinates(15, 29), Logic.TRUE, this, Orientation.DOWN, new DiscreteCoordinates(14, 0), new  DiscreteCoordinates(15, 0)));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14, 3), this));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15, 3), this));
		playableEnem = new playableEnemy(this, Orientation.DOWN, new DiscreteCoordinates(14, 12), "superpacman/ghost.clyde");
		this.registerActor(playableEnem);
		playableEnem.setSpawnPosition(new DiscreteCoordinates(14, 12));
		Spooky spooky = new Spooky(this, Orientation.DOWN, new DiscreteCoordinates(14, 12), "superpacman/ghost.spooky");
		addGhost(this, spooky, 14, 12);
		super.createArea();
	}



	
}
