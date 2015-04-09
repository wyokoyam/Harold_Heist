package com.Harold_Heist.View;

import com.Harold_Heist.Assets;
import com.Harold_Heist.Model.CafeMac;
import com.Harold_Heist.Model.Protagonist;
import com.Harold_Heist.Model.Antagonist;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;


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

	public void setSize (float w, float h) {
		this.width = w;
		this.height = h;
	}
	
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;

    public Protagonist protag;
    public Antagonist antag;

    private ArrayList<Rectangle> collisionRects;
    private MapLayer objectLayer;

    public CafeMacRenderer(CafeMac cafeMac, boolean debug) {
        protag = cafeMac.getProtagonist();
        antag = cafeMac.getAntagonist();
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

        debugRenderer = new ShapeRenderer();

        objectLayer = tiledMap.getLayers().get("Collision");
        MapObjects collisionObjects = objectLayer.getObjects();
        collisionRects = new ArrayList<Rectangle>();

        for (MapObject obj: collisionObjects) {
            RectangleMapObject rectObj = (RectangleMapObject)obj;
            Rectangle rect = rectObj.getRectangle();
            collisionRects.add(rect);
        }
	}


	public void render() {

        this.cam.update();
        tiledMapRenderer.setView(this.cam);
        tiledMapRenderer.render();
        if (debug) {
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (Rectangle rect: collisionRects) {
                debugRenderer.setColor(new Color(0, 1, 0, 1));
                debugRenderer.rect(rect.getX(), rect.getY(), rect.width, rect.height);
            }
            debugRenderer.end();
        }

		spriteBatch.begin();
		drawProtag();
		drawAntag();
        spriteBatch.end();
        collisionHandler();
	}

    private void collisionHandler() {
        for (int i = 0; i < collisionRects.size(); i++) {
            float protagX = protag.getPosition().x;
            float protagY = protag.getPosition().y;
            float protagSize = protag.getSize();
            Rectangle rect = collisionRects.get(i);
            float rectX = rect.getX();
            float rectY = rect.getY();
            float rectWidth = rect.getWidth();
            float rectHeight = rect.getHeight();

//            collision left of object
            if (protagX + protagSize > rectX && protagX + protagSize < rectX + 10 && protagY + protagSize > rectY && protagY < rectY + rectHeight) {
                protag.getPosition().x = rectX - protagSize;
            }

//            collision right of object
            else if (protagX < rectX + rectWidth && protagX > rectX + rectWidth - 10 && protagY + protagSize > rectY && protagY < rectY + rectHeight) {
                protag.getPosition().x = rectX + rectWidth;
            }

            //collision top of object
            else if (protagX + protagSize> rectX && protagX < rectX + rectWidth && protagY < rectY + rectHeight && protagY > rectY + rectHeight - 10) {
                protag.getPosition().y = rectY + rectHeight;
            }

            //collision below object
            else if (protagX + protagSize > rectX && protagX < rectX + rectWidth && protagY + protagSize > rectY && protagY + protagSize < rectY + 10) {
                protag.getPosition().y = rectY - protagSize;
            }

            //wall collisions
            if (protagX < 0) protag.getPosition().x = 0;
            if (protagX + protagSize > width) protag.getPosition().x = width - protagSize;

            if (protagY < 0) protag.getPosition().y = 0;
            if (protagY + protagSize > height) protag.getPosition().y = height - protagSize;

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

//	private void drawDebug() {
//        // render blocks
//        debugRenderer.setProjectionMatrix(cam.combined);
//        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
////        for (Table table : cafeMac.getTables()) {
//        for (Rectangle rect: collisionRects) {
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