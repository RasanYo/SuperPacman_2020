package ch.epfl.cs107.play.game.superpacman.actor.ghost;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.Collectables.CollectableAreaEntity;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class playableEnemy extends Player {

    private Sprite[][] sprites;
    private Sprite[] afraidsprite;
    /// Animation duration in frame number
    private final static int ANIMATION_DURATION = 6;
    private Animation[] animations;
    private Animation afraidanimation;
    private float timer = 10;
    private int GHOST_SCORE = 500;
    private boolean playerState;
    private Orientation desiredOrientation = Orientation.DOWN;
    private static boolean afraid = false;
    private SuperEnemyHandler handler = new SuperEnemyHandler();
    private DiscreteCoordinates refuge;

    /**
     * Demo actor
     *
     */
    public playableEnemy(Area owner , Orientation orientation , DiscreteCoordinates coordinates, String sprite) {
        super(owner, orientation, coordinates);
        sprites = RPGSprite.extractSprites(sprite, 2, 1, 1, this, 16, 16,
                new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});
        afraidsprite = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1, this, new Vector(0.1f, 0.1f), 16, 16);
        animations = Animation.createAnimations(ANIMATION_DURATION / 2, sprites);
        afraidanimation = new Animation(ANIMATION_DURATION / 2, afraidsprite);
        resetMotion();
    }

    //dessine les animations tout comme le score du personnage principale et ses points de vie
    public void draw(Canvas canvas) {
        if (afraid) {
            afraidanimation.draw(canvas);
        } else {
            animations[getOrientation().ordinal()].draw(canvas);
        }
    }



    //methode update met a jour le mouvement et l'animation
    public void update(float deltaTime) {

        Keyboard keyboard= getOwnerArea().getKeyboard();


        setDesiredOrientation(Orientation.LEFT, keyboard.get(Keyboard.A));
        setDesiredOrientation(Orientation.UP, keyboard.get(Keyboard.W));
        setDesiredOrientation(Orientation.RIGHT, keyboard.get(Keyboard.D));
        setDesiredOrientation(Orientation.DOWN, keyboard.get(Keyboard.S));

        if (afraid) {
            if (!isDisplacementOccurs()) {
                if (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(desiredOrientation.toVector())))) {
                    orientate(desiredOrientation);
                    move(9);
                    afraidanimation.reset();
                }
            }

            if (isDisplacementOccurs()) {
                afraidanimation.update(deltaTime);
            }
        } else {
            if (!isDisplacementOccurs()) {
                if (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(desiredOrientation.toVector())))) {
                    orientate(desiredOrientation);
                    move(9);
                }
                if (strengthen()) {
                    animations[getOrientation().ordinal()].reset();
                }
            }

            if (isDisplacementOccurs()) {
                animations[getOrientation().ordinal()].update(deltaTime);
            }
        }

        super.update(deltaTime);
    }
    private void setDesiredOrientation(Orientation orientation, Button button){
        if(button.isDown()) {
            desiredOrientation=orientation;
        }
    }
    public boolean strengthen() {
        return (timer == 0) ;
    }
    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
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
        return true;
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean wantsViewInteraction() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }
    private class SuperEnemyHandler implements SuperPacmanInteractionVisitor {

        public void interactWith(CollectableAreaEntity a) {

        }
        public void interactWith(Ghost ghost) {

        }
        public void interactWith(SuperPacmanPlayer p) {

        }
    }
    public boolean isAfraid() {
        // TODO Auto-generated method stub
        return afraid;
    }
    public static void setAfraid(boolean bool) {
        afraid = bool;
    }
    public DiscreteCoordinates getSpawnPosition() {
        return refuge;
    }
    public void setSpawnPosition(DiscreteCoordinates newRef) {
        refuge = newRef;
    }
    public void respawn() {
        getOwnerArea().leaveAreaCells(this , getEnteredCells());
        setCurrentPosition(getSpawnPosition().toVector());
        getOwnerArea().enterAreaCells(this , getCurrentCells());
        resetMotion();
        setAfraid(false);
    }

    public int getPoints() {
        // TODO Auto-generated method stub
        return 700;
    }
}