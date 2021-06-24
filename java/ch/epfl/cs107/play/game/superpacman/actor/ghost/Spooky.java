package ch.epfl.cs107.play.game.superpacman.actor.ghost;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Audio;

public class Spooky extends Pinky{

    private SoundAcoustics spookSound = new SoundAcoustics("sounds/spooky_scream.wav", 0.5f, false, false, false, false);
    private int screamTimer = 300;
    private int slowedTimer = 40;
    private boolean restartSound = true;
    private boolean isScreaming = false;
    private boolean playSound = false;

    public Spooky(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String sprite) {
        super(owner, orientation, coordinates, sprite);
        setMovementSpeed(18);
    }

    /**
     *
     * @param deltaTime (float)
     * screaming cycle:
     *      - when the screamTimer is null or negative, screaming mechanisms are launched
     *      - interval timer slowedTimer is launched
     *      - when slowedTimer is null or negative, all values are set to initial state
     */
    public void scream(float deltaTime) {
      if (!isScreaming) {
          screamTimer -= deltaTime;
      }else if (isScreaming) {
          slowedTimer -= deltaTime;
      }
      if (screamTimer <= 0) {
          setIsScreaming();
          screamTimer = 300;
      } else if (slowedTimer <= 0) {
          setIsScreaming();
          slowedTimer = 40;
      }
    }

    /**
     * changes screaming state to opposite value and depending on value, changes savedPlayer's movement speed
     */
    public void setIsScreaming () {
        isScreaming = !isScreaming;
        if (isScreaming) {
            getSavedPacman().setMovementSpeed(14);
            playSound = true;
            spookSound.shouldBeStarted();
        } else {
            getSavedPacman().setMovementSpeed(4);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getSavedPacman() != null && !isAfraid()) {
            scream(deltaTime);
        }

    }

    @Override
    public void bip(Audio audio) {
        if (playSound) {
            spookSound.bip(audio);
            playSound = false;
        }
    }

}
