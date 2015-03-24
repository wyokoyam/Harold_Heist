package com.Harold_Heist.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Antagonist extends Rectangle{
	
	static final float SPEED = 2f; 
	static final float SIZE = .05f; 
	
	Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
	boolean		facingLeft = true;
	
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
		
}
