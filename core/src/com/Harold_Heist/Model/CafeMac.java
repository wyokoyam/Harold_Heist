package com.Harold_Heist.Model;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



public class CafeMac {

    public enum State {
        STATE_RUNNING, STATE_GAMEOVER;
    }

	/** Our player controlled hero **/
    Protagonist protag;
	/** Our heroes autonomous nemesis **/
	Antagonist antag;
    /** Fruits **/
    Array<Fruit> fruitArray = new Array<Fruit>();

    State state = State.STATE_RUNNING;

	// Getters -----------

	public Protagonist getProtagonist() {
		return protag;
	}

	public Antagonist getAntagonist(){
		return antag;
	}

    public Array<Fruit> getFruitArray() {
        return fruitArray;
    }

    public State getState() {return state;}
	// --------------------

    // Setters -----------

    public void setState(State state) {this.state = state;}

    // --------------------
	public CafeMac() {
		createCafeMac();
	}

	private void createCafeMac() {
        protag = new Protagonist(new Vector2(40, 40));
        antag = new Antagonist(new Vector2(1, 1));

        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            Fruit fruit = new Fruit(new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight())));
            while ((fruit.getPosition().x == 40 && fruit.getPosition().y == 40) || (fruit.getPosition().x == 1 && fruit.getPosition().y == 1)) {
                fruit = new Fruit(new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight())));
            }
            fruitArray.add(fruit);
        }
    }
}
