package com.Harold_Heist.View;

import com.Harold_Heist.*;
import com.Harold_Heist.Model.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen{
	
	private final HaroldHeist game;
	private CafeMac cafeMac;
	private CafeMacRenderer renderer;
	private OrthographicCamera cam;
	private Protagonist protag;
	private Antagonist antag;
    private Viewport viewport;

	public GameScreen(HaroldHeist game) {
        this.game = game;
        cafeMac = new CafeMac();
        protag = cafeMac.getProtagonist();
        antag = cafeMac.getAntagonist();
        renderer = new CafeMacRenderer(cafeMac, false);
        cam = new OrthographicCamera();
        viewport = new ScreenViewport(cam);
    }


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		keyboardControls();
        update();
		renderer.render();
	}

    private void update() {
        if (cafeMac.getState() == CafeMac.State.STATE_GAMEOVER) {
            game.setScreen(new GameOverScreen(game));
        }
    }

    private void keyboardControls() {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            protag.setState(Protagonist.State.FACELEFT);
            protag.getPosition().x -= Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            protag.setState(Protagonist.State.FACERIGHT);
            protag.getPosition().x += Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            protag.setState(Protagonist.State.FACEDOWN);
            protag.getPosition().y -= Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Keys.UP)) {
            protag.setState(Protagonist.State.FACEUP);
            protag.getPosition().y += Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		renderer.setSize(width, height);
//        viewport.update(width, height);

    }

	@Override
	public void show() {
		// TODO Auto-generated method stub

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
	}

}
