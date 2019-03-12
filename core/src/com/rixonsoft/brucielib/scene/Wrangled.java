package com.rixonsoft.brucielib.scene;

import com.rixonsoft.brucielib.BrucieGame;

/** Wrangled instances need to be initialized with a handle to the game.
 *
 */
public interface Wrangled {
    void setGame(BrucieGame game);
}
