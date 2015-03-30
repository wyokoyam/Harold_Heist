package com.Harold_Heist.View;

import com.Harold_Heist.Assets;
import com.Harold_Heist.Model.Table;
import com.Harold_Heist.Model.Protagonist;
import com.Harold_Heist.Model.Antagonist;
import com.Harold_Heist.Model.CafeMac;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class CafeMacRenderer {
	
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private CafeMac cafeMac;
	private OrthographicCamera cam;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	private Music catchyMusic;
	
	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)560 / CAMERA_WIDTH;
		ppuY = (float)320 / CAMERA_HEIGHT;
	}
	
	public CafeMacRenderer(CafeMac cafeMac, boolean debug) {
		this.cafeMac = cafeMac;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		
//		this.protagTexture = Assets.protagRight;
//		this.antagTexture = Assets.antagRight;
//		this.tableTexture = Assets.tableTexture;
		
	}


	public void render() {
		spriteBatch.enableBlending();
		spriteBatch.begin();
		drawTables();
		drawProtag();
		drawAntag();
		spriteBatch.end();
//		if (debug) drawDebug();
	}
	
	private void drawTables() {
		for (Table table: cafeMac.getTables()) {
			spriteBatch.draw(Assets.tableTexture, table.getPosition().x * ppuX, table.getPosition().y * ppuY, Table.getSize() * ppuX, Table.getSize() * ppuY);
		}
	}
	
	private void drawProtag() {
		Protagonist protag = cafeMac.getProtagonist();
		float xCoordinate = protag.getPosition().x * ppuX;
		float yCoordinate = protag.getPosition().y * ppuY;
		if (protag.getState() == Protagonist.State.FACERIGHT) {
			spriteBatch.draw(Assets.protagRight, xCoordinate, yCoordinate, Protagonist.getSize() * ppuX, Protagonist.getSize() * ppuY);
		}
		else if (protag.getState() == Protagonist.State.FACELEFT) {
			spriteBatch.draw(Assets.protagLeft, xCoordinate, yCoordinate, Protagonist.getSize() * ppuX, Protagonist.getSize() * ppuY);
		}
		else if (protag.getState() == Protagonist.State.FACEUP) {
			spriteBatch.draw(Assets.protagUp, xCoordinate, yCoordinate, Protagonist.getSize() * ppuX, Protagonist.getSize() * ppuY);
		} 
		else if (protag.getState() == Protagonist.State.FACEDOWN) {
			spriteBatch.draw(Assets.protagDown, xCoordinate, yCoordinate, Protagonist.getSize() * ppuX, Protagonist.getSize() * ppuY);
		}
	}
	
	private void drawAntag(){
		Antagonist antag = cafeMac.getAntagonist();
		float xCoordinate = antag.getPosition().x * ppuX;
		float yCoordinate = antag.getPosition().y * ppuY;
		if (antag.getState() == Antagonist.State.FACERIGHT) {
			spriteBatch.draw(Assets.antagRight, xCoordinate, yCoordinate, Protagonist.getSize() * ppuX, Protagonist.getSize() * ppuY);
		}
		else if (antag.getState() == Antagonist.State.FACELEFT) {
			spriteBatch.draw(Assets.antagLeft, xCoordinate, yCoordinate, Antagonist.getSize() * ppuX, Antagonist.getSize() * ppuY);
		}
		else if (antag.getState() == Antagonist.State.FACEUP) {
			spriteBatch.draw(Assets.antagUp, xCoordinate, yCoordinate, Antagonist.getSize() * ppuX, Antagonist.getSize() * ppuY);
		} 
		else if (antag.getState() == Antagonist.State.FACEDOWN) {
			spriteBatch.draw(Assets.antagDown, xCoordinate, yCoordinate, Antagonist.getSize() * ppuX, Antagonist.getSize() * ppuY);
		}
	}
	
//	private void drawDebug() {
//		// render blocks
//		debugRenderer.setProjectionMatrix(cam.combined);
//		debugRenderer.begin(ShapeType.Line);
//		for (Table table : cafeMac.getTables()) {
//			Rectangle rect = table.getBounds();
//			float x1 = table.getPosition().x + rect.x;
//			float y1 = table.getPosition().y + rect.y;
//			debugRenderer.setColor(new Color(1, 0, 0, 1));
//			debugRenderer.rect(x1, y1, rect.width, rect.height);
//		}
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