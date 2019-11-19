package com.rixonsoft.brucielib.core.boot;

import com.rixonsoft.brucielib.BrucieGame;

import com.rixonsoft.brucielib.templates.MinimalScene;
import com.rixonsoft.brucielib.core.scene.Scene;
import com.rixonsoft.brucielib.test.TestScene;

public class GameSelector extends BrucieGame {


	private int appNum =0;

	public void setAppNum(int appnum) {
		this.appNum = appnum;
	}

	@Override
	public void create () {
		super.create();

		Scene boot = new TestScene();
		switch(appNum) {
			case 1:
				// Minimal
				boot = new MinimalScene();
				break;
			case 2:
				//boot = new TestShooter();
				break;
			case 3:
				//boot = new PlatScene();
				break;
			case 4:
				//boot = new BFlatGameScene();
				break;

			case 0:
				// Default Test Scene
			default:
				boot = new TestScene();
				break;
		}
		bootScene(boot);
	}

}
