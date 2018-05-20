package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.groundcontrol.game.controller.elements.PlayerController;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class FloatState implements PlayerState {


    @Override
    public void handleInput(PlayerController context, InputDecoder.Input input) {

        if (input == InputDecoder.Input.PLANET_LAND) {
            context.setState(new IdleState());
        }

    }

    public void setRotation(PlayerController context, ArrayList<Body> objects) {

        for (Body e : objects) {

            float distance = abs(e.getPosition().x - context.getX());
            distance += abs(e.getPosition().y - context.getY());

            if (distance < 8) {

                context.setTransform(context.getX(), context.getY(), context.getAngleBetween(e));

            }

        }

    }


    public void applyPullForce(PlayerController context, ArrayList<Body> objects) {

        for (Body e : objects) {
            Vector2 force = context.calculatePullForce(e);
            context.applyForceToCenter(force, true);
        }

    }
}
