package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.groundcontrol.game.controller.elements.PlayerController;

public interface PlayerState {

    void handleInput(PlayerController context, InputDecoder.Input input);

    void setRotation(PlayerController context, Array<Body> objects);

    void applyPullForce(PlayerController context, Array<Body> objects);

    void updateTime(PlayerController context, float delta);

    float getTime();

}