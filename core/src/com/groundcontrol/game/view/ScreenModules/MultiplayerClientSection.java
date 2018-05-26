package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.network.Client;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

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
            client.stop();
            gv.gameModel=new GameModel();
            gv.gameController = new GameController(gv.gameModel);
            gv.menuSection.transition();
        }


        handleInputs(delta);

        String messageReceived = null;
        messageReceived = client.receiveMessage();

        if(messageReceived != null){
            if(messageReceived.equals("PAUSE")){
                gv.pauseSecondSection.setClient(client);
                gv.pauseSecondSection.transition();
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



        Stage stage= new Stage();

        stage.addActor(pauseButton);


        return stage;

    }

    @Override
    public void loadAssets() {
        gv.game.getAssetManager().load("backgroundSecond.png", Texture.class);
        gv.game.getAssetManager().finishLoading();
    }

    @Override
    public void drawStages(float delta) {
        stage.draw();
    }

    @Override
    public void drawBackground() {
        Texture background = gv.game.getAssetManager().get("backgroundSecond.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        gv.game.getBatch().draw(background, 0, 0, 0, 0, (int)(ARENA_WIDTH / gv.PIXEL_TO_METER), (int) (ARENA_HEIGHT / gv.PIXEL_TO_METER));

    }

    private void handleInputs(float delta){

        this.currTime+=delta;

        if(delta>30){
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
