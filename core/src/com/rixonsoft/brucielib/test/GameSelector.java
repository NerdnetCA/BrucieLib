package com.rixonsoft.brucielib.test;

import com.rixonsoft.brucielib.BrucieGame;
import com.rixonsoft.brucielib.scene.MinimalScene;
import com.rixonsoft.brucielib.scene.Scene;

public class GameSelector extends BrucieGame {


	private int appNum =0;

	public void setAppNum(int appnum) {
		this.appNum = appnum;
	}

	@Override
	public void create () {
		super.create();

		Scene boot;
		switch(appNum) {
			case 0:
				boot = new TestScene();
				break;
			case 1:
				boot = new MinimalScene();
				break;
/*
			case 2:
				boot = new TestShooter();
				break;
			case 3:
				boot = new PlatScene();
				break;
			case 4:
				boot = new BFlatGameScene();
				break;
*/
			default:
				boot = new TestScene();
				break;
		}
		bootScene(boot);
	}

}
