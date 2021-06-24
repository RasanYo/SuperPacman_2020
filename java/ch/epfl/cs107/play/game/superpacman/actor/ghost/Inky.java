package ch.epfl.cs107.play.game.superpacman.actor.ghost;

import java.util.*;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Inky extends Ghost{

    private final float MAX_DISTANCE_WHEN_SCARED = 5;
    private final float MAX_DISTANCE_WHEN_NOT_SCARED = 10;
    private Queue<Orientation> path;
    private Queue<Orientation> nextPath;
    private DiscreteCoordinates targetPos = null;
    private List<DiscreteCoordinates> refugeAreaAfraid = new ArrayList<>();
    private List<DiscreteCoordinates> refugeArea = new ArrayList<>();
    private boolean outOfBounds = false;



    public Inky(Area owner , Orientation orientation , DiscreteCoordinates coordinates, String sprite){
        super(owner, orientation, coordinates, sprite);
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
    public void update(float deltaTime) {
        if (isAfraid()) {
            setAfraidMovementSpeed(5);
        } else {
            setMovementSpeed(9);
        }
        super.update(deltaTime);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /**
     * @return
     * next orientation of path considering if ghost is afraid or not and if the player is saved
     * if none of the above is true, takes random orientation as long as ghost remains inside a certain radius
     */
    @Override
    public Orientation getNextOrientation() {
        if (DiscreteCoordinates.distanceBetween(getCurrentMainCellCoordinates(), getRefugePostion()) == MAX_DISTANCE_WHEN_NOT_SCARED) {
            outOfBounds = true;
        }
        if (path == null || !isDisplacementOccurs()) {
            if (isAfraid()) {
                setTargetPosition(getRefugePostion(), MAX_DISTANCE_WHEN_SCARED);
                for (DiscreteCoordinates dc : refugeAreaAfraid) {
                    if (!this.targetPos.equals(dc)) {
                        setTargetPosition(getRefugePostion(), MAX_DISTANCE_WHEN_SCARED);
                    }
                }
            } else if (playerHasBeenSaved()) {
                this.targetPos = getPlayerPos();
            } else if (!playerHasBeenSaved() && outOfBounds){
                setTargetPosition(getRefugePostion(), MAX_DISTANCE_WHEN_NOT_SCARED - 2);
            }
            path = pathFinder(getCurrentMainCellCoordinates(), this.targetPos);
            if (path != null) {
                return path.poll();
            }
        }

        return randomOrientation();
    }

    /**
     * @param referenceCoords (DiscreteCoordinates): center of area
     * @param radius (int)
     * selects random area inside of area with referenceCoords inside radius radius
     */
    public void setTargetPosition(DiscreteCoordinates referenceCoords, float radius) {
        Random r = new Random();
        DiscreteCoordinates targetPos;
        do {
            targetPos = new DiscreteCoordinates(r.nextInt(getOwnerArea().getWidth()), r.nextInt(getOwnerArea().getHeight()));
        } while (DiscreteCoordinates.distanceBetween(referenceCoords, targetPos) > radius && targetPos.equals(getCurrentMainCellCoordinates()));
        this.targetPos = targetPos;
    }

    /**
     * @param field (List<DiscreteCoordinates>)
     * creates list of cells of around refugePosition (afraid)
     */
    public void setRefugeAreaAfraid(List<DiscreteCoordinates> field) {
        refugeAreaAfraid = field;
    }

    /**
     * @param field (List<DiscreteCoordinates>)
     * creates list of cells of around refugePosition (not afraid)
     */
    public void setRefugeArea(List<DiscreteCoordinates> field) {
        refugeArea = field;
    }
}
