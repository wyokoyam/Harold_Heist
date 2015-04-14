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


	public void setSize (float w, float h) {
		this.width = w;
		this.height = h;
	}
	
	private TiledMap tiledMap;
	private BatchTiledMapRenderer tiledMapRenderer;

    private Protagonist protag;
    private Antagonist antag;
    private Array<Fruit> fruits;

    private ArrayList<Shape2D> collisionShapes;
    private MapLayer objectLayer;

    public CafeMacRenderer(CafeMac cafeMac, boolean debug) {
        protag = cafeMac.getProtagonist();
        antag = cafeMac.getAntagonist();
        fruits = cafeMac.getFruitArray();
        this.cafeMac = cafeMac;
		
		tiledMap = new TmxMapLoader().load("graphics/cafeMacMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_WIDTH * (height/width));
        this.cam.position.set(CAMERA_WIDTH/2, CAMERA_WIDTH * (height/width)/2, 0);

		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        gameScore = 0;
        gameScoreName = "Score: 0";
        scoreFont = new BitmapFont();

        debugRenderer = new ShapeRenderer();

        objectLayer = tiledMap.getLayers().get("Collision");
        MapObjects collisionObjects = objectLayer.getObjects();
        collisionShapes = new ArrayList<Shape2D>();

        for (MapObject obj: collisionObjects) {
            if (obj.getClass() == RectangleMapObject.class) {
                RectangleMapObject rectObj = (RectangleMapObject)obj;
                Rectangle rect = rectObj.getRectangle();
                collisionShapes.add(rect);
            } else {
                EllipseMapObject ellipseObj = (EllipseMapObject)obj;
                Ellipse ellip = ellipseObj.getEllipse();
                collisionShapes.add(ellip);
            }
        }
	}

	public void render() {

        this.cam.update();
        tiledMapRenderer.setView(this.cam);
        tiledMapRenderer.render();
        if (debug) {

            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (Shape2D shape: collisionShapes) {
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
            debugRenderer.end();
        }

		spriteBatch.begin();
		drawProtag();
		drawAntag();
        drawFruits();
        scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreFont.draw(spriteBatch, gameScoreName, 730, 20);
        spriteBatch.end();
        collisionHandler();
	}

    private void collisionHandler() {
        for (int i = 0; i < collisionShapes.size(); i++) {
            Shape2D shape = collisionShapes.get(i);
            objectCollisionHandler(protag.getPosition(), shape, true);
            objectCollisionHandler(antag.getPosition(), shape, false);
        }
        wallCollisionHandler(protag.getPosition(), true);
        wallCollisionHandler(antag.getPosition(), false);
        characterCollisionHandler(protag.getPosition(), antag.getPosition());

    }

    private void objectCollisionHandler(Vector2 character, Shape2D shape, boolean isProtag) {
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

//            collision left of object
        if (charX + charSize > shapeX && charX + charSize < shapeX + 10 && charY + charSize > shapeY && charY < shapeY + shapeHeight) {
            character.x = shapeX - charSize;
        }

//            collision right of object
        else if (charX < shapeX + shapeWidth && charX > shapeX + shapeWidth - 10 && charY + charSize > shapeY && charY < shapeY + shapeHeight) {
            character.x = shapeX + shapeWidth;
        }

        //collision top of object
        else if (charX + charSize> shapeX && charX < shapeX + shapeWidth && charY < shapeY + shapeHeight && charY > shapeY + shapeHeight - 10) {
            character.y = shapeY + shapeHeight;
        }

        //collision below object
        else if (charX + charSize > shapeX && charX < shapeX + shapeWidth && charY + charSize > shapeY && charY + charSize < shapeY + 10) {
            character.y = shapeY - charSize;
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

    private void characterCollisionHandler(Vector2 protagPosition, Vector2 antagPosition) {
        float protagX = protagPosition.x;
        float protagY = protagPosition.y;
        float protagSize = protag.getSize();

        float antagX = antagPosition.x;
        float antagY = antagPosition.y;
        float antagSize = antag.getSize();

        // collision left of antag
        if (protagX + protagSize > antagX && protagX + protagSize < antagX + 10 && protagY + protagSize > antagY && protagY < antagY + antagSize) {
            cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
        }

        // collision right of antag
        else if (protagX < antagX + antagSize && protagX > antagX + antagSize - 10 && protagY + protagSize > antagY && protagY < antagY + antagSize) {
            cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
        }

        // collision top of antag
        else if (protagX + protagSize> antagX && protagX < antagX + antagSize && protagY < antagY + antagSize && protagY > antagY + antagSize - 10) {
            cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
        }

        // collision below antag
        else if (protagX + protagSize > antagX && protagX < antagX + antagSize && protagY + protagSize > antagY && protagY + protagSize < antagY + 10) {
            cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
        }
    }

	private void drawProtag() {
		float xCoordinate = protag.getPosition().x ;
		float yCoordinate = protag.getPosition().y ;
        float protagSize = Protagonist.getSize();

        if (debug) {
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            Rectangle rect = protag.getBounds();
            debugRenderer.setColor(new Color(0, 1, 0, 1));
            debugRenderer.rect(xCoordinate, yCoordinate, rect.width, rect.height);
            debugRenderer.end();
        }

        if (protag.getState() == Protagonist.State.FACERIGHT) {
            spriteBatch.draw(Assets.protagRight, xCoordinate, yCoordinate, protagSize, protagSize);
        }
		else if (protag.getState() == Protagonist.State.FACELEFT) {
			spriteBatch.draw(Assets.protagLeft, xCoordinate, yCoordinate, protagSize, protagSize);
		}
		else if (protag.getState() == Protagonist.State.FACEUP) {
			spriteBatch.draw(Assets.protagUp, xCoordinate, yCoordinate, protagSize, protagSize);
		}
		else if (protag.getState() == Protagonist.State.FACEDOWN) {
			spriteBatch.draw(Assets.protagDown, xCoordinate, yCoordinate, protagSize, protagSize);
		}
	}
	
	private void drawAntag(){
		float xCoordinate = antag.getPosition().x ;
		float yCoordinate = antag.getPosition().y ;
        float antagSize = Antagonist.getSize();

        if (debug) {
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            Rectangle rect = antag.getBounds();
            debugRenderer.setColor(new Color(0, 1, 0, 1));
            debugRenderer.rect(xCoordinate, yCoordinate, rect.width, rect.height);
            debugRenderer.end();
        }

        if (antag.getState() == Antagonist.State.FACERIGHT) {
			spriteBatch.draw(Assets.antagRight, xCoordinate, yCoordinate, antagSize, antagSize);
		}
		else if (antag.getState() == Antagonist.State.FACELEFT) {
			spriteBatch.draw(Assets.antagLeft, xCoordinate, yCoordinate, antagSize, antagSize);
		}
		else if (antag.getState() == Antagonist.State.FACEUP) {
			spriteBatch.draw(Assets.antagUp, xCoordinate, yCoordinate, antagSize, antagSize);
		}
		else if (antag.getState() == Antagonist.State.FACEDOWN) {
			spriteBatch.draw(Assets.antagDown, xCoordinate, yCoordinate, antagSize, antagSize);
		}
	}

    private void drawFruits() {
        for (Fruit fruit: fruits) {
            spriteBatch.draw(Assets.fruitTexture, fruit.getPosition().x, fruit.getPosition().y, fruit.getSize(), fruit.getSize());
        }
    }
//	private void drawDebug() {
//        // render blocks
//        debugRenderer.setProjectionMatrix(cam.combined);
//        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
////        for (Table table : cafeMac.getTables()) {
//        for (Rectangle rect: collisionShapes) {
////            Rectangle rect = table.getBounds();
////            float x1 = table.getPosition().x + rect.x;
////            float y1 = table.getPosition().y + rect.y;
//            debugRenderer.setColor(new Color(1, 0, 0, 1));
//            debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
//        }
//    }
//
//		// render protagonist
//		Protagonist protag = cafeMac.getProtagonist();
//		Rectangle rect = protag.getBounds();
//		float x1 = protag.getPosition().x + rect.x;
//		float y1 = protag.getPosition().y + rect.y;
//		debugRenderer.setColor(new Color(0, 1, 0, 1));
//		debugRenderer.rect(x1, y1, rect.width, rect.height);
//		debugRenderer.end();
//
//		// render antagonist
//		Antagonist antag = cafeMac.getAntagonist();
//		Rectangle Arect = protag.getBounds();
//		float Ax1 = protag.getPosition().x + rect.x;
//		float Ay1 = protag.getPosition().y + rect.y;
//		debugRenderer.setColor(new Color(0, 1, 0, 1));
//		debugRenderer.rect(Ax1, Ay1, Arect.width, Arect.height);
//		debugRenderer.end();
//	}
}