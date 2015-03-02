package com.Harold_Heist.Controller;

import java.util.HashMap;
import java.util.Map;

import com.Harold_Heist.Model.Protagonist;
import com.Harold_Heist.Model.Protagonist.State;
import com.Harold_Heist.Model.CafeMac;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;


// Inactive right now
public class CharacterController {
//	enum Keys {
//		LEFT, RIGHT, UP, DOWN
//	}
	
	private CafeMac cafeMac;
	private Protagonist protag;
	
//	static Map<Keys, Boolean> keys = new HashMap<CharacterController.Keys, Boolean>();
//	static {
//		keys.put(Keys.LEFT, false);
//		keys.put(Keys.RIGHT, false);
//		keys.put(Keys.UP, false);
//		keys.put(Keys.DOWN, false);
//	};
//	
//	public CharacterController(CafeMac cafeMac) {
//		this.cafeMac = cafeMac;
//		this.protag = cafeMac.getProtagonist();
//	}
//	
//	// Key presses and touches
//	
//	public void leftPressed() {
//		keys.get(keys.put(Keys.LEFT, true));
//	}
//	
//	public void rightPressed() {
//		keys.get(keys.put(Keys.RIGHT, true));
//	}
//	
//	public void upPressed() {
//		keys.get(keys.put(Keys.UP, true));
//	}
//	
//	public void downPressed() {
//		keys.get(keys.put(Keys.DOWN, true));
//	}
//	
//	public void leftReleased() {
//		keys.get(keys.put(Keys.LEFT, false));
//	}
//	
//	public void rightReleased() {
//		keys.get(keys.put(Keys.RIGHT, false));
//	}
//	
//	public void upReleased() {
//		keys.get(keys.put(Keys.UP, false));
//	}
//	
//	public void downReleased() {
//		keys.get(keys.put(Keys.DOWN, false));
//	}
	
	public void update(float delta) {
		if(Gdx.input.isKeyPressed(Keys.LEFT)) protag.getPosition().x -= 200 * Gdx.graphics.getDeltaTime();
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) protag.getPosition().x += 200 * Gdx.graphics.getDeltaTime();
	    if(Gdx.input.isKeyPressed(Keys.DOWN)) protag.getPosition().y-= 200 * Gdx.graphics.getDeltaTime();
	    if(Gdx.input.isKeyPressed(Keys.UP)) protag.getPosition().y += 200 * Gdx.graphics.getDeltaTime();

	}
	// main update method
//	public void update(float delta) {
//		processInput();
//		protag.update(delta);
//	}
	
	// Change protagonist's state and parameters based on input controls
//	private void processInput() {
//		if (keys.get(Keys.LEFT)) {
//			// left is pressed
//			protag.setFacingLeft(true);
//			protag.setState(State.WALKING);
//			protag.getVelocity().x = -Protagonist.getSpeed();
//		}
//		if (keys.get(Keys.RIGHT)) {
//			// left is pressed
//			protag.setFacingLeft(false);
//			protag.setState(State.WALKING);
//			protag.getVelocity().x = Protagonist.getSpeed();
//		}
//	}
}
