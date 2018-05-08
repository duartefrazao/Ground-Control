package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.model.elements.ElementModel;

public class WalkState implements PlayerState {

    public void handleInput(PlayerController context, InputDecoder.Input input){

        if(input == InputDecoder.Input.LEFT_RIGHT)
            context.setState(new IdleState());


        //POS -> RIGHT
        //NEG ->LEFT


        float rot = context.getAngleBetween(context.getPlanet());

        rot -= Math.PI / 2.0;

        Vector2 direction = new Vector2((float) Math.cos(rot), (float) Math.sin(rot));

        //TODO -> Calculate the desire velocity in the planet
        direction.scl(10) ;


        if(input == InputDecoder.Input.JUMP) {

            context.removeFromPlanet();

            context.setLinearVelocity(direction.scl(10));

            context.setState(new FloatState());

            return;

        }

         if(input == InputDecoder.Input.RIGHT){
            context.setLinearVelocity(direction.rotate90(1));
             context.setRightSide(true);

        }
        else if(input == InputDecoder.Input.LEFT){
             context.setLinearVelocity(direction.rotate90(-1));
             context.setRightSide(false);
        }


    }

    @Override
    public void enter(PlayerController context) {

    }
}
