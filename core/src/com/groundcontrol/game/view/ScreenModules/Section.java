package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface Section {
    void update(float delta);
    void display(float delta);
    void transition();
    Stage createStage();
    void loadAssets();
    void drawStages(float delta);
    void drawBackground();
}
