package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.view.GameView.StateInput;

import java.util.ArrayList;

public interface PlayerState {



    PlayerState handleInput(PlayerController context, StateInput input, ArrayList<PlanetController> planets);

    void enter(PlayerController context);



}
