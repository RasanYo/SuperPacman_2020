package ch.epfl.cs107.play.game.tutos.area.tuto1;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class Tuto1 extends AreaGame{
	
	private SimpleGhost player;
	
	private void createAreas() {
		addArea(new Ferme());
		addArea(new Village());
	
	}
	
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			 createAreas();
			// traitement specifiques à Tuto1
			Area area = setCurrentArea("zelda/Village", true);
			player = new SimpleGhost(new Vector(18, 7), "ghost.1");
			area.registerActor(player);
			area.setViewCandidate(player);
			return true;
		} else {
			return false;
		}
	}
	
	public void end() {}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		Keyboard keyboard = getWindow().getKeyboard();
		Button key = keyboard.get(Keyboard.UP);
		if (key.isDown()) {
			player.moveUp(deltaTime);
		}
		key = keyboard.get(Keyboard.DOWN);
		if (key.isDown()) {
			player.moveDown(deltaTime);
		}
		key = keyboard.get(Keyboard.LEFT);
		if (key.isDown()) {
			player.moveLeft(deltaTime);
		}
		key = keyboard.get(Keyboard.RIGHT);
		if (key.isDown()) {
			player.moveRight(deltaTime);
		}
		if (player.isWeak()) {
			switchArea();
			player.strenghten();
		}
		
	}
	
	public String getTitle() {
		return "Tuto1";
	}
	
	public void switchArea() {
		Area area = getCurrentArea();
		area.unregisterActor(player);
		if (area.getTitle().equals("zelda/Ferme")) {
			area = setCurrentArea("zelda/Village", true);
		}else if (area.getTitle().equals("zelda/Village")) {
			area = setCurrentArea("zelda/Ferme", true);
		}
		area.registerActor(player);
		area.setViewCandidate(player);
	}
	

}
