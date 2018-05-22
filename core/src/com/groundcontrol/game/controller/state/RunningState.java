package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.groundcontrol.game.controller.elements.PlayerController;

import java.util.ArrayList;

public class RunningState implements PlayerState {

    private static int clockWise = 1;
    private static int counterClockWise = -1;

    public void handleInput(PlayerController context, InputDecoder.Input input) {

        if (input == InputDecoder.Input.IDLE) {

            context.setState(new IdleState());

        } else if (input == InputDecoder.Input.JUMP) {

            context.jump();


        } else {

            context.walk(input == InputDecoder.Input.RIGHT ? clockWise : counterClockWise);

            context.setRightSide(input == InputDecoder.Input.RIGHT);

        }
    }

    public void setRotation(PlayerController context, ArrayList<Body> objects) {
        context.setTransform(context.getX(), context.getY(), context.getAngleBetween(context.getPlanet()));
    }

    public void applyPullForce(PlayerController context, ArrayList<Body> objects) {

        Vector2 force = context.calculatePullForce(context.getPlanet());
        context.applyForceToCenter(force, true);


    }
}
