package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;

import java.util.ArrayList;

import static com.groundcontrol.game.controller.elements.PlayerController.jumpMultiplier;
import static com.groundcontrol.game.view.GameView.StateInput.*;
import static com.groundcontrol.game.view.GameView.StateInput;

public class IdleState implements PlayerState {

    public void handleInput(PlayerController context, InputDecoder.Input input) {

        if (!context.isInPlanet())
            return;

        if (input == InputDecoder.Input.JUMP) {

            context.jump();

            context.setState(new FloatState());

        } else if (input == InputDecoder.Input.RIGHT || input == InputDecoder.Input.LEFT) {
            context.setState(new WalkState());
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
