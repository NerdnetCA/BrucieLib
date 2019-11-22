package com.rixonsoft.brucielib.tufree.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class DefaultPad implements InputProcessor {

    private PadControllable controllable;

    public DefaultPad setControllable(PadControllable controllable) {
        this.controllable = controllable;
        return this;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.DPAD_UP:
                controllable.padUp(true);
                return true;
            case Keys.DPAD_DOWN:
                controllable.padDown(true);
                return true;
            case Keys.DPAD_LEFT:
                controllable.padLeft(true);
                return true;
            case Keys.DPAD_RIGHT:
                controllable.padRight(true);
                return true;
            case Keys.Z:
                controllable.buttonA(true);
                return true;
            case Keys.X:
                controllable.buttonB(true);
                return true;
            case Keys.C:
                controllable.buttonC(true);
                return true;
            case Keys.ESCAPE:
                controllable.buttonStart(true);
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Keys.DPAD_UP:
                controllable.padUp(false);
                return true;
            case Keys.DPAD_DOWN:
                controllable.padDown(false);
                return true;
            case Keys.DPAD_LEFT:
                controllable.padLeft(false);
                return true;
            case Keys.DPAD_RIGHT:
                controllable.padRight(false);
                return true;
            case Keys.Z:
                controllable.buttonA(false);
                return true;
            case Keys.X:
                controllable.buttonB(false);
                return true;
            case Keys.C:
                controllable.buttonC(false);
                return true;
            case Keys.ESCAPE:
                controllable.buttonStart(false);
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
