package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.groundcontrol.game.controller.elements.PlayerController;

import java.util.ArrayList;

import static com.groundcontrol.game.controller.elements.PlayerController.walkMultiplier;

public class WalkState implements PlayerState {

    private static int clockWise = 1;
    private static int counterClockWise = -1;

    public void handleInput(PlayerController context, InputDecoder.Input input) {


        if (input == InputDecoder.Input.JUMP) {

            context.jump();

            context.setState(new FloatState());

            return;

        }

        float rot = context.getAngleBetween(context.getPlanet());

        rot -= Math.PI / 2.0;

        Vector2 direction = new Vector2((float) Math.cos(rot), (float) Math.sin(rot)).nor();

        direction.scl(context.calculatePullForce(context.getPlanet()).len() / 60);

        context.applyLinearImpulseToCenter(direction.rotate90(input == InputDecoder.Input.RIGHT ? clockWise : counterClockWise), true);

        context.setRightSide(input == InputDecoder.Input.RIGHT);


    }

    public void setRotation(PlayerController context, ArrayList<Body> objects) {
        context.setTransform(context.getX(), context.getY(), context.getAngleBetween(context.getPlanet()));
    }

    public void applyPullForce(PlayerController context, ArrayList<Body> objects) {
        Vector2 force = context.calculatePullForce(context.getPlanet());
        context.applyForceToCenter(force, true);

    }
}
