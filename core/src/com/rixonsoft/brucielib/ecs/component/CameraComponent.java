package com.rixonsoft.brucielib.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent implements Component {
    public OrthographicCamera camera = new OrthographicCamera();
}
