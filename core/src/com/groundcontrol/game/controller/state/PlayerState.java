package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.controller.elements.PlayerController;

public interface PlayerState {



    void handleInput(PlayerController context, InputDecoder.Input input);

    void enter(PlayerController context);



}
