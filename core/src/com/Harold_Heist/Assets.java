package com.Harold_Heist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
//	public static Texture protagTexture;
//	public static Texture antagTexture;
//	public static Texture tableTexture;
	public static Music catchyMusic;
	
	public static void load() {
//		tableTexture = new Texture(Gdx.files.internal("graphics/hh table.png"));
//		protagTexture = new Texture(Gdx.files.internal("graphics/protagonist2.png"));
//		antagTexture =  new Texture(Gdx.files.internal("graphics/antagonist2.png"));
		catchyMusic = Gdx.audio.newMusic(Gdx.files.internal("music/catchyMusic.mp3"));
		catchyMusic.setLooping(true);
		catchyMusic.play();
	}
}
