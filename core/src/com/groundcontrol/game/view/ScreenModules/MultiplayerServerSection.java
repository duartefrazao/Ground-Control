package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.network.Server;

import java.io.IOException;

public class MultiplayerServerSection extends GameSection{


    private Server server;

    public MultiplayerServerSection(GameView gameView){
        super(gameView);

    }

    @Override
    public void update(float delta) {
        gv.gameController.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            gv.gameController.handleInput(StateInput.LEFT_BUTTON);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gv.gameController.handleInput(StateInput.RIGHT_BUTTON);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            gv.gameController.handleInput(StateInput.SPACE_BUTTON);
        }

        server.tick();
        receiveInputs(delta);


        gv.gameController.setPlanetForce(delta, -vx, -vy);
    }

    private void receiveInputs(float delta) {
        String messageReceived = null;
        messageReceived = server.receiveMessage();

        if(messageReceived != null){
            if(messageReceived.substring(0,2).equals("vx")) {
                messageReceived=messageReceived.substring(2);
                vx=Float.valueOf(messageReceived);
            }else{
                messageReceived=messageReceived.substring(2);
                vy=Float.valueOf(messageReceived);
            }
        }
    }


    @Override
    public void transition() {


    }



    public void setServer(Server server) {
        this.server = server;
    }
}
