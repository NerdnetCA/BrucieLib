package com.rixonsoft.brucielib.ecs.component;

import com.badlogic.ashley.core.Component;

public class InputComponent implements Component {
    public int axisY;
    public int axisX;
    public boolean buttonA, buttonB, buttonC, buttonStart;
}