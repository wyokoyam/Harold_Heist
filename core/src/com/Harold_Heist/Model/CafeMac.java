package com.Harold_Heist.Model;

import java.util.ArrayList;
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

        ArrayList<Vector2> fruitPositions = new ArrayList<Vector2>();
        for (int foodIndex = 0; foodIndex < 4; foodIndex++) {
            for (int i = 0; i < 5; i++) {
                Vector2 position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                Vector2 upperLeft = new Vector2(position.x, position.y + Fruit.SIZE);
                Vector2 upperRight = new Vector2(position.x + Fruit.SIZE, position.y + Fruit.SIZE);
                Vector2 bottomRight = new Vector2(position.x + Fruit.SIZE, position.y);

                while (fruitPositions.contains(position) || fruitPositions.contains(upperLeft) || fruitPositions.contains(upperRight) || fruitPositions.contains(bottomRight)) {
                    position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                    upperLeft = new Vector2(position.x, position.y + Fruit.SIZE);
                    upperRight = new Vector2(position.x + Fruit.SIZE, position.y + Fruit.SIZE);
                    bottomRight = new Vector2(position.x + Fruit.SIZE, position.y);
                }

                if (!fruitPositions.contains(position) || (!fruitPositions.contains(upperLeft)) || (!fruitPositions.contains(upperRight)) || (!fruitPositions.contains(bottomRight))) {
                    for (int x = 0; x < Fruit.SIZE; x++) {
                        for (int y = 0; y < Fruit.SIZE; y++) {
                            fruitPositions.add(new Vector2(position.x + x, position.y + y));
                        }
                    }
                }

                Fruit fruit = new Fruit(position, foodIndex);

                while ((fruit.getPosition().x == 40 && fruit.getPosition().y == 40) || (fruit.getPosition().x == 1 && fruit.getPosition().y == 1)) {
                    fruit = new Fruit(new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight())), foodIndex);
                }
                fruitArray.add(fruit);
            }
        }

    }

    public void removeFruit(Vector2 fruitPosition) {
        for (int i = 0; i < fruitArray.size; i++) {
            if (fruitArray.get(i).getPosition().equals(fruitPosition)) {
                fruitArray.removeIndex(i);
            }
        }
    }
}
