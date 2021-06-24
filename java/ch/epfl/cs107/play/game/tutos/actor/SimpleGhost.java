package ch.epfl.cs107.play.game.tutos.actor;
import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SimpleGhost extends Entity{
	
	private float hp;
	private TextGraphics hpText;
	private Sprite sprite;
	
	public boolean isWeak() {
		if (this.hp <= 0) {
			return true;
		}
		return false;
	}
	
	public void strenghten() {
		this.hp = 10;
	}
	
	public SimpleGhost(Vector position, String spriteName) {
		super(position);
		sprite = new Sprite(spriteName, 1, 1.f, this);
		this.hp = 10;
		this.hpText = new TextGraphics(Integer.toString((int)hp), 0.4f, Color.BLUE);
		hpText.setParent(this);
		this.hpText.setAnchor(new Vector(-0.3f, 0.1f));
	
	}
	
	
	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
		hpText.draw(canvas);
	}
	
	
	public void update(float deltaTime) {
		if (!(this.hp <= 0)) {
			this.hp -= deltaTime;
				if (this.hp < 0) {
					this.hp = 0;
				}
			this.hpText.setText(Integer.toString((int)this.hp));
		}
	}
	
	public float getCameraScaleFactor() {
		return 10.f;
	}
	
	public void moveUp(float deltaTime) {
		setCurrentPosition(getPosition().add(0.f, -deltaTime));
	}
	
	public void moveDown(float deltaTime) {
		setCurrentPosition(getPosition().add(0.f, deltaTime));
	}
	
	public void moveLeft(float deltaTime) {
		setCurrentPosition(getPosition().add(deltaTime, 0.f));
	}
	
	public void moveRight(float deltaTime) {
		setCurrentPosition(getPosition().add(-deltaTime, 0.f));
	}

}
