package com.rixonsoft.brucielib.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rixonsoft.brucielib.BrucieConfig;
import com.rixonsoft.brucielib.test.GameSelector;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Get width and height of the application window from
		// static definition in BrucieConfig
		config.width = BrucieConfig.desktopWidth;
		config.height = BrucieConfig.desktopHeight;

		// Accept an integer commandline parameter.
		int dnum = 0;
		if(arg.length > 0) {
			try {
				dnum = Integer.parseInt(arg[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// Create game instance and give it the parameter.
		GameSelector t = new GameSelector();
		t.setAppNum(dnum);

		// GO!
		new LwjglApplication(t, config);
	}
}
