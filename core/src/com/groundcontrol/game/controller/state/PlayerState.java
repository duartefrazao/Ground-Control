package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.view.GameView.StateInput;

public interface PlayerState {



    PlayerState handleInput(PlayerController context, StateInput input);

    void enter(PlayerController context);

}
