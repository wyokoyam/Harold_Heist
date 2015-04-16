package com.Harold_Heist.View;

import com.Harold_Heist.Assets;
import com.Harold_Heist.Model.CafeMac;
import com.Harold_Heist.Model.Fruit;
import com.Harold_Heist.Model.Protagonist;
import com.Harold_Heist.Model.Antagonist;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


import java.util.ArrayList;


public class CafeMacRenderer {

    private static final float CAMERA_WIDTH = 25f;

    private CafeMac cafeMac;
    private OrthographicCamera cam;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private boolean debug = false;
    private float width;
    private float height;
    private ShapeRenderer debugRenderer;
    private ShapeRenderer shapeRenderer;
    private int gameScore;
    private String gameScoreName;
    BitmapFont scoreFont;


    public void setSize(float w, float h) {
        this.width = w;
        this.height = h;
    }

    private TiledMap tiledMap;
    private BatchTiledMapRenderer tiledMapRenderer;

    private Protagonist protag;
    private Antagonist antag;
    private Fruit fruit;
    private Array<Fruit> fruits;

    private ArrayList<Shape2D> collisionShapes;
    private MapLayer objectLayer;

    public CafeMacRenderer(CafeMac cafeMac, boolean debug) {
        protag = cafeMac.getProtagonist();
        antag = cafeMac.getAntagonist();
        fruits = cafeMac.getFruitArray();
        this.cafeMac = cafeMac;

        tiledMap = new TmxMapLoader().load("graphics/cafeMacMap.tmx");
//		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        //tiled map is shown correctly, but size of characters and fruits are wrong
//        this.cam = new OrthographicCamera();
//        this.cam.setToOrtho(false, CAMERA_WIDTH, CAMERA_WIDTH * (height/width));

        //map is small, but the objects are at the right place
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, width, height);

        this.cam.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        gameScore = 0;
        gameScoreName = "SCORE: " + gameScore;
        scoreFont = new BitmapFont();

        debugRenderer = new ShapeRenderer();

        objectLayer = tiledMap.getLayers().get("Collision");
        MapObjects collisionObjects = objectLayer.getObjects();
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

    public void render() {

        this.cam.update();
        tiledMapRenderer.setView(this.cam);
        tiledMapRenderer.render();
        if (debug) drawDebug();
        draw();
        collisionHandler();
    }

    private void collisionHandler() {
        for (Shape2D shape : collisionShapes) {
            obstacleCollisionHandler(protag.getPosition(), shape, true);
            obstacleCollisionHandler(antag.getPosition(), shape, false);
        }
        wallCollisionHandler(protag.getPosition(), true);
        wallCollisionHandler(antag.getPosition(), false);
        objectCollisionHandler(protag.getPosition(), antag.getPosition(), true);
        for (Fruit fruit : cafeMac.getFruitArray()) {
            objectCollisionHandler(protag.getPosition(), fruit.getPosition(), false);
        }
    }

    private void draw() {
        spriteBatch.begin();
        drawProtag();
        drawAntag();
        drawFruits();
        scoreFont.setColor(0, 0, 1, 0.8f);
        scoreFont.draw(spriteBatch, gameScoreName, 710, 20);
        spriteBatch.end();
    }

    private void obstacleCollisionHandler(Vector2 character, Shape2D shape, boolean isProtag) {
        float charX = character.x;
        float charY = character.y;
        float charSize;
        if (isProtag) {
            charSize = protag.getSize();
        } else {
            charSize = antag.getSize();
        }

        float shapeX;
        float shapeY;
        float shapeWidth;
        float shapeHeight;
        Rectangle rect;
        Ellipse ellip;

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

        // collision left of object
        if (charX + charSize > shapeX && charX + charSize < shapeX + 10 && charY + charSize > shapeY && charY < shapeY + shapeHeight) {
            character.x = shapeX - charSize;
        }

        // collision right of object
        else if (charX < shapeX + shapeWidth && charX > shapeX + shapeWidth - 10 && charY + charSize > shapeY && charY < shapeY + shapeHeight) {
            character.x = shapeX + shapeWidth;
        }

        // collision top of object
        else if (charX + charSize > shapeX && charX < shapeX + shapeWidth && charY < shapeY + shapeHeight && charY > shapeY + shapeHeight - 10) {
            character.y = shapeY + shapeHeight;
        }

        // collision below object
        else if (charX + charSize > shapeX && charX < shapeX + shapeWidth && charY + charSize > shapeY && charY + charSize < shapeY + 10) {
            character.y = shapeY - charSize;
        }
    }

    /**
     * If protagAndAntag is true, handle collision between protagonist and antagonist. If false, handle collision
     * between protagonist and fruit.
     */
    private void objectCollisionHandler(Vector2 object1Position, Vector2 object2Position, boolean protagAndAntag) {
        float object1X = object1Position.x;
        float object1Y = object1Position.y;
        float object1Size = protag.getSize();

        float object2X = object2Position.x;
        float object2Y = object2Position.y;
        float object2Size;
        if (protagAndAntag) object2Size = antag.getSize();
        else object2Size = fruit.getSize();

        // collision left of object
        if (object1X + object1Size > object2X && object1X + object1Size < object2X + 10 && object1Y + object1Size > object2Y && object1Y < object2Y + object2Size) {
            if (protagAndAntag) cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
            else {
                cafeMac.removeFruit(object2Position);
                gameScore++;
                gameScoreName = "SCORE: " + gameScore;
            }
        }

        // collision right of object
        else if (object1X < object2X + object2Size && object1X > object2X + object2Size - 10 && object1Y + object1Size > object2Y && object1Y < object2Y + object2Size) {
            if (protagAndAntag) cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
            else {
                cafeMac.removeFruit(object2Position);
                gameScore++;
                gameScoreName = "SCORE: " + gameScore;
            }
        }

        // collision top of object
        else if (object1X + object1Size > object2X && object1X < object2X + object2Size && object1Y < object2Y + object2Size && object1Y > object2Y + object2Size - 10) {
            if (protagAndAntag) cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
            else {
                cafeMac.removeFruit(object2Position);
                gameScore++;
                gameScoreName = "SCORE: " + gameScore;
            }
        }

        // collision below object
        else if (object1X + object1Size > object2X && object1X < object2X + object2Size && object1Y + object1Size > object2Y && object1Y + object1Size < object2Y + 10) {
            if (protagAndAntag) cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
            else {
                cafeMac.removeFruit(object2Position);
                gameScore++;
                gameScoreName = "SCORE: " + gameScore;
            }
        }
    }


    private void wallCollisionHandler(Vector2 character, boolean isProtag) {
        float charX = character.x;
        float charY = character.y;
        float charSize;
        if (isProtag) {
            charSize = protag.getSize();
        } else {
            charSize = antag.getSize();
        }

        if (charX < 0) protag.getPosition().x = 0;
        if (charX + charSize > width) character.x = width - charSize;

        if (charY < 0) protag.getPosition().y = 0;
        if (charY + charSize > height) character.y = height - charSize;
    }

    private void drawProtag() {
        float xCoordinate = protag.getPosition().x;
        float yCoordinate = protag.getPosition().y;
        float protagSize = Protagonist.getSize();

        if (protag.getState() == Protagonist.State.FACERIGHT) {
            spriteBatch.draw(Assets.protagRight, xCoordinate, yCoordinate, protagSize, protagSize);
        } else if (protag.getState() == Protagonist.State.FACELEFT) {
            spriteBatch.draw(Assets.protagLeft, xCoordinate, yCoordinate, protagSize, protagSize);
        } else if (protag.getState() == Protagonist.State.FACEUP) {
            spriteBatch.draw(Assets.protagUp, xCoordinate, yCoordinate, protagSize, protagSize);
        } else if (protag.getState() == Protagonist.State.FACEDOWN) {
            spriteBatch.draw(Assets.protagDown, xCoordinate, yCoordinate, protagSize, protagSize);
        }
    }

    private void drawAntag() {
        float xCoordinate = antag.getPosition().x;
        float yCoordinate = antag.getPosition().y;
        float antagSize = Antagonist.getSize();

        if (antag.getState() == Antagonist.State.FACERIGHT) {
            spriteBatch.draw(Assets.antagRight, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (antag.getState() == Antagonist.State.FACELEFT) {
            spriteBatch.draw(Assets.antagLeft, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (antag.getState() == Antagonist.State.FACEUP) {
            spriteBatch.draw(Assets.antagUp, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (antag.getState() == Antagonist.State.FACEDOWN) {
            spriteBatch.draw(Assets.antagDown, xCoordinate, yCoordinate, antagSize, antagSize);
        }
    }

    private void drawFruits() {
        for (Fruit fruit : fruits) {
            spriteBatch.draw(Assets.fruitTexture, fruit.getPosition().x, fruit.getPosition().y, fruit.getSize(), fruit.getSize());
        }
    }

    private void drawDebug() {
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Outline the shapes
        for (Shape2D shape : collisionShapes) {
            if (shape.getClass() == Rectangle.class) {
                Rectangle rect = (Rectangle) shape;
                debugRenderer.setColor(new Color(0, 1, 0, 1));
                debugRenderer.rect(rect.getX(), rect.getY(), rect.width, rect.height);

            } else {
                Ellipse ellip = (Ellipse) shape;
                debugRenderer.setColor(new Color(0, 1, 0, 1));
                debugRenderer.rect(ellip.x, ellip.y, ellip.width, ellip.height);
            }
        }

        // Outline protagonist
        Rectangle protagBounds = protag.getBounds();
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.rect(protag.getPosition().x, protag.getPosition().y, protagBounds.width, protagBounds.height);

        // Outline antagonist
        Rectangle antagBounds = antag.getBounds();
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.rect(antag.getPosition().x, antag.getPosition().y, antagBounds.width, antagBounds.height);

        debugRenderer.end();
    }

}