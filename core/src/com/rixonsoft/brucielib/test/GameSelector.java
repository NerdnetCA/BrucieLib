package com.rixonsoft.brucielib.test;

import com.rixonsoft.brucielib.BrucieGame;
import com.rixonsoft.brucielib.scene.MinimalScene;
import com.rixonsoft.brucielib.scene.Scene;

/** GameSelector is an example wrapper for the starting scene of the game.
 *
 * A Scene is instantiated based on command-line parameters, and passed to
 * the bootScene() method of
 */
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
			case 0:
				boot = new TestScene();
				break;
			case 1:
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
			case 5:
				//boot = new DGenScene();
				break;

			default:
				boot = new TestScene();
				break;
		}
		bootScene(boot);
	}

}
