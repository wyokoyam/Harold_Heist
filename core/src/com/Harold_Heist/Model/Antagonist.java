package com.Harold_Heist.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Antagonist extends Rectangle{

	static final float SIZE = 32;
    static final float SPEED = SIZE * 4;
    static final float START_X = 1;
    static final float START_Y = 1;

    Vector2 	position = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State state = State.FACERIGHT;

    public enum State {FACELEFT, FACERIGHT, FACEUP, FACEDOWN; }
	
	public Antagonist(Vector2 position){
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public Vector2 getPosition(){return position; }

    public Vector2 getStartPosition(){return new Vector2(START_X, START_Y);}
	
	public Rectangle getBounds(){return bounds; }
	
	public static float getSpeed(){return SPEED; }
	
	public static float getSize(){return SIZE; }
	
	public State getState(){return state; }
	
	public void setState(State state){this.state = state; }

    public void goNorth(){
        this.setState(Antagonist.State.FACEUP);
        this.getPosition().y += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
    }

    public void goNorthEast(){
        this.setState(Antagonist.State.FACERIGHT);
        this.getPosition().x += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        this.getPosition().y += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
    }

    public void goEast(){
        this.setState(Antagonist.State.FACERIGHT);
        this.getPosition().x += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
    }

    public void goSouthEast(){
        this.setState(Antagonist.State.FACERIGHT);
        this.getPosition().x += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        this.getPosition().y -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
    }

    public void goSouth(){
        this.setState(Antagonist.State.FACEDOWN);
        this.getPosition().y -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
    }

    public void goSouthWest(){
        this.setState(Antagonist.State.FACELEFT);
        this.getPosition().x -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        this.getPosition().y -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
    }

    public void goWest(){
        this.setState(Antagonist.State.FACELEFT);
        this.getPosition().x -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
    }

    public void goNorthWest(){
        this.setState(Antagonist.State.FACELEFT);
        this.getPosition().x -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        this.getPosition().y += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
    }
		
}
