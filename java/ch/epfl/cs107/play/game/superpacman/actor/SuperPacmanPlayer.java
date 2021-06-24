package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.Collectables.*;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.playableEnemy;
import ch.epfl.cs107.play.game.superpacman.gui.SuperPacmanStatusGUI;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.Ghost;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.solidEntities.Portal;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.*;

public class SuperPacmanPlayer extends Player {
	
		private Sprite[][] sprites;
		private final static int ANIMATION_DURATION = 4;
		private Orientation desiredOrientation = Orientation.DOWN;
		private SuperPacmanPlayerHandler handler = new SuperPacmanPlayerHandler();
		private Animation[] animations;
		private SuperPacmanStatusGUI status;
		private int score = 0;
		private int chances = 3;
		private int movementSpeed = 4;
		private boolean invincible = false;
		private float timer = 10;
		private SoundAcoustics diamondSound = new SoundAcoustics("sounds/transactionFail.wav", 0.3f, false, false, false, false);
		private SoundAcoustics cherrySound = new SoundAcoustics("sounds/pacman_eatfruit.wav", 0.4f, false, false, false, false);
		private SoundAcoustics eatGhostSound = new SoundAcoustics("sounds/pacman_eatghost.wav", 0.4f, false, false, false, false);
		private SoundAcoustics heartSound = new SoundAcoustics("sounds/dialogNext.wav", 0.4f, false, false, false, false);
		private SoundAcoustics music = new SoundAcoustics("sounds/fight.wav", 0.7f, false, false, true, false);
		private boolean restartMusic = true;


	/**
		 * Demo actor
		 * 
		 */
		public SuperPacmanPlayer(Area owner , Orientation orientation , DiscreteCoordinates coordinates) {
	    	super(owner, orientation, coordinates);
	        sprites = RPGSprite.extractSprites("superpacman/pacman", 4, 1, 1, this, 64, 64,
	                new Orientation[] {Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT});

	    	animations = Animation.createAnimations(ANIMATION_DURATION / 4, sprites);
	    	
	    	resetMotion();
			music.shouldBeStarted();
		}

		/**
		 *
		 * @param canvas (Canvas) target, not null
		 * draws player's animation and GUI associated with the player
		 */
		public void draw(Canvas canvas) {
				animations[getOrientation().ordinal()].draw(canvas);
				status = new SuperPacmanStatusGUI(canvas, chances, score);

			}


	/**
	 *
	 * @param deltaTime (float)
	 * updates player's animation, movement, orientation and state
	 */
	public void update(float deltaTime) {

            Keyboard keyboard= getOwnerArea().getKeyboard();

            setDesiredOrientation(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
            setDesiredOrientation(Orientation.UP, keyboard.get(Keyboard.UP));
            setDesiredOrientation(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
            setDesiredOrientation(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

            if (!isDisplacementOccurs()) {
                if (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(desiredOrientation.toVector())))) {
                    orientate(desiredOrientation);
                    move(movementSpeed);

                }
                animations[getOrientation().ordinal()].reset();
            }

            if (isDisplacementOccurs()) {
            	animations[getOrientation().ordinal()].update(deltaTime);
            }

            if (invincible) {
				timer -= deltaTime;
			}
			if (timer <= 0) {
				setInvincible(false);
				timer = 10;
			}

            super.update(deltaTime);
        }

		/**
		 *
		 * @param orientation (Orientation): new orientation request
		 * @param button (Button): button associated with orientation input
		 * applies requested orientation
		 */
		private void setDesiredOrientation(Orientation orientation, Button button){
				if(button.isDown()) {
					desiredOrientation=orientation;
				}
			}

		@Override
		public void acceptInteraction(AreaInteractionVisitor v) {
			((SuperPacmanInteractionVisitor)v).interactWith(this);
		}

		@Override
		public void interactWith(Interactable other) {
			other.acceptInteraction(handler);
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
			return false;
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

	/**
	 *
	 * @param x (int): value of points to add to the score
	 * adds to score x
	 */
		public void incrementScore(int x) {
			score += x;
		}

	/**
	 *
	 * @param x (int): number of life chances to grant
	 * adds x to the chances
	 */
		public void incrementChances(int x) {chances += x;}

	/**
	 * methodgetOwnerArea().leaveAreaCells(SuperPacmanPlayer.this , getEnteredCells());
	 * 			setCurrentPosition(((SuperPacmanArea)getOwnerArea()).getSpawnPosition().toVector());
	 * 			getOwnerArea().enterAreaCells(SuperPacmanPlayer.this , getCurrentCells());
	 * 			resetMotion(); called when player is eaten by an ennemy entity (ghost)
	 * decreases chances by 1
	 * respawns player to the current level's spawn position
	 */
		public void isEaten() {
			incrementChances(-1);
			getOwnerArea().leaveAreaCells(this, getEnteredCells());
			setCurrentPosition(((SuperPacmanArea)getOwnerArea()).getSpawnPosition().toVector());
			getOwnerArea().enterAreaCells(this, getCurrentCells());
			resetMotion();
			incrementScore(-score);
			setMovementSpeed(4);
		}

	/**
	 *
	 * @param state (boolean)
	 * changes invincibility state of player to state
	 */
		public void setInvincible (boolean state) {
			invincible = state;
			((SuperPacmanArea)getOwnerArea()).setGhostsAfraid(state);
		}

		public void setMovementSpeed(int x) {
			movementSpeed = x;
		}


		@Override
		public void bip(Audio audio) {
			super.bip(audio);
			diamondSound.bip(audio);
			cherrySound.bip(audio);
			eatGhostSound.bip(audio);
			heartSound.bip(audio);
			if (restartMusic) {
				music.bip(audio);
				restartMusic = false;
			}

		}

		public boolean getInvincible() {
			return invincible;
		}

		public boolean isgameOver() {
		return chances == 0;
	}



	private class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor {

			@Override
			/**
			 * handles interaction between player and door
			 * changes level index to access next level
			 */
			public void interactWith(Door door) {
				setIsPassingADoor(door);
				SuperPacmanArea.incrementLvlIndex();
				SuperPacmanArea.switchingLevels = true;
			}
			
			@Override
			/**
			 * handles interaction between player and a CollectableAreaEntity
			 * changes collectable's value of isCollected
			 * increments player's score by the points associated to the collectable
			 * calls specific functionnality's associated with collectable
			 * unregisters collectable at the end of the interaction
			 */
			public void interactWith(CollectableAreaEntity collectable) {
				collectable.setIsCollected();
				incrementScore(collectable.getPoints());
				if (collectable instanceof Diamond) {
					diamondSound.shouldBeStarted();
					//Keeping track of the number of diamonds is necessary to change the current level's signal
					((SuperPacmanArea)getOwnerArea()).incrementCounter(-1);
				} else if (collectable instanceof Bonus) {
					setInvincible(true);
				} else if (collectable instanceof Cherry) {
					cherrySound.shouldBeStarted();
				} else if (collectable instanceof Heart) {
					heartSound.shouldBeStarted();
					if (chances < 5) {
						incrementChances(1);
					}
				}

				getOwnerArea().unregisterActor(collectable);
				
			}

			/**
			 * @param ghost (Ghost)
			 * handles interaction with ghost
			 */
			public void interactWith(Ghost ghost) {
				if (!invincible || !ghost.isAfraid()) {
					isEaten();
					((SuperPacmanArea)getOwnerArea()).respawnGhosts();
				} else{
					eatGhostSound.shouldBeStarted();
					ghost.respawn();
					incrementScore(ghost.getPoints());
				}
			}

			public void interactWith(Portal portal) {
				portal.teleport(SuperPacmanPlayer.this);
				resetMotion();
			}

		public void interactWith(playableEnemy ghost) {
			if (!invincible || !ghost.isAfraid()) {
				isEaten();
				ghost.respawn();
			} else{
				ghost.respawn();
				incrementScore(ghost.getPoints());
				ghost.setAfraid(false);
			}
		}

		}
		
}