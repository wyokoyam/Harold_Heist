package com.Harold_Heist.Model;



import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by Reena on 4/13/15.
 */
public class Fruit  {

    static final float SIZE = 32;

    Vector2 position = new Vector2();
    Rectangle bounds = new Rectangle();

    public Fruit(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public static float getSize() {
        return SIZE;
    }
}
