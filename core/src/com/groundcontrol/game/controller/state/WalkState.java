package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.view.GameView;

import java.util.ArrayList;

import static com.groundcontrol.game.view.GameView.StateInput.*;

public class WalkState implements PlayerState {

    int times= 0;

    public void handleInput(PlayerController context, GameView.StateInput input){

        if(input == SPACE_BUTTON){
            context.setState(new JumpState());

        }else if(input== RIGHT_LEFT_BUTTONS) {
            context.setState(new IdleState());
        }

        //POS -> RIGHT
        //NEG ->LEFT


        float rot = context.getAngleBetween(context.getPlanet());

        rot -= Math.PI / 2.0;

        Vector2 direction = new Vector2((float) Math.cos(rot), (float) Math.sin(rot));

        //TODO -> Calculate the desire velocity in the planet
        direction.scl(context.getPlanet().getMass() / 7);

         if(input == RIGHT_BUTTON){
            context.setLinearVelocity(direction.rotate90(1));

        }
        else if(input ==LEFT_BUTTON){
             context.setLinearVelocity(direction.rotate90(-1));
        }


    }

    @Override
    public void enter(PlayerController context) {

    }
}
