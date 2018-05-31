package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.view.ScreenModules.GameSection;

/**
 * Decodes any given input from the view into one recognized by the controller
 */
public class InputDecoder {

    /**
     * All the controller inputs, regarding movements
     */
    public enum Input {LEFT, RIGHT, JUMP, PLANET_LAND, IDLE}

    /**
     * Converts the input
     * @param input the view input
     * @return an input recognized by the controller
     */
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
