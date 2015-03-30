package com.Harold_Heist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	public static Texture protagRight;
	public static Texture protagLeft;
	public static Texture protagUp;
	public static Texture protagDown;

	public static Texture antagRight;
	public static Texture antagLeft;
	public static Texture antagUp;
	public static Texture antagDown;
	
	public static Texture tableTexture;
	public static Texture soundOn;
	public static Texture soundOff;
	
	public static Music catchyMusic;
	
	public static void load() {
		tableTexture = new Texture(Gdx.files.internal("graphics/table.png"));
		
		protagRight = new Texture(Gdx.files.internal("graphics/protagRight.png"));
		protagLeft = new Texture(Gdx.files.internal("graphics/protagLeft.png"));
		protagUp = new Texture(Gdx.files.internal("graphics/protagUp.png"));
		protagDown = new Texture(Gdx.files.internal("graphics/protagDown.png"));
		
		antagRight =  new Texture(Gdx.files.internal("graphics/antagRight.png"));
		antagLeft = new Texture(Gdx.files.internal("graphics/antagLeft.png"));
		antagUp = new Texture(Gdx.files.internal("graphics/antagUp.png"));
		antagDown = new Texture(Gdx.files.internal("graphics/antagDown.png"));
		
		soundOn = new Texture(Gdx.files.internal("graphics/soundOn.png"));
		soundOff = new Texture(Gdx.files.internal("graphics/soundOff.png"));
		catchyMusic = Gdx.audio.newMusic(Gdx.files.internal("music/catchyMusic.mp3"));
		catchyMusic.setLooping(true);
		catchyMusic.play();
	}
}
