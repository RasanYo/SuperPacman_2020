package ch.epfl.cs107.play.game.superpacman.Collectables;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Key extends CollectableAreaEntity implements Logic {

	Sprite sprite;
	private Logic keySignal = Logic.FALSE;
	
	public Key(Area area, DiscreteCoordinates position) {
		super(area, position, 0);
		sprite = new Sprite("superpacman/key", 1, 1, this);
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
	}
	
	
	@Override
	/**
	 * changes the key's signal when collected
	 */
	public void setIsCollected() {
		super.setIsCollected();
		keySignal = Logic.TRUE;
	}

	@Override
	//renvoie le boolean annoncant si le signal est allume ou non
	public boolean isOn() {
		// TODO Auto-generated method stub
		return keySignal.isOn();
	}

	@Override
	//renvoie le boolean annoncant si le signal est eteint ou non
	public boolean isOff() {
		// TODO Auto-generated method stub
		return keySignal.isOff();
	}

	@Override
	public float getIntensity() {
		if (isCollected()) { return 1; }
		else {return 0;}
	}

}
