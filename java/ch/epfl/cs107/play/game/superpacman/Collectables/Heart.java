package ch.epfl.cs107.play.game.superpacman.Collectables;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Heart extends CollectableAreaEntity{

    Sprite sprite;

    public Heart(Area area, DiscreteCoordinates position, int points) {
        super(area, position, 0);
        sprite = new Sprite("superpacman/heart1", 1, 1, this);

    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
}
