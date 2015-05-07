package com.Harold_Heist.Model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class CafeMac {

    public enum State {
        STATE_RUNNING, STATE_GAMEOVER;
    }

	/** Our player controlled hero **/
    Protagonist protag;
	/** Our heroes autonomous nemesis **/
	Antagonist antag;
    Antagonist evilTwinAntag;
    /** Foods **/
//    ArrayList<Food> foodArray = new ArrayList<Food>();
    Array<Food> foodArray = new Array<Food>();
    /** Positions that already contains object **/
    ArrayList<Vector2> takenPositions = new ArrayList<Vector2>();

    TiledMap tiledMap = new TmxMapLoader().load("graphics/cafeMacMap.tmx");

    MapLayer objectLayer;
    MapObjects collisionObjects;
    ArrayList<Shape2D> collisionShapes;

    State state = State.STATE_RUNNING;

	// Getters -----------

	public Protagonist getProtagonist() { return protag; }

	public Antagonist getAntagonist(){
		return antag;
	}

    public Antagonist getEvilTwin(){ return evilTwinAntag; }

    public Array<Food> getFoodArray() {
        return foodArray;
    }

    public State getState() {
        return state;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public ArrayList<Shape2D> getCollisionShapes() {
        return collisionShapes;
    }

    public ArrayList<Vector2> getTakenPositions() { return takenPositions;}
    // --------------------

    // Setters -----------

    public void setState(State state) {this.state = state;}

    // --------------------
	public CafeMac() {
		createCafeMac();
	}

	private void createCafeMac() {
        protag = new Protagonist(new Vector2(50, 50));
        antag = new Antagonist(new Vector2(1, 1));
        evilTwinAntag = new Antagonist(new Vector2(330, 500));
        addCollisionShapes();

        for (int foodIndex = 0; foodIndex < 4; foodIndex++) {
            for (int i = 0; i < 3; i++) {
                Vector2 position = getFreePosition();
                Food food = new Food(position, foodIndex);
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

    public Food addFood() {
        Random rand = new Random();
        Vector2 position = getFreePosition();
//        Vector2 position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
        Food food = new Food(position, rand.nextInt(4));
        foodArray.add(food);
        return food;
    }

    private Vector2 getFreePosition() {
        Random rand = new Random();
        Vector2 position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
        float foodX = position.x;
        float foodY = position.y;
        float foodSize = Food.SIZE;

        for (int i = 0; i < foodArray.size; i++) {
            Food otherFood = foodArray.get(i);
            float otherFoodX = otherFood.getPosition().x;
            float otherFoodY = otherFood.getPosition().y;
            float otherFoodSize = otherFood.getSize();
//
//            // check bottom left of food
            while ((foodX >= otherFoodX && foodX <= otherFoodX + otherFoodSize) && (foodY >= otherFoodY && foodY <= otherFoodY + otherFoodSize)) {
                position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                foodX = position.x;
                foodY = position.y;
            }

            // check bottom right
            while ((foodX + foodSize >= otherFoodX && foodX + foodSize <= otherFoodX + otherFoodSize) && (foodY >= otherFoodY && foodY <= otherFoodY + otherFoodSize)) {
                position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                foodX = position.x;
                foodY = position.y;
            }

            // check upper left

            while ((foodX >= otherFoodX && foodX <= otherFoodX + otherFoodSize) && (foodY + foodSize >= otherFoodY && foodY + foodSize <= otherFoodY + otherFoodSize)) {
                position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                foodX = position.x;
                foodY = position.y;
            }

            // check upper right

            while ((foodX + foodSize >= otherFoodX && foodX + foodSize <= otherFoodX + otherFoodSize) && (foodY + foodSize >= otherFoodY && foodY + foodSize <= otherFoodY + otherFoodSize)) {
                position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                foodX = position.x;
                foodY = position.y;
            }
        }
        return position;
    }

    private void addCollisionShapes() {
        objectLayer = tiledMap.getLayers().get("Collision");
        collisionObjects = objectLayer.getObjects();
        collisionShapes = new ArrayList<Shape2D>();

        for (MapObject obj : collisionObjects) {
            if (obj.getClass() == RectangleMapObject.class) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Rectangle rect = rectObj.getRectangle();
                collisionShapes.add(rect);
            } else {
                EllipseMapObject ellipseObj = (EllipseMapObject) obj;
                Ellipse ellip = ellipseObj.getEllipse();
                collisionShapes.add(ellip);
            }
        }
    }

}
