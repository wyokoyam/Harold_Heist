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
import com.badlogic.gdx.math.Vector2;
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
		collisionHandler();
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
	    
	}
	
	private void collisionHandler() {
	    for (Table table: cafeMac.getTables()) {
	    	objectCollisionHandler(protag.getPosition(), table.getPosition(), false);
	    	objectCollisionHandler(antag.getPosition(), table.getPosition(), false);
	    	objectCollisionHandler(protag.getPosition(), antag.getPosition(), true);
	    }    
	    wallCollisionHandler(protag.getPosition());
	    wallCollisionHandler(antag.getPosition());
	}
	
	private void wallCollisionHandler(Vector2 object) {
		float objectX = object.x;
		float objectY = object.y;
		
	    if(objectX < 0f) object.x = 0f;
	    if(objectX > 9.5f) object.x = 9.5f;
	    if(objectY < 0f) object.y = 0f;
	    if(objectY > 6.5f) object.y = 6.5f;
	}
	
	private void objectCollisionHandler(Vector2 object1, Vector2 object2, boolean protagAndAntag) {
		float object1X = object1.x;
		float object1Y = object1.y;
		float object2X = object2.x;
		float object2Y = object2.y;
		
		// handle collision left of object2
    	if (object1X + 0.5f > object2X && object1X + 0.5f < object2X + 0.1f && object1Y > object2Y - 0.45f && object1Y < object2Y + 0.45f) {
    		if (protagAndAntag) game.setScreen(new GameOverScreen(game)); 
    		else protag.getPosition().x = object2X - 0.5f;
    	}
    	
    	// handle collision right of object2
    	else if (object1X < object2X + 0.5f && object1X > object2X + 0.4f && object1Y > object2Y - 0.45f && object1Y < object2Y + 0.45f) {
    		if (protagAndAntag) game.setScreen(new GameOverScreen(game));
    		else protag.getPosition().x = object2X + 0.5f;
    	}
    	
    	//handle collision below object2
    	else if (object1X + 0.45f > object2X && object1X < object2X + 0.45f && object1Y + 0.5f > object2Y && object1Y + 0.5f < object2Y + 0.1f) {
    		if (protagAndAntag) game.setScreen(new GameOverScreen(game));
    		else protag.getPosition().y = object2Y - 0.5f;
    	}
    	
    	//handle collision above object2
    	else if (object1X + 0.45f > object2X && object1X < object2X + 0.45f && object1Y < object2Y + 0.5f && object1Y > object2Y + 0.4f) {
    		if (protagAndAntag) game.setScreen(new GameOverScreen(game));
    		else protag.getPosition().y = object2Y + 0.5f;
    	}
	}
}
