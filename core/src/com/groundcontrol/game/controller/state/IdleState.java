package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.controller.elements.PlayerController;
import static com.groundcontrol.game.view.GameView.StateInput.*;
import static com.groundcontrol.game.view.GameView.StateInput;

public class IdleState implements PlayerState {

    public PlayerState handleInput(PlayerController context, StateInput input){
        if(input== SPACE_BUTTON) return new JumpState();
        else if(input != RIGHT_LEFT_BUTTONS && (input == RIGHT_BUTTON || input == LEFT_BUTTON)){
            return new WalkState();
        }

        return null;
    }

    @Override
    public void enter(PlayerController context) {

    }
}
