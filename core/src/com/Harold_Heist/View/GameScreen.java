package com.Harold_Heist.View;

import com.Harold_Heist.*;
import com.Harold_Heist.Model.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameScreen implements Screen{
	
	private final HaroldHeist game;
	private CafeMac cafeMac;
	private CafeMacRenderer renderer;
	private OrthographicCamera cam;
	private Protagonist protag;
	private Antagonist antag;

	public GameScreen(HaroldHeist game) {
        this.game = game;
        cafeMac = new CafeMac();
        protag = cafeMac.getProtagonist();
        antag = cafeMac.getAntagonist();
        renderer = new CafeMacRenderer(cafeMac, false);
        cam = new OrthographicCamera();

    }

	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		keyboardControls();
        automateAntag(antag);
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

    private void automateAntag(Antagonist antag) {

        if (Gdx.input.isKeyPressed(Keys.UP)){
            moveAntagUp(antag);
        }

        if(Gdx.input.isKeyPressed(Keys.DOWN)){
            moveAntagDown(antag);
        }

    }

    private void moveAntagUp(Antagonist ant) {
        ant.setState(Antagonist.State.FACEUP);
        ant.getPosition().y += ant.getSpeed() * Gdx.graphics.getDeltaTime();

    }

    private void moveAntagDown(Antagonist ant) {
        ant.setState(Antagonist.State.FACEDOWN);
        ant.getPosition().y -= ant.getSpeed() * Gdx.graphics.getDeltaTime();

    }

    private void moveAntagRight(Antagonist ant) {
        ant.setState(Antagonist.State.FACERIGHT);
        ant.getPosition().x += ant.getSpeed() * Gdx.graphics.getDeltaTime();

    }

    private void moveAntagLeft(Antagonist ant) {
        ant.setState(Antagonist.State.FACELEFT);
        ant.getPosition().x -= ant.getSpeed() * Gdx.graphics.getDeltaTime();

    }

    private static ArrayList<Vector2> getCoords(Antagonist antag){

        ArrayList<Vector2> coorList = new ArrayList<Vector2>();

        for(int i = 0; i < 100; i++){
            Vector2 pos = antag.getPosition();
            coorList.add(pos);
        }

        return coorList;
    }


    @Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		renderer.setSize(width, height);
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
