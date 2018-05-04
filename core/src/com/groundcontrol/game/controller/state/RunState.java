package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.model.elements.PlayerModel;
import com.groundcontrol.game.view.GameView;

import static com.groundcontrol.game.view.GameView.StateInput.LEFT_BUTTON;
import static com.groundcontrol.game.view.GameView.StateInput.RIGHT_BUTTON;
import static com.groundcontrol.game.view.GameView.StateInput.RIGHT_LEFT_BUTTONS;
import static com.groundcontrol.game.view.GameView.StateInput.SPACE_BUTTON;

public class RunState implements PlayerState {

    public PlayerState handleInput(PlayerController context, GameView.StateInput input){
        if(input == SPACE_BUTTON){
            return new JumpState();

        }else if(input== RIGHT_LEFT_BUTTONS){
            return new IdleState();

        }else if(input == RIGHT_BUTTON){
            context.setLinearVelocity(new Vector2(10,10));

        }
        else if(input ==LEFT_BUTTON){
            context.setLinearVelocity(new Vector2(-10,-10));
        }


        return null;
    }

    @Override
    public void enter(PlayerController context) {

    }


}
