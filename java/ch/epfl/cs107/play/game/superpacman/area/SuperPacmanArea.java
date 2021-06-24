package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.playableEnemy;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public abstract class SuperPacmanArea extends Area implements Logic{
	
	final static float CameraScale = 15.f;
	private boolean paused = false;
	public static int lvlIndex = 0;
	public static boolean switchingLevels = false;
	private final DiscreteCoordinates[] PLAYER_SPAWN_POSITION =
			{new DiscreteCoordinates(10, 1), new DiscreteCoordinates(15, 6), new DiscreteCoordinates(15, 29)};	private Logic diamondSignal = Logic.FALSE; //signal de l'aire


	private SuperPacmanBehavior behavior;
	private int diamondCounter = 0; //compteur pour les diamants
	private AreaGraph graph;
	private boolean ghostsAfraid = false;

	
	public void createArea() {
			behavior.registerActors(this);
	}
	
	public float getCameraScaleFactor() {
		return 30.f;
	}


	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
        	behavior = new SuperPacmanBehavior(window, getTitle());
            setBehavior(behavior);
            graph = behavior.getGraph();
            createArea();
            return true;
        }
        return false;
    }

    public AreaGraph getGraph() {
		return graph;
	}


	/**
	 * @param x (int)
	 * increments diamond counter by x
	 */
	public void incrementCounter (int x) {
		diamondCounter += x;
		if (diamondCounter == 0) {
			diamondSignal = Logic.TRUE;
		}
	}

	/**
	 * changes index of level to change level
	 */
	public static void incrementLvlIndex() {
		++lvlIndex;
	}

	public DiscreteCoordinates getSpawnPosition() {
		return PLAYER_SPAWN_POSITION[lvlIndex];
	}



	@Override
	public boolean isOn() {
		return diamondSignal.isOn();
	}
	
	@Override
	public boolean isOff() {
		return diamondSignal.isOff();
	}
	
	@Override
	public float getIntensity() {
		return diamondSignal.getIntensity();
	}

	public void addGhost(Area area, Ghost ghost, int x, int y) {
		behavior.addGhost(area, ghost, x, y);
	}


	public void setGhostsAfraid (boolean afraid) {
		for (int i = 0; i < behavior.accessGhosts().size(); ++i) {
			behavior.accessGhosts().get(i).setAfraid(afraid);
		}
		playableEnemy.setAfraid(afraid);


	}

	public void respawnGhosts() {
		for (int i = 0; i < behavior.accessGhosts().size(); ++i) {
			behavior.accessGhosts().get(i).respawn();
		}
	}

	public void addNode(int x, int y) {
		behavior.addNode(x, y);
	}


}
