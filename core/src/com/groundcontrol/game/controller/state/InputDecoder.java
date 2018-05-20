package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.view.GameView;

public class InputDecoder {

    public enum Input {LEFT, RIGHT, LEFT_RIGHT, JUMP, PLANET_LAND}


    public Input convertViewInput(GameView.StateInput input) {


        switch (input) {
            case LEFT_BUTTON:
                return Input.LEFT;
            case RIGHT_BUTTON:
                return Input.RIGHT;
            case RIGHT_LEFT_BUTTONS:
                return Input.LEFT_RIGHT;
            case SPACE_BUTTON:
                return Input.JUMP;
        }

        return Input.LEFT_RIGHT;
    }

    public InputDecoder(){

    }


}