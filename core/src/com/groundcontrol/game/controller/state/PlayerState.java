package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.physics.box2d.Body;
import com.groundcontrol.game.controller.elements.PlayerController;

import java.util.ArrayList;

public interface PlayerState {

    void handleInput(PlayerController context, InputDecoder.Input input);

    void setRotation(PlayerController context, ArrayList<Body> objects);

    void applyPullForce(PlayerController context, ArrayList<Body> objects);

}
