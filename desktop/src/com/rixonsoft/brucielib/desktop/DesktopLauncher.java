package com.rixonsoft.brucielib.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rixonsoft.brucielib.test.TestGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 800;
		config.height = 600;

		new LwjglApplication(new TestGame(), config);
	}
}
