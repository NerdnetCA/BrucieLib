package com.rixonsoft.brucielib;

public abstract class AbstractScene implements Scene {

    protected BrucieGame myGame;
    protected Wrangler wrangler;
    protected boolean done;


    public void setDone(boolean done) {
        this.done = done;
    }


    @Override
    public void configure(BrucieGame game) {
        myGame = game;
        wrangler = new Wrangler(game);
    }

    /** Scenes are generally allowed to run until isDone returns false.
     * @return
     */
    public boolean isDone() {
        return done;
    }


    /** hide will call dispose. See dispose. If you override this, make sure to call super.
     *
     */
    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        wrangler.dispose();
    }
}
