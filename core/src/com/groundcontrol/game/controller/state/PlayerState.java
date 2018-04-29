package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.view.GameView;

public interface PlayerState {

    void handleInput(PlayerController context, GameView.StateInput input);

}
