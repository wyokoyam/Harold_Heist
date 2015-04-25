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

import java.util.ArrayList;


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
        moveAntag();
    }

    private void moveAntag() {
        double tanInRadians = Math.toRadians(antag.getPosition().y - protag.getPosition().y) / Math.toRadians(antag.getPosition().x - protag.getPosition().x);
        double degree = Math.toDegrees(Math.atan(tanInRadians));
        double unitCircleX = Math.cos(Math.toRadians(degree));
        double unitCircleY = Math.sin(Math.toRadians(degree));

        double northDotVector = 1 * unitCircleY;
        double northEastDotVector = (Math.sqrt(2)/2 * unitCircleX) + (Math.sqrt(2)/2 * unitCircleY);
        double eastDotVector = 1 * unitCircleX;
        double southEastDotVector = (Math.sqrt(2)/2 * unitCircleX) + (-Math.sqrt(2)/2 * unitCircleY);
        double southDotVector = -1 * unitCircleY;
        double southWestDotVector = (-Math.sqrt(2)/2 * unitCircleX) + (-Math.sqrt(2)/2 * unitCircleY);
        double westDotVector = -1 * unitCircleX;
        double northWestDotVector = (-Math.sqrt(2)/2 * unitCircleX) + (Math.sqrt(2)/2 * unitCircleY);

        ArrayList<Double> directionVectors = new ArrayList<Double>();
        directionVectors.add(southDotVector);
        directionVectors.add(southWestDotVector);
        directionVectors.add(westDotVector);
        directionVectors.add(northWestDotVector);
        directionVectors.add(northDotVector);
        directionVectors.add(northEastDotVector);
        directionVectors.add(eastDotVector);
        directionVectors.add(southEastDotVector);

        double max = Integer.MIN_VALUE;
        for (Double directionVector : directionVectors) {
            if (directionVector > max) max = directionVector;
        }

        if (max == northDotVector) {
            antag.setState(Antagonist.State.FACEUP);
            antag.getPosition().y += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        } else if (max == northEastDotVector) {
            antag.setState(Antagonist.State.FACERIGHT);
            antag.getPosition().x += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
            antag.getPosition().y += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        } else if (max == eastDotVector) {
            antag.setState(Antagonist.State.FACERIGHT);
            antag.getPosition().x += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        } else if (max == southEastDotVector) {
            antag.setState(Antagonist.State.FACERIGHT);
            antag.getPosition().x += Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
            antag.getPosition().y -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        } else if (max == southDotVector) {
            antag.setState(Antagonist.State.FACEDOWN);
            antag.getPosition().y -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        } else if (max == southWestDotVector) {
            antag.setState(Antagonist.State.FACELEFT);
            antag.getPosition().x -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
            antag.getPosition().y -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        } else if (max == westDotVector) {
            antag.setState(Antagonist.State.FACELEFT);
            antag.getPosition().x -= Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
//        }
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
