package com.Harold_Heist.Model;

import com.badlogic.gdx.math.Vector2;


public class CafeMac {
	/** Our player controlled hero **/
	Protagonist protag;
	/** Our heroes autonomous nemesis **/
	Antagonist antag;


	// Getters -----------

	public Protagonist getProtagonist() {
		return protag;
	}

	public Antagonist getAntagonist(){
		return antag;
	}
	// --------------------

	public CafeMac() {
		createCafeMac();
	}
//
//
	private void createCafeMac() {
		protag = new Protagonist(new Vector2(40, 40));
		antag = new Antagonist(new Vector2(1, 1));
	}
}
