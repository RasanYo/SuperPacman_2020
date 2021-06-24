package ch.epfl.cs107.play.game.superpacman;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.playableEnemy;
import ch.epfl.cs107.play.game.superpacman.area.Level0;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.Level2;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.gui.Menu;
import ch.epfl.cs107.play.game.superpacman.gui.MenuEnd;
import ch.epfl.cs107.play.game.superpacman.gui.PauseMenu;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class SuperPacman extends RPG {


	private boolean paused = false;
	private Menu start;
	private MenuEnd endMenu;
	private PauseMenu pauseMenu;
	private boolean isStart = false;
	private boolean end = false;

	private SuperPacmanPlayer player;



	@Override
	public String getTitle() {
		return "Super Pac-Man";
	}


	/**
	 *
	 * @param b (Button)
	 * changes the value of paused given a certain keyboard input
	 */
	public void pause(Button b) {
		if(b.isPressed() && paused == false) {
			paused = true;
		} else if (b.isPressed() && paused == true) {
			paused = false;
		}
	}

	/**
	 *
	 * @param pause (boolean)
	 * changes value of paused
	 */
	public void setPaused(boolean pause) {
		paused = pause;
	}

	/**
	 *
	 * @param deltaTime (float)
	 * updates state of SuperPacman and its content and has integrated pause function
	 */
	public void update(float deltaTime) {
		Keyboard keyboard = getCurrentArea().getKeyboard();
		if(!isStart) {
			start.draw(getWindow());
			if(keyboard.get(Keyboard.ENTER).isPressed()) {
				isStart = true;
			}
		}else{
			pause(keyboard.get(Keyboard.P));

			if (paused) {
				pauseMenu.draw(getWindow());
				return;
			}
			if(player.isgameOver()) {
				endMenu.draw(getWindow());
				return;
			}

			super.update(deltaTime);
		}
	}

	
	private void createAreas() {
		addArea(new Level0());
		addArea(new Level1());
		addArea(new Level2());

	}
	
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			createAreas();
			Area area = setCurrentArea("superpacman/Level" + SuperPacmanArea.lvlIndex, true);
			player = new SuperPacmanPlayer(getCurrentArea(), Orientation.RIGHT, ((SuperPacmanArea)area).getSpawnPosition());
			initPlayer(player);
			start = new Menu();
			pauseMenu = new PauseMenu();
			endMenu = new MenuEnd();
			return true;
		} else {
			return false;
		}
	}
	
	public void end() {}
	

}
