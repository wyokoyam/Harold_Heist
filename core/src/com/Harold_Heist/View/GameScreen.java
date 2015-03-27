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
	private Texture antagIMG; // antag image
	private OrthographicCamera camera;
	private Table table;
	private Protagonist protag;
	private Antagonist antag; 
	
	public GameScreen(HaroldHeist game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		keyboardControls();
		renderer.render();
		
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
		antag = cafeMac.getAntagonist();
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
		antagIMG.dispose();
	}
	
	private void keyboardControls() {
		if(Gdx.input.isKeyPressed(Keys.LEFT)) protag.getPosition().x -= 2 * Gdx.graphics.getDeltaTime();
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) protag.getPosition().x += 2 * Gdx.graphics.getDeltaTime();
	    if(Gdx.input.isKeyPressed(Keys.DOWN)) protag.getPosition().y-= 2 * Gdx.graphics.getDeltaTime();
	    if(Gdx.input.isKeyPressed(Keys.UP)) protag.getPosition().y += 2 * Gdx.graphics.getDeltaTime();
	    
	    for (Table table: cafeMac.getTables()) {
	    	float protagX = protag.getPosition().x;
	    	float protagY = protag.getPosition().y;
	    	float antagX = antag.getPosition().x;
	    	float antagY = antag.getPosition().x;
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
	    	
	    	// handle protag antag collision L
	    	if (protagX + 0.5f > antagX && protagX + 0.5f < antagX + 0.1f && protagY >= antagY - 0.5f && protagY <= antagY + 1) {
//	    		protag.getPosition().x = antagX - 0.5f;
	        	game.setScreen(new GameOverScreen(game));
	    	}
	    	
	    	// handle protag antag collision R
	    	else if (protagX < antagX + 0.5f && protagX > antagX + 0.1f && protagY >= antagY - 0.5f && protagY <= antagY + 1) {
//	    		protag.getPosition().x = antagX + 1;
	        	game.setScreen(new GameOverScreen(game));
	    	}
	    	
	    	// handle protag antag collision B
	    	else if (protagX + 0.5f > antagX + 0.1 && protagX < antagX + 1 && protagY + 0.5f > antagY && protagY + 0.5f < antagY + 0.1) {
//	    		protag.getPosition().y = antagY - 0.5f;
	        	game.setScreen(new GameOverScreen(game));
	    	}
	    	
	    	// handle protag antag collision A
	    	else if (protagX + 0.5f > antagX + 0.1 && protagX < antagX + 1 && protagY < antagY + 0.5f && protagY > antagY + 0.1f) {
//	    		protag.getPosition().y = antagY + 0.5f;
	        	game.setScreen(new GameOverScreen(game));
	    	}
	    }
	    
	     //protag stays within the screen bounds
	    
	    if(protag.getPosition().x < 0f) protag.getPosition().x = 0f;
	    if(protag.getPosition().x > 9.5f) protag.getPosition().x = 9.5f;
	    if(protag.getPosition().y < 0f) protag.getPosition().y = 0f;
	    if(protag.getPosition().y > 6.5f) protag.getPosition().y = 6.5f;
	}

}
