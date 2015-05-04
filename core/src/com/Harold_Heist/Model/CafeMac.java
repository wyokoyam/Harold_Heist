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
    /** Foods **/
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

//        avoidCollisionShapes();
        addCollisionShapes();
//        avoidProtagAntagPositions();
//        avoidWalls();

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
//        for (int x = 0; x < Food.SIZE; x++) {
//            for (int y = 0; y < Food.SIZE; y++) {
//                takenPositions.remove(new Vector2(foodPosition.x + x, foodPosition.y + y));
//            }
//        }
    }

    public Food addFood() {
        Random rand = new Random();
        Vector2 position = getFreePosition();
        Food food = new Food(position, rand.nextInt(4));
        foodArray.add(food);
        return food;
    }

    private Vector2 getFreePosition() {
        Random rand = new Random();
        Vector2 position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
        Vector2 upperLeft = new Vector2(position.x, position.y + Food.SIZE);
        Vector2 upperRight = new Vector2(position.x + Food.SIZE, position.y + Food.SIZE);
        Vector2 bottomRight = new Vector2(position.x + Food.SIZE, position.y);

        while (takenPositions.contains(position) || takenPositions.contains(upperLeft) || takenPositions.contains(upperRight) || takenPositions.contains(bottomRight)) {
            position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
            upperLeft = new Vector2(position.x, position.y + Food.SIZE);
            upperRight = new Vector2(position.x + Food.SIZE, position.y + Food.SIZE);
            bottomRight = new Vector2(position.x + Food.SIZE, position.y);
        }

        if (!takenPositions.contains(position) || (!takenPositions.contains(upperLeft)) || (!takenPositions.contains(upperRight)) || (!takenPositions.contains(bottomRight))) {
            for (int x = 0; x < Food.SIZE; x++) {
                for (int y = 0; y < Food.SIZE; y++) {
                    takenPositions.add(new Vector2(position.x + x, position.y + y));
                }
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

    private void avoidProtagAntagPositions() {
        for (int x = 0; x < Protagonist.SIZE; x++) {
            for (int y = 0; y < Protagonist.SIZE; y++) {
                takenPositions.add(new Vector2(protag.x + x, protag.y + y));
                takenPositions.add(new Vector2(antag.x + x, antag.y + y));
            }
        }
    }

    private void avoidWalls() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        for (float x = 0; x < screenWidth; x++) {
            for (float y = 0; y < Food.SIZE; y++) {
                takenPositions.add(new Vector2(x, y));
            }
            for (float y = screenHeight; y > screenHeight - Food.SIZE; y--) {
                takenPositions.add(new Vector2(x, y));
            }
        }
        for (float y = 0; y < screenHeight; y++) {
            for (float x = 0; x < Food.SIZE; x++) {
                takenPositions.add(new Vector2(x, y));
            }
            for (float x = screenWidth; x > screenWidth - Food.SIZE; x--) {
                takenPositions.add(new Vector2(x, y));
            }
        }
    }

    private void avoidCollisionShapes() {
        addCollisionShapes();
        float shapeX;
        float shapeY;
        float shapeWidth;
        float shapeHeight;
        Rectangle rect;
        Ellipse ellip;
        for (Shape2D shape: collisionShapes) {
            if (shape.getClass() == Rectangle.class) {
                rect = (Rectangle) shape;
                shapeX = rect.getX();
                shapeY = rect.getY();
                shapeWidth = rect.getWidth();
                shapeHeight = rect.getHeight();

            } else {
                ellip = (Ellipse) shape;
                shapeX = ellip.x;
                shapeY = ellip.y;
                shapeWidth = ellip.width;
                shapeHeight = ellip.height;
            }

            for (int x = 0; x < shapeWidth; x++) {
                for (int y = 0; y < shapeHeight; y++) {
                    takenPositions.add(new Vector2(shapeX + x, shapeY + y));
                }
            }
        }
    }
}
