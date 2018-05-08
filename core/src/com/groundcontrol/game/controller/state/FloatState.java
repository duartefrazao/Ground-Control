package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.controller.elements.PlayerController;

public class FloatState implements  PlayerState{


    @Override
    public void handleInput(PlayerController context, InputDecoder.Input input) {

        if(input == InputDecoder.Input.PLANET_LAND){
            context.setState(new IdleState());
        }

    }

    @Override
    public void enter(PlayerController context) {

    }
}
