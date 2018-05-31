package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.groundcontrol.game.controller.elements.PlayerController;

/**
 * Player State Machine Interface, using state pattern.
 */
public interface PlayerState {

    /**
     * Handles the input sent by the view, given it's current state
     * @param context the player controller
     * @param input the input
     */
    void handleInput(PlayerController context, InputDecoder.Input input);

    /**
     * Changes the player rotation accordingly to it's current state and all the surrounding objects
     * @param context the player controller
     * @param objects surrounding objects
     */
    void setRotation(PlayerController context, Array<Body> objects);

    /**
     * Applies the pull force made by planets to the player. Our simulation of gravity.
     * @param context the player controller
     * @param objects surrounding objects
     */
    void applyPullForce(PlayerController context, Array<Body> objects);

    /**
     * Updates the time the player has been in a certain state
     * @param context the player controller
     * @param delta surrounding objects
     */
    void updateTime(PlayerController context, float delta);

    /**
     * Returns the current state time
     * @return time
     */
    float getTime();

}