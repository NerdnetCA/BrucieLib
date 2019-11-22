package com.rixonsoft.brucielib.core;

import com.rixonsoft.brucielib.core.scene.Scene;
import com.rixonsoft.brucielib.test.TestScene;

/** GameSelector is an example of creating a basic game.
 * This assumes a number was passed in from the launcher. In production, this
 * will be removed.
 *
 * The entrypoint should always extend from BrucieGame, and must call bootScene(scene) with
 * a newly-created Scene instance.
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
