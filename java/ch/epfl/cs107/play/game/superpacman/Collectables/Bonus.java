package ch.epfl.cs107.play.game.superpacman.Collectables;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Bonus extends CollectableAreaEntity {
	
	Sprite sprites[][];
	private Animation[] animations;
	
	public Bonus(Area area, DiscreteCoordinates position) {
		super(area, position, 0);
		sprites = RPGSprite.extractSprites("superpacman/coin", 4, 1, 1, this, 16, 16, new Orientation[] {Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT});
		animations = Animation.createAnimations(ANIMATION_DURATION / 4, sprites);

	}

	/**
	 *
	 * @param deltaTime (float)
	 * updates animation
	 */
	public void update(float deltaTime) {
		animations[getOrientation().ordinal()].update(deltaTime);

	}
	
	public void draw(Canvas canvas) {
		animations[getOrientation().ordinal()].draw(canvas);		
	}
	
	
}
