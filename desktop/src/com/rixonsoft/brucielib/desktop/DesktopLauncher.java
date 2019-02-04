package com.rixonsoft.brucielib.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rixonsoft.brucielib.BrucieConfig;
import com.rixonsoft.brucielib.test.TestGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = BrucieConfig.desktopWidth;
		config.height = BrucieConfig.desktopHeight;

		new LwjglApplication(new TestGame(), config);
	}
}
