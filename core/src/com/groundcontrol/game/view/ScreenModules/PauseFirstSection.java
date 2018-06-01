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
import com.groundcontrol.game.view.elements.ViewFactory;
import com.groundcontrol.game.view.network.Server;

public class PauseFirstSection extends PauseSection {

    Server server;


    public PauseFirstSection(GameView gameView) {
        super(gameView);
    }

    @Override
    public void update(float delta) {

        if(server.isAlive())
            server.tick();
        else
            exitToMainMenu();

        String messageReceived = null;


        messageReceived = server.receiveMessage();

        if(messageReceived != null){
            if(messageReceived.equals("RESUME"))
                gv.multiplayerServer.transition();
            else if(messageReceived.equals("EXIT"))
                exitToMainMenu();
        }
    }


    @Override
    public void display(float delta) {
        drawBackground();

        ViewFactory.drawElement(gv.gameModel.getPlayer(), gv);

        ViewFactory.drawAllElements(gv.gameModel.getPlanets(), gv);
        ViewFactory.drawAllElements(gv.gameModel.getComets(), gv);
        ViewFactory.drawAllElements(gv.gameModel.getExplosions(), gv);

    }

    @Override
    public void transition() {

        ViewFactory.updatePause(gv.gameModel.getPlayer(), gv, true);

        ViewFactory.updatePauseElements(gv.gameModel.getExplosions(), gv, true);

        ViewFactory.updatePauseElements(gv.gameModel.getComets(), gv, true);

        Gdx.input.setInputProcessor(stage);

        gv.currentSection= gv.pauseFirstSection;
    }

    @Override
    public Stage createStage() {

        Stage stage= new Stage();

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
                server.sendMessage("RESUME");
                gv.multiplayerServer.transition();
            }
        });

        Button exitButton=butFac.makeButton(gv.game.getAssetManager().get("exitMM.png",Texture.class),gv.game.getAssetManager().get("exitMM.png",Texture.class),w/2,2*h/6, (int)(w/2),(int)(h)/8);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                server.sendMessage("EXIT");
            }
        });


        stage.addActor(resumeButton);
        stage.addActor(exitButton);

        return stage;
    }

    public void exitToMainMenu(){
        server.stop();
        gv.menuSection.transition();
    }


    public void setServer(Server server){
        this.server=server;
    }
}
