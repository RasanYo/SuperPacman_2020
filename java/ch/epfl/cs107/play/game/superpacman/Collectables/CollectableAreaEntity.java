package ch.epfl.cs107.play.game.superpacman.Collectables;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class CollectableAreaEntity extends AreaEntity{

	private int points;
	protected final static int ANIMATION_DURATION = 8;
	private boolean isCollected = false;
	
	public CollectableAreaEntity(Area area, DiscreteCoordinates position, int points) {
		super(area, Orientation.DOWN, position);
		this.points = points;
	}

	@Override
	public abstract void draw(Canvas canvas);
	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor)v).interactWith(this);
	}

	/**
	 * @return value of isCollected
	 */
	public boolean isCollected() {
		return isCollected;
	}

	/**
	 * methode that changes value of isCollected when called
	 */
	public void setIsCollected() {
		isCollected = true;
	}
	
	public int getPoints() {
		return this.points;
	}

}
