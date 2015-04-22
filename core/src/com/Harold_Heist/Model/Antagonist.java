package com.Harold_Heist.Model;

import com.Harold_Heist.Model.Protagonist.State;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Antagonist extends Rectangle{
	public enum State {
		FACELEFT, FACERIGHT, FACEUP, FACEDOWN;
	}
	
	static final float SIZE = 32;
    static final float SPEED = SIZE * 2.5f; // changed from final

    Vector2 	position = new Vector2();
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
