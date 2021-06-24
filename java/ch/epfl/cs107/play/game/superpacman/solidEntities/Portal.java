package ch.epfl.cs107.play.game.superpacman.solidEntities;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Portal extends AreaEntity {

    private Sprite[][] sprites;
    private Animation[] animations;
    private DiscreteCoordinates destination;
    private final int ANIMATION_DURATION = 8;

    /**
     * Default AreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
    public Portal(Area area, Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates destination) {
        super(area, orientation, position);
        this.destination = destination;
        sprites = RPGSprite.extractSprites("superpacman/portal2", 4, 1, 1, this, 20, 80, new Orientation[] {Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT});
        animations = Animation.createAnimations(ANIMATION_DURATION / 4, sprites);

    }

    public void teleport(SuperPacmanPlayer player) {
        getOwnerArea().leaveAreaCells(player, player.getEnteredCells());
        setCurrentPosition(destination.toVector());
        getOwnerArea().enterAreaCells(player, getCurrentCells());
    }

    @Override
    public void update(float deltaTime) {
        animations[getOrientation().ordinal()].update(deltaTime);
    }


    @Override
    public void draw(Canvas canvas) {
        animations[getOrientation().ordinal()].draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
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

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
    }
}
