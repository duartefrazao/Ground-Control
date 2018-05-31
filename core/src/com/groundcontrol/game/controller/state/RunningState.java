package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.groundcontrol.game.controller.elements.PlayerController;

/**
 * Represents the state in which the player is running around a planet
 */
public class RunningState implements PlayerState {

    private static int clockWise = 1;
    private static int counterClockWise = -1;
    private float timeInPlanet;

    /**
     * Class Constructor given the time
     *
     * @param time
     */
    public RunningState(float time) {
        this.timeInPlanet = time;
    }

    @Override
    public float getTime() {
        return this.timeInPlanet;
    }

    @Override
    public void updateTime(PlayerController context, float delta) {

        this.timeInPlanet -= delta;

        if (this.timeInPlanet < 0) {

            context.setLost(true);

        }

    }

    @Override
    public void handleInput(PlayerController context, InputDecoder.Input input) {

        if (input == InputDecoder.Input.IDLE) {

            context.setState(new IdleState(this.timeInPlanet));

        } else if (input == InputDecoder.Input.JUMP) {

            context.jump();

        } else {

            context.walk(input == InputDecoder.Input.RIGHT ? clockWise : counterClockWise);

            context.setRightSide(input == InputDecoder.Input.RIGHT);

        }
    }

    @Override
    public void setRotation(PlayerController context, Array<Body> objects) {
        context.setTransform(context.getX(), context.getY(), context.getAngleBetween(context.getPlanet()));
    }

    @Override
    public void applyPullForce(PlayerController context, Array<Body> objects) {

        Vector2 force = context.calculatePullForce(context.getPlanet());
        context.applyForceToCenter(force, true);

    }
}
