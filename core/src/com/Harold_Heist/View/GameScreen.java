package com.Harold_Heist.View;

import com.Harold_Heist.*;
import com.Harold_Heist.Model.*;
import com.Harold_Heist.Controller.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
//import com.badlogic.gdx.graphics.g2d.Sprite.batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen{
	
	private final HaroldHeist game;
	private CafeMac cafeMac;
	private CafeMacRenderer renderer;
	private CharacterController controller;
	private Texture tableIMG; // table image
	private Texture protagIMG; // protag image
	private OrthographicCamera camera;
	private Table table;
	private Protagonist protag;
	
	public GameScreen(HaroldHeist game) {
		this.game = game;
	}
	
//	public GameScreen(final Main game) {
//		this.game = game;
//		tableIMG = new Texture(Gdx.files.internal("tableImage.png"));
//		protagIMG = new Texture(Gdx.files.internal("marioImage.png"));
//		
//		// create camera
//		camera = new OrthographicCamera();
//		camera.setToOrtho(false, 800, 480);
//		
//		// create rectangles hard code, design Table Class
////		table = new Rectangle();
////		table.x = 300;
////		table.y = 40;
////		table.width = 0;
////		table.height = 0;
//		
//		// create protagonist hard code, design Protagonist Class
//		protag = new Rectangle();
//		protag.x = 400;
//		protag.y = 240;
//		protag.width = 4;
//		protag.height = 8;
//	}
	
//	@Override
//	public void create() {
//
//
//	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		keyboardControls();
		renderer.render();
		
		// update camera matrices
//		camera.update();
		
//		game.batch.setProjectionMatrix(camera.combined);
		
//		ShapeRenderer debugRenderer = new ShapeRenderer();
//		debugRenderer.setProjectionMatrix(camera.combined);
//		debugRenderer.begin(ShapeType.Line);
		
		// hard-code tables until design Table Class, table.x, table.y, simple for loop
//		game.batch.begin();
//		game.batch.draw(new Table(), 100, 300); 
//		game.batch.draw(tableIMG, 200, 100);
//		game.batch.draw(tableIMG, 70,  150);
//		game.batch.draw(tableIMG, 600, 350);
//		game.batch.end();
		
		
		//render Protagonist
//		game.batch.begin();
//		game.batch.draw(protagIMG, protag.x, protag.y);
//		game.batch.end();
		
		//
//		if(Gdx.input.isKeyPressed(Keys.LEFT)) protag.x -= 200 * Gdx.graphics.getDeltaTime();
//	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) protag.x += 200 * Gdx.graphics.getDeltaTime();
//	    if(Gdx.input.isKeyPressed(Keys.DOWN)) protag.y-= 200 * Gdx.graphics.getDeltaTime();
//	    if(Gdx.input.isKeyPressed(Keys.UP)) protag.y += 200 * Gdx.graphics.getDeltaTime();

	      // protag stays within the screen bounds
//	     if(protag.x < 0) protag.x = 0;
//	     if(protag.x > 800 - 64) protag.x = 800 - 64;
//	     if(protag.y < 0) protag.y = 0;
//	     if(protag.y > 480 - 64) protag.y = 480 - 64;
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		renderer.setSize(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		cafeMac = new CafeMac();
		protag = cafeMac.getProtagonist();
		renderer = new CafeMacRenderer(cafeMac, false);
	}
		
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		tableIMG.dispose();
		protagIMG.dispose();
	}
	
	private void keyboardControls() {
		if(Gdx.input.isKeyPressed(Keys.LEFT)) protag.getPosition().x -= 2 * Gdx.graphics.getDeltaTime();
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) protag.getPosition().x += 2 * Gdx.graphics.getDeltaTime();
	    if(Gdx.input.isKeyPressed(Keys.DOWN)) protag.getPosition().y-= 2 * Gdx.graphics.getDeltaTime();
	    if(Gdx.input.isKeyPressed(Keys.UP)) protag.getPosition().y += 2 * Gdx.graphics.getDeltaTime();
	    
	    for (Table table: cafeMac.getTables()) {
	    	float protagX = protag.getPosition().x;
	    	float protagY = protag.getPosition().y;
	    	float tableX = table.getPosition().x;
	    	float tableY = table.getPosition().y;
	    	
	    	// handle collision left of table
	    	if (protagX + 0.5f > tableX && protagX + 0.5f < tableX + 0.1f && protagY >= tableY - 0.5f && protagY <= tableY + 1) {
	    		protag.getPosition().x = tableX - 0.5f;
	    	}
	    	
	    	// handle collision right of table
	    	else if (protagX < tableX + 1 && protagX > tableX + 0.9f && protagY >= tableY - 0.5f && protagY <= tableY + 1) {
	    		protag.getPosition().x = tableX + 1;
	    	}
	    	
	    	//handle collision below table
	    	else if (protagX + 0.5f > tableX + 0.1 && protagX < tableX + 1 && protagY + 0.5f > tableY && protagY + 0.5f < tableY + 0.1) {
	    		protag.getPosition().y = tableY - 0.5f;
	    	}
	    	
	    	//handle collision above table
	    	else if (protagX + 0.5f > tableX + 0.1 && protagX < tableX + 1 && protagY < tableY + 1 && protagY > tableY + 0.9f) {
	    		protag.getPosition().y = tableY + 1;
	    	}
	    }
	    
	     //protag stays within the screen bounds
	    
	    if(protag.getPosition().x < 0f) protag.getPosition().x = 0f;
	    if(protag.getPosition().x > 9.5f) protag.getPosition().x = 9.5f;
	    if(protag.getPosition().y < 0f) protag.getPosition().y = 0f;
	    if(protag.getPosition().y > 6.5f) protag.getPosition().y = 6.5f;
	}

}
