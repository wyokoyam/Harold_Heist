package com.Harold_Heist.Model;

import com.badlogic.gdx.math.Vector2;


public class CafeMac {

    public enum State {
        STATE_RUNNING, STATE_GAMEOVER;
    }

	/** Our player controlled hero **/
    Protagonist protag;
	/** Our heroes autonomous nemesis **/
	Antagonist antag;

    State state = State.STATE_RUNNING;

	// Getters -----------

	public Protagonist getProtagonist() {
		return protag;
	}

	public Antagonist getAntagonist(){
		return antag;
	}

    public State getState() {return state;}
	// --------------------

    // Setters -----------

    public void setState(State state) {this.state = state;}

    // --------------------
	public CafeMac() {
		createCafeMac();
	}

	private void createCafeMac() {
		protag = new Protagonist(new Vector2(40, 40));
		antag = new Antagonist(new Vector2(1, 1));
	}
}
