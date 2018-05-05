package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;

import java.util.ArrayList;

import static com.groundcontrol.game.view.GameView.StateInput.*;
import static com.groundcontrol.game.view.GameView.StateInput;

public class IdleState implements PlayerState {

    public void handleInput(PlayerController context, StateInput input){
        if(input== SPACE_BUTTON)
            context.setState(new JumpState());
        else if(input != RIGHT_LEFT_BUTTONS && (input == RIGHT_BUTTON || input == LEFT_BUTTON))
            context.setState(new WalkState());


    }

    @Override
    public void enter(PlayerController context) {

    }
}
