package com.Harold_Heist.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by victorbordo on 5/5/15.
 */
public class EvilTwin extends Antagonist {

    Vector2 	position = new Vector2();
    Rectangle bounds = new Rectangle();
    State state = State.FACERIGHT;

    public EvilTwin(Vector2 position){
        super(position);
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

}
