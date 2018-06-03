package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.network.Client;

/**
 * Section responsible for multiplayer second player game
 */
public class MultiplayerClientSection implements Section{

    Client client;
    GameView gv;
    Stage stage;

    InputMultiplexer ip = new InputMultiplexer();
    private float currTime = 0;

    public MultiplayerClientSection(GameView gameView) {

        this.gv= gameView;
        stage=createStage();
        loadAssets();
        ip.addProcessor(stage);
    }

    @Override
    public void update(float delta) {
        if(client.isAlive())
            client.tick();
        else{
            gv.lostConnectionSection.transition();
            client.stop();
        }


        handleInputs(delta);

        String messageReceived = null;
        messageReceived = client.receiveMessage();

        if(messageReceived != null){
            if(messageReceived.equals("PAUSE")){
                gv.pauseSecondSection.setClient(client);
                gv.pauseSecondSection.transition();
            }else if(messageReceived.equals("LOST")){
                //client.sendMessage("LOST");
                //client.sendMessages();
                gv.gameOverSecondSection.transition();
                gv.gameOverSecondSection.setClient(client);
            }

        }

    }

    @Override
    public void display(float delta) {
        drawBackground();
    }

    @Override
    public void transition() {
        Gdx.input.setInputProcessor(ip);
        gv.currentSection=gv.multiplayerClient;
    }

    @Override
    public Stage createStage() {

        ButtonFactory butFac = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        Button pauseButton=butFac.makeButton(gv.game.getAssetManager().get("Buttons/pause.png",Texture.class),gv.game.getAssetManager().get("Buttons/pause.png",Texture.class),19*w/20f,19*h/20f,(int)(w+h)/20,(int)(h+w)/20);
        pauseButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                sendPauseMessage();
                gv.pauseSecondSection.setClient(client);
                gv.pauseSecondSection.transition();
                return true;
            }

            @Override
            public void touchUp (InputEvent e, float x, float y, int pointer, int button){
            }
        });


        Image background = new Image(new Texture(Gdx.files.internal("backgroundSecond.png")));

        background.setBounds(Gdx.graphics.getWidth(),0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        background.setPosition(0,0 );

        Stage stage= new Stage();

        stage.addActor(background);
        stage.addActor(pauseButton);


        return stage;

    }

    @Override
    public void loadAssets() {
    }

    @Override
    public void drawStages(float delta) {
        stage.draw();
    }

    @Override
    public void drawBackground() {
    }

    private void handleInputs(float delta){

        this.currTime+=delta;

        if(currTime>0.050){
            this.currTime=0;
        }else return;

        boolean accAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        if(accAvailable){

            float vx = Gdx.input.getAccelerometerX();
            float vy = Gdx.input.getAccelerometerY();
            client.sendMessage("vx"+Float.toString(vx));
            client.sendMessage("vy"+Float.toString(vy));
        }
    }

    public boolean connectClient(String ip){
        client= new Client();
        return client.startConnection(ip,8500);
    }


    public void sendPauseMessage() {
        client.sendMessage("PAUSE");
    }
}
