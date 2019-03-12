package com.rixonsoft.brucielib.input;

public interface PadControllable {

    public void reset();
    public void padUp(boolean down);
    public void padDown(boolean down);
    public void padLeft(boolean down);
    public void padRight(boolean down);
    public void buttonA(boolean down);
    public void buttonB(boolean down);
    public void buttonC(boolean down);
    public void buttonEsc(boolean down);

}
