package com.Harold_Heist.desktop;

//import com.Harold_Heist.View.GameScreen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.Harold_Heist.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Angry Albert";
		config.width = 560;
		config.height = 320;
		new LwjglApplication(new Main(), config);
	}
}
