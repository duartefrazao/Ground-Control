package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.groundcontrol.game.controller.elements.PlayerController;

public class RunState implements PlayerState {

    public void handleInput(PlayerController context, InputDecoder.Input input){
        if(input == InputDecoder.Input.JUMP){
            context.setState(new JumpState());

        }else if(input== InputDecoder.Input.LEFT_RIGHT){
            context.setState(new IdleState());

        }else if(input == InputDecoder.Input.RIGHT){
            context.setLinearVelocity(new Vector2(10,10));

        }
        else if(input == InputDecoder.Input.LEFT){
            context.setLinearVelocity(new Vector2(-10,-10));
        }

    }

    @Override
    public void enter(PlayerController context) {

    }


}
