package com.Harold_Heist.Model;

import com.Harold_Heist.Model.Protagonist.State;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Antagonist extends Rectangle{
	public enum State {
		FACELEFT, FACERIGHT, FACEUP, FACEDOWN;
	}
	
	static final float SPEED = 2f; 
	static final float SIZE = 0.5f; 
	
	Vector2 	position = new Vector2();
//	Vector2 	acceleration = new Vector2();
//	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State state = State.FACERIGHT;
	
	public Antagonist(Vector2 position){
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
	
	public static float getSize() {
		return SIZE;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
		
}
