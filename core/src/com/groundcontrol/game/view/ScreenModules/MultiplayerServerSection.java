package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.PlayerModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.elements.PlayerView;
import com.groundcontrol.game.view.elements.ViewFactory;
import com.groundcontrol.game.view.network.Server;

public class MultiplayerServerSection extends GameSection{


    private Server server;

    public MultiplayerServerSection(GameView gameView){
        super(gameView);
        server=new Server();

    }

    @Override
    public void update(float delta) {

        /*
        if(!Gdx.input.isTouched()){
            currentInput=StateInput.IDLE;
        }
        */


        gv.gameController.handleInput(currentInput);


        if(server.isAlive()) {
            server.tick();
        }
        else {
            server.stop();
            gv.gameModel=new GameModel();
            gv.gameController = new GameController(gv.gameModel);
            gv.menuSection.transition();
        }

        receiveInputs(delta);

        gv.gameController.setPlanetForce(delta, -vx, -vy);

        gv.gameController.update(delta);
    }

    private void receiveInputs(float delta) {
        String messageReceived = null;
        messageReceived = server.receiveMessage();

        if(messageReceived != null){
            System.out.println(messageReceived);
            if(messageReceived.equals("PAUSE"))
            {
                gv.pauseFirstSection.setServer(server);
                gv.pauseFirstSection.transition();
            }
            else if(messageReceived.substring(0,2).equals("vx")) {
                messageReceived=messageReceived.substring(2);
                vx=Float.valueOf(messageReceived);
            }else if(messageReceived.substring(0,2).equals("vy")){
                messageReceived=messageReceived.substring(2);
                vy=Float.valueOf(messageReceived);
            }
        }
    }


    @Override
    public void transition() {
        PlayerModel player = gv.gameModel.getPlayer();
        PlayerView viewPlayer = (PlayerView) ViewFactory.makeView(gv.game, player);
        viewPlayer.removeStopFrame();
        Gdx.input.setInputProcessor(this.ip);

        gv.currentSection = gv.multiplayerServer;

    }

    @Override
    public Stage createStage() {
        ButtonFactory butFac = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        Button leftButton=butFac.makeButton( gv.game.getAssetManager().get("Buttons/left.png",Texture.class),gv.game.getAssetManager().get("Buttons/left.png",Texture.class),w/3f,2*h/20f,(int)(w+h)/16,(int)(h+w)/16);
        leftButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                currentInput=StateInput.LEFT_BUTTON;
                return true;
            }

            @Override
            public void touchUp (InputEvent e, float x, float y, int pointer, int button){
                currentInput=StateInput.IDLE;
            }
        });

        Button rightButton=butFac.makeButton(gv.game.getAssetManager().get("Buttons/right.png",Texture.class),gv.game.getAssetManager().get("Buttons/right.png",Texture.class),2*w/3f,2*h/20f,(int)(w+h)/16,(int)(h+w)/16);
        rightButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                currentInput=StateInput.RIGHT_BUTTON;
                return true;
            }

            @Override
            public void touchUp (InputEvent e, float x, float y, int pointer, int button){
                currentInput=StateInput.IDLE;
            }
        });

        Button pauseButton=butFac.makeButton(gv.game.getAssetManager().get("Buttons/pause.png",Texture.class),gv.game.getAssetManager().get("Buttons/pause.png",Texture.class),19*w/20f,19*h/20f,(int)(w+h)/20,(int)(h+w)/20);
        pauseButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                sendPauseMessage();
                gv.pauseFirstSection.setServer(server);
                gv.pauseFirstSection.transition();
                return true;
            }

            @Override
            public void touchUp (InputEvent e, float x, float y, int pointer, int button){
            }
        });


        Stage stage= new Stage();

        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(pauseButton);


        return stage;
    }


    public void setServer(Server server) {
        this.server = server;
    }

    public void stopServer() {
        server.stop();
    }

    public void sendPauseMessage() {
        server.sendMessage("PAUSE");
    }
}
