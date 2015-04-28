package com.Harold_Heist.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Protagonist extends Rectangle {
	public enum State {
		FACELEFT, FACERIGHT, FACEUP, FACEDOWN;
	}

	static final float SIZE = 32;
    static final float SPEED = SIZE * 5.5f;	// unit per second

    Vector2 	position = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State		state = State.FACERIGHT;

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
	
	public static float getSize() {
		return SIZE;
	}

    public void keyboardControlsProtag() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.setState(Protagonist.State.FACELEFT);
            this.getPosition().x -= Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.setState(Protagonist.State.FACERIGHT);
            this.getPosition().x += Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.setState(Protagonist.State.FACEDOWN);
            this.getPosition().y -= Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.setState(Protagonist.State.FACEUP);
            this.getPosition().y += Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
    }


}
