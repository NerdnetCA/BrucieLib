package com.rixonsoft.brucielib.test;

import com.rixonsoft.brucielib.BrucieGame;

public class TestGame extends BrucieGame {

	@Override
	public void create () {
		super.create();
		bootScene(new TestScene());
	}

}
