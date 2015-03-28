package com.Harold_Heist.Model;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class CafeMac {
	/** The tableArray making up the world **/
	Array<Table> tableArray = new Array<Table>();
	/** Our player controlled hero **/
	Protagonist protag;
	/** Our heroes autonomous nemesis **/
	Antagonist antag;

	// Getters -----------
	public Array<Table> getTables() {
		return tableArray;
	}
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

	private void createCafeMac() {
		protag = new Protagonist(new Vector2(7, 2));
		antag = new Antagonist(new Vector2(1, 1));
		Random rand = new Random();

		for (int i = 0; i < 20; i++) { 			 			
			tableArray.add(new Table(new Vector2(rand.nextInt(10), rand.nextInt(8))));
		}
	}
	
}
