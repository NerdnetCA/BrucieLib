package com.rixonsoft.brucielib.test;

import com.rixonsoft.brucielib.BrucieGame;
import com.rixonsoft.brucielib.retron.TestCase;
import com.rixonsoft.brucielib.scene.WipeTest;

public class TestGame extends BrucieGame {

	@Override
	public void create () {
		super.create();
		bootScene(new TestCase());
	}

}
