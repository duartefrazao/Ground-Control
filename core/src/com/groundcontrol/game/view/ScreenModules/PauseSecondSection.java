package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.network.Client;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

/**
 * Section responsible for multiplayer second player pause view
 */
public class PauseSecondSection extends PauseSection  {

    Client client;

    public PauseSecondSection(GameView gameView) {
        super(gameView);
    }


    @Override
    public void update(float delta) {

        if(client.isAlive()){
            client.tick();
        }else
            exitToMainMenu();

        String messageReceived = null;
        messageReceived = client.receiveMessage();

        if(messageReceived != null){
            if(messageReceived.equals("RESUME"))
                gv.multiplayerClient.transition();
            else if(messageReceived.equals("EXIT"))
            {
                client.sendMessage("EXIT");
                exitToMainMenu();
            }
        }
    }

    @Override
    public void display(float delta){

    }

    @Override
    public void transition(){
        Gdx.input.setInputProcessor(stage);

        gv.currentSection= gv.pauseSecondSection;
    }

    @Override
    public void drawBackground() {

    }

    @Override
    public Stage createStage() {

        Stage stage= new Stage();

        Image backgroundSec = new Image(new Texture(Gdx.files.internal("backgroundSecond.png")));

        backgroundSec.setBounds(Gdx.graphics.getWidth(),0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        backgroundSec.setPosition(0,0 );
        stage.addActor(backgroundSec);


        Image background = new Image(new Texture(Gdx.files.internal("pauseBack.png")));

        background.setBounds(Gdx.graphics.getWidth(),0,Gdx.graphics.getWidth() -Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/3);
        background.setPosition(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/6 );
        stage.addActor(background);


        ButtonFactory butFac = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        Button resumeButton= butFac.makeButton( gv.game.getAssetManager().get("resume.png",Texture.class),gv.game.getAssetManager().get("resume.png",Texture.class),w/2,3.2f*h/6, (int)(w/2),(int)(h)/8);
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                client.sendMessage("RESUME");
                gv.multiplayerClient.transition();
            }
        });

        Button exitButton=butFac.makeButton(gv.game.getAssetManager().get("exitMM.png",Texture.class),gv.game.getAssetManager().get("exitMM.png",Texture.class),w/2,2*h/6, (int)(w/2),(int)(h)/8);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                client.sendMessage("EXIT");
                exitToMainMenu();
            }
        });


        stage.addActor(resumeButton);
        stage.addActor(exitButton);

        return stage;
    }

    public void exitToMainMenu(){
        client.stop();
        gv.menuSection.transition();
    }


    public void setClient(Client client){
        this.client=client;
    }


}
