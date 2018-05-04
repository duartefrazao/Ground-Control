package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.view.GameView.StateInput;

public class JumpState implements PlayerState {

    public PlayerState handleInput(PlayerController context, StateInput input){
        return null;
    }

    @Override
    public void enter(PlayerController context) {

    }
}
