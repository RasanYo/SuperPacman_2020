package ch.epfl.cs107.play.game.superpacman.solidEntities;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Gate extends AreaEntity{

	Sprite sprite;
	private Logic key;

	/**
	 *
	 * @param area
	 * @param orientation
	 * @param position
	 * @param key
	 * Constructoe when gates opens with key(s)
	 */
	public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Logic key) {
		super(area, orientation, position);
		this.key = key;
		if (orientation == Orientation.UP || orientation == Orientation.DOWN) {
			sprite = new Sprite("superpacman/gate", 1.f, 1.f, this, new RegionOfInterest(0, 0, 64, 64));
		} else {
			sprite = new Sprite("superpacman/gate", 1.f,1.f, this, new RegionOfInterest(0, 64, 64, 64));
		}

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		((SuperPacmanArea)getOwnerArea()).getGraph().setSignal(getCurrentMainCellCoordinates(), key);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	/**
	 * @return takeCellSpace true while signal is off
	 */
	@Override
	public boolean takeCellSpace() {
		return key.isOff();
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param canvas target, not null
	 * draws gate's sprite while signal is off
	 */
	@Override
	public void draw(Canvas canvas) {
		if (key != null && key.isOff()) {
			sprite.draw(canvas);
		}
	}
}

		


