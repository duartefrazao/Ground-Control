package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.view.ScreenModules.GameSection;

public class InputDecoder {

    public enum Input {LEFT, RIGHT, JUMP, PLANET_LAND, IDLE}


    public static Input convertViewInput(GameSection.StateInput input) {

        switch (input) {
            case LEFT_BUTTON:
                return Input.LEFT;
            case RIGHT_BUTTON:
                return Input.RIGHT;
            case SPACE_BUTTON:
                return Input.JUMP;
            case IDLE:
                return Input.IDLE;
        }

        return Input.IDLE;
    }


}
