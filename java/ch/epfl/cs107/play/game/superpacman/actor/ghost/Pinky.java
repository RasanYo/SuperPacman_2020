package ch.epfl.cs107.play.game.superpacman.actor.ghost;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Pinky extends Ghost{

    private float ATTEMPT = 0;
    private float MAX_RANDOM_ATTEMPT = 200;
    private float MIN_AFFRAID_DISTANCE = 5;
    private Queue<Orientation> path;
    private DiscreteCoordinates targetPos;
    private int i = 1;

    public Pinky(Area owner , Orientation orientation , DiscreteCoordinates coordinates, String sprite){
        super(owner, orientation, coordinates, sprite);
    }

    public void update(float deltaTime) {
        if (!isAfraid()) {
            ATTEMPT = 0;
        }
        super.update(deltaTime);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        // TODO Auto-generated method stub
        return super.getFieldOfViewCells();
    }

    @Override
    public boolean wantsCellInteraction() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Orientation getNextOrientation() {
        if (path == null || !isDisplacementOccurs()) {
            if (playerHasBeenSaved()) {
                if (isAfraid()) {
                    setTargetPositionFleeing(getPlayerPos());
                } else {
                    this.targetPos = getPlayerPos();
                }
            }
            path = pathFinder(getCurrentMainCellCoordinates(), this.targetPos);
            if (path != null) {
                return path.poll();
            }
        }
        return randomOrientation();
    }

    /**
     * @param ennemyCoords (DiscreteCoordinates) : coords of ennemy player
     * chooses random coordinate when fleeing inside a certain radius arounf ennemyCoords
     */
    public void setTargetPositionFleeing(DiscreteCoordinates ennemyCoords) {
        Random r = new Random();
        DiscreteCoordinates targetPos;
        do {
            ++ATTEMPT;
            targetPos = new DiscreteCoordinates(r.nextInt(getOwnerArea().getWidth()), r.nextInt(getOwnerArea().getHeight()));
        } while (DiscreteCoordinates.distanceBetween(ennemyCoords, targetPos) < MIN_AFFRAID_DISTANCE && ATTEMPT < MAX_RANDOM_ATTEMPT);
        this.targetPos = targetPos;
    }

}
