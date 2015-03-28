package com.Harold_Heist.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Table {
	static final float SIZE = 0.5f;

	Vector2 	position = new Vector2();
	Rectangle 	bounds = new Rectangle();

	public Table(Vector2 pos) {
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public static float getSize() {
		return SIZE;
	}
}
