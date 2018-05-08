package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;

import java.util.ArrayList;

import static com.groundcontrol.game.view.GameView.StateInput.*;
import static com.groundcontrol.game.view.GameView.StateInput;

public class IdleState implements PlayerState {

    public void handleInput(PlayerController context, InputDecoder.Input input){

        if(!context.isInPlanet())
            return;

        if(input== InputDecoder.Input.JUMP) {

            float rot = context.getAngleBetween(context.getPlanet());

            rot -= Math.PI / 2.0;

            Vector2 direction = new Vector2((float) Math.cos(rot), (float) Math.sin(rot));

            //TODO -> Calculate the desire velocity in the planet
            direction.scl(context.getPlanet().getMass() / 7);

            context.removeFromPlanet();

            context.setLinearVelocity(direction.scl(10));

            context.setState(new FloatState());

            return;
        }
        else if(input != InputDecoder.Input.LEFT_RIGHT && (input == InputDecoder.Input.RIGHT || input == InputDecoder.Input.LEFT)) {
            context.setState(new WalkState());
        }



    }

    @Override
    public void enter(PlayerController context) {

    }
}
