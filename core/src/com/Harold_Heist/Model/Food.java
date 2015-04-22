package com.Harold_Heist.Model;



import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by Reena on 4/13/15.
 */
public class Food {

    static final float SIZE = 32;

    Vector2 position = new Vector2();
    Rectangle bounds = new Rectangle();
    int foodIndex; // 0 = banana, 1 = apple, 2 = bacon, 3 = cake

    public Food(Vector2 position, int foodIndex) {
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
        this.foodIndex = foodIndex;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getFoodIndex() {return foodIndex;}

    public static float getSize() {
        return SIZE;
    }
}
