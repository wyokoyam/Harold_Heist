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
    /** Foods **/
    Array<Food> foodArray = new Array<Food>();

    State state = State.STATE_RUNNING;

	// Getters -----------

	public Protagonist getProtagonist() {
		return protag;
	}

	public Antagonist getAntagonist(){
		return antag;
	}

    public Array<Food> getFoodArray() {
        return foodArray;
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

        ArrayList<Vector2> foodPositions = new ArrayList<Vector2>();
        for (int foodIndex = 0; foodIndex < 4; foodIndex++) {
            for (int i = 0; i < 5; i++) {
                Vector2 position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                Vector2 upperLeft = new Vector2(position.x, position.y + Food.SIZE);
                Vector2 upperRight = new Vector2(position.x + Food.SIZE, position.y + Food.SIZE);
                Vector2 bottomRight = new Vector2(position.x + Food.SIZE, position.y);

                while (foodPositions.contains(position) || foodPositions.contains(upperLeft) || foodPositions.contains(upperRight) || foodPositions.contains(bottomRight)) {
                    position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                    upperLeft = new Vector2(position.x, position.y + Food.SIZE);
                    upperRight = new Vector2(position.x + Food.SIZE, position.y + Food.SIZE);
                    bottomRight = new Vector2(position.x + Food.SIZE, position.y);
                }

                if (!foodPositions.contains(position) || (!foodPositions.contains(upperLeft)) || (!foodPositions.contains(upperRight)) || (!foodPositions.contains(bottomRight))) {
                    for (int x = 0; x < Food.SIZE; x++) {
                        for (int y = 0; y < Food.SIZE; y++) {
                            foodPositions.add(new Vector2(position.x + x, position.y + y));
                        }
                    }
                }

                Food food = new Food(position, foodIndex);

                while ((food.getPosition().x == 40 && food.getPosition().y == 40) || (food.getPosition().x == 1 && food.getPosition().y == 1)) {
                    food = new Food(new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight())), foodIndex);
                }
                foodArray.add(food);
            }
        }

    }

    public void removeFood(Vector2 foodPosition) {
        for (int i = 0; i < foodArray.size; i++) {
            if (foodArray.get(i).getPosition().equals(foodPosition)) {
                foodArray.removeIndex(i);
            }
        }
    }
}
