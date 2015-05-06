package com.Harold_Heist.View;

import com.Harold_Heist.HaroldHeist;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameOverScreen implements Screen {
	
	final HaroldHeist game;
	OrthographicCamera camera;
    private Texture backgroundTexture;


	
	public GameOverScreen(HaroldHeist game) {
		this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("graphics/endScreen.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 560, 320);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //game.font.draw(game.batch, "Game Over!", 250, 200);
        //game.font.draw(game.batch, "Click anywhere to get back to the main menu", 150, 150);
        game.batch.end();
        
        if (Gdx.input.isTouched()) {
        	game.setScreen(new MainMenuScreen(game));
        	dispose();
        }
	}

	@Override
	public void resize(int width, int height) {
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
