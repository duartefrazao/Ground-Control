package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
        drawBackground();
    }

    @Override
    public void transition(){
        Gdx.input.setInputProcessor(stage);

        gv.currentSection= gv.pauseSecondSection;
    }

    @Override
    public void drawBackground() {
        Texture background = game.getAssetManager().get("backgroundSecond.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(ARENA_WIDTH / gv.PIXEL_TO_METER), (int) (ARENA_HEIGHT / gv.PIXEL_TO_METER));

    }

    @Override
    public Stage createStage() {
        ButtonFactory butFac = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        Button resumeButton= butFac.makeButton( gv.game.getAssetManager().get("resume.png",Texture.class),gv.game.getAssetManager().get("resume.png",Texture.class),w/2,2*h/3, (int)(w/2),(int)(h)/4);
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                client.sendMessage("RESUME");
                gv.multiplayerClient.transition();
            }
        });

        Button exitButton=butFac.makeButton(gv.game.getAssetManager().get("exit.png",Texture.class),gv.game.getAssetManager().get("exit.png",Texture.class),w/2,h/3, (int)(w/2),(int)(h)/4);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                client.sendMessage("EXIT");
                exitToMainMenu();
            }
        });

        Stage stage= new Stage();

        stage.addActor(resumeButton);
        stage.addActor(exitButton);

        return stage;
    }

    public void exitToMainMenu(){
        client.stop();
        gv.gameModel=new GameModel();
        gv.gameController = new GameController(gv.gameModel);
        gv.menuSection.transition();
    }


    public void setClient(Client client){
        this.client=client;
    }
}
