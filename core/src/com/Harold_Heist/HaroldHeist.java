package com.Harold_Heist;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.Harold_Heist.View.GameScreen;
import com.Harold_Heist.View.MainMenuScreen;

public class HaroldHeist extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
//	Texture protagTexture;
//	Sprite protagSprite;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		
//		testing collision detection
//		protagTexture = new Texture(Gdx.files.internal("marioImage.png"));
//		protagSprite = new Sprite(protagTexture);
//		World world = new World(new Vector2(0, -1), true);
//		BodyDef bodyDef = new BodyDef();
//		bodyDef.type = BodyDef.BodyType.DynamicBody;
//		protagSprite.setPosition(xCoordinate, yCoordinate);
//		bodyDef.position.set(protagSprite.getX(), protagSprite.getY());
//		Body body = world.createBody(bodyDef);
//		PolygonShape shape = new PolygonShape();
//		shape.setAsBox(protagSprite.getWidth()/2, protagSprite.getHeight()/2);
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = shape;
//        fixtureDef.density = 1f;
//        Fixture fixture = body.createFixture(fixtureDef);
//        shape.dispose();
		
		this.setScreen(new MainMenuScreen(this));
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
