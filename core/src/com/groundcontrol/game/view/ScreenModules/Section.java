package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface Section {
    void update(float delta);
    void display(float delta);
    void transition();
    void draw();
    Stage createStage();
}
