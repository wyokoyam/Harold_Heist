package com.Harold_Heist.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Protagonist extends Rectangle {
	public enum State {
		FACELEFT, FACERIGHT, FACEUP, FACEDOWN;
	}

	static final float SPEED = 2f;	// unit per second
	static final float JUMP_VELOCITY = 1f;
	static final float SIZE = 0.5f; // half a unit

	Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State		state = State.FACERIGHT;
//	boolean		facingLeft = true;

	public Protagonist(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public static float getSpeed() {
		return SPEED;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public static float getJumpVelocity() {
		return JUMP_VELOCITY;
	}
	
	public static float getSize() {
		return SIZE;
	}
	
}
