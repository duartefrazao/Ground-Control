package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.view.GameView.StateInput;

import java.util.ArrayList;

public interface PlayerState {



    void handleInput(PlayerController context, StateInput input);

    void enter(PlayerController context);



}
