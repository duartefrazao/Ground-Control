package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.groundcontrol.game.controller.elements.PlayerController;

import static java.lang.Math.abs;

/**
 * Represents the state in which the player is floating through the space
 */
public class FloatState implements PlayerState {

    private final static float MAX_PLANET_DISTANCE = 8;

    private final static float INITIAL_TIME = 30f;

    private final static float OVERFLOW_CHECK = 0.01f;

    private static float floatTime = INITIAL_TIME;

    @Override
    public float getTime(){
        return this.floatTime;
    }

    /**
     * Default constructor. Initializes the state time to a default value.
     */
    public FloatState(){
        this.floatTime = INITIAL_TIME;
    }

    /**
     * Class constructor given the time
     * @param time
     */
    public FloatState(float time){
        floatTime = time;
    }

    @Override
    public void updateTime(PlayerController context, float delta){

    }

    @Override
    public void handleInput(PlayerController context, InputDecoder.Input input) {

        if (input == InputDecoder.Input.PLANET_LAND) {
            context.setState(new IdleState(this.floatTime));
        }

    }

    @Override
    public void setRotation(PlayerController context, Array<Body> objects) {

        for (Body e : objects) {

            float distance = abs(e.getPosition().x - context.getX());
            distance += abs(e.getPosition().y - context.getY());

            if (distance < MAX_PLANET_DISTANCE && distance > OVERFLOW_CHECK) {

                context.setTransform(context.getX(), context.getY(), context.getAngleBetween(e));

            }

        }

    }

    @Override
    public void applyPullForce(PlayerController context, Array<Body> objects) {

        for (Body e : objects) {
            Vector2 force = context.calculatePullForce(e);
            context.applyForceToCenter(force, true);
        }

    }
}
