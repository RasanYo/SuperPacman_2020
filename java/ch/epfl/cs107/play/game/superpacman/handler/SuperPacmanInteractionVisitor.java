package ch.epfl.cs107.play.game.superpacman.handler;

import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.Collectables.CollectableAreaEntity;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.playableEnemy;
import ch.epfl.cs107.play.game.superpacman.solidEntities.Portal;

import javax.sound.sampled.Port;

public interface SuperPacmanInteractionVisitor extends RPGInteractionVisitor {
	
	default public void interactWith(SuperPacmanPlayer player) {}
	
	default public void interactWith(CollectableAreaEntity collectable) {}
	
	default public void interactWith(Ghost ghost) {}

	default public void interactWith(playableEnemy ghost) {}

	default  public void interactWith(Portal portal){}

}
