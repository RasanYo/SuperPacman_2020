package ch.epfl.cs107.play.game.superpacman.actor.ghost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Ghost extends MovableAreaEntity implements Interactor {
	
	private Sprite[][] sprites;
	private Sprite[] afraidsprite;
	private final static int ANIMATION_DURATION = 8;
	private Animation[] animations;
	private Animation afraidanimation;
	private int GHOST_SCORE = 500;
	private boolean playerState;
	private GhostHandler handler = new GhostHandler();
	private boolean afraid = false;
	private SuperPacmanPlayer savedPacman = null;
	private DiscreteCoordinates refugePostion;
	private int movementSpeed = 9;
	private int afraidMovementSpeed = 18;

	/**
	 * Demo actor
	 * 
	 */
	public Ghost(Area owner , Orientation orientation , DiscreteCoordinates coordinates, String sprite) {
    	super(owner, orientation, coordinates);
        sprites = RPGSprite.extractSprites(sprite, 2, 1, 1, this, 16, 16,
        	    new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});
        afraidsprite = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1, this, new Vector(0.1f, 0.1f), 16, 16);
    	animations = Animation.createAnimations(ANIMATION_DURATION / 2, sprites);
    	afraidanimation = new Animation(ANIMATION_DURATION / 2, afraidsprite);
    	resetMotion();
    }

	/**
	 *
	 * @param deltaTime (float)
	 * updates ghost animation depending on value of afraid
	 */
	public void update(float deltaTime) {
		if (isAfraid()) { afraidAnimate(deltaTime); }

		else { animate(deltaTime); }

		super.update(deltaTime);
	}

	/**
	 * @param deltaTime (float)
	 * animates ghost when not afraid
	 */
    public void animate(float deltaTime) {
		if (!isDisplacementOccurs()) {
			if (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(getNextOrientation().toVector())))) {
				orientate(getNextOrientation());
				move(movementSpeed);
			}

		}

		if (isDisplacementOccurs()) {
			animations[getOrientation().ordinal()].update(deltaTime);
		}
	}

	/**
	 * @param deltaTime (float)
	 * animates ghost when afraid
	 */
	public void afraidAnimate(float deltaTime) {
		if (!isDisplacementOccurs()) {
			if (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(getNextOrientation().toVector())))) {
				orientate(getNextOrientation());
				move(afraidMovementSpeed);
				afraidanimation.reset();
			}
		}

		if (isDisplacementOccurs()) {
			afraidanimation.update(deltaTime);
		}

	}

	/**
	 * @param canvas (Canvas) target, not null
	 * draws ghost depending on value of afraid
	 */
	public void draw(Canvas canvas) {
		if (afraid) {
			afraidanimation.draw(canvas);
		} else {
			animations[getOrientation().ordinal()].draw(canvas);
		}
	}

/*
    ################# 	MÉTHODES AUXILIAIRES	 #####################
*/
	abstract Orientation getNextOrientation();

	/**
	 * @param orientation (Orientation)
	 * moves ghost in the given direction
	 */
	public void moveOrientate(Orientation orientation) {

		if(getNextOrientation() == orientation) {
			move(ANIMATION_DURATION);
		}else {
			orientate(orientation);
		}
	}

	/**
	 * @return a randomly generated orientation
	 */
	public Orientation randomOrientation() {
		int randomInt = RandomGenerator.getInstance().nextInt(4);
		return Orientation.fromInt(randomInt);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}
    
    @Override
	public void acceptInteraction(AreaInteractionVisitor v) {
    	((SuperPacmanInteractionVisitor)v).interactWith(this);
	}

	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

    public boolean wantsViewInteraction() {
		return true;
	}
    
    public boolean wantsCellInteraction() {
    	return false;
    }
    
    @Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}
	
	@Override
    public boolean isViewInteractable() {
    	return false;
    }


	/**
	 * @return list of cells inside the wanted field of view
	 */
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		List<DiscreteCoordinates> view = new ArrayList<>();

		for (int x = -5; x < 6; ++x) {
			for (int y = -5; y < 6; ++y) {
				if (cellExist(getCurrentMainCellCoordinates().jump(x, y)))
					view.add(getCurrentMainCellCoordinates().jump(x, y));
			}
		}
		return view;
	}

	/**
	 *
	 * @param dc (DiscreteCoordinates)
	 * @return boolean true if cells exists and false if not
	 */
	private boolean cellExist(DiscreteCoordinates dc) {
		return ((dc.x < getOwnerArea().getWidth()) && (dc.y < getOwnerArea().getHeight()) && (dc.x >= 0) && (dc.y >=0));
	}

	/**
	 *
	 * @param currentCell (DiscreteCoordinates) : current positiom
	 * @param target (DiscreteCoordinates) : coordinates of goal
	 * @return shortest path betewnn currentCell and target
	 */
	public Queue<Orientation> pathFinder(DiscreteCoordinates currentCell, DiscreteCoordinates target) {
		return ((SuperPacmanArea)getOwnerArea()).getGraph().shortestPath(currentCell, target);
	}

	/**
	 * @return savedPlayer's position
	 */
	public DiscreteCoordinates getPlayerPos() {
		return (DiscreteCoordinates) savedPacman.getCurrentCells().toArray()[0];
	}

	public SuperPacmanPlayer getSavedPacman() {
		return savedPacman;
	}

	/**
	 * @return boolean if player is saved
	 */
	public boolean playerHasBeenSaved(){
		if(savedPacman != null) {
			return true;
		}
		return false;
	}

	public void forgetPlayer() {
		savedPacman = null;
	}


	public boolean isAfraid() {
    	return afraid;
	}

	public void setAfraid(boolean afraid) {
		this.afraid = afraid;
	}

	public int getPoints() {
    	return GHOST_SCORE;
	}

	/**
	 * @param refuge sets refuge position upon registration inside of current area
	 */
	public void setRefugePostion (DiscreteCoordinates refuge) {
		refugePostion = refuge;
	}

	public DiscreteCoordinates getRefugePostion() {
		return refugePostion;
	}

	/**
	 * respawns ghost into its refugePosition
	 * forgets player
	 * sets afraid to false
	 */
	public void respawn() {
		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		setCurrentPosition(getRefugePostion().toVector());
		getOwnerArea().enterAreaCells(this, getCurrentCells());
		resetMotion();
		forgetPlayer();
		setAfraid(false);
	}

	/**
	 * @param x (int)
	 * changes movementSpeed to x if desired
	 */
	public void setMovementSpeed(int x) {
		movementSpeed = x;
	}

	public void setAfraidMovementSpeed(int x) {afraidMovementSpeed = x;}


	
	private class GhostHandler implements SuperPacmanInteractionVisitor {
		
		public void interactWith(SuperPacmanPlayer pacman) {
			savedPacman = pacman;

		}
	}
}

