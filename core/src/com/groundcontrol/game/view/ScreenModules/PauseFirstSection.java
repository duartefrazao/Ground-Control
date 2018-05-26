package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.CometModel;
import com.groundcontrol.game.model.elements.ExplosionModel;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.elements.CometView;
import com.groundcontrol.game.view.elements.ElementView;
import com.groundcontrol.game.view.elements.ExplosionView;
import com.groundcontrol.game.view.elements.PlayerView;
import com.groundcontrol.game.view.elements.ViewFactory;
import com.groundcontrol.game.view.network.Server;

import java.util.List;

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
            {
                exitToMainMenu();
            }
        }
    }


    @Override
    public void display(float delta) {
        drawBackground();

        PlayerModel player = gv.gameModel.getPlayer();
        ElementView viewPlayer = ViewFactory.makeView(gv.game,player);
        viewPlayer.update(player);
        viewPlayer.draw(gv.game.getBatch());

        List<PlanetModel> planets = gv.gameModel.getPlanets();
        for(PlanetModel p : planets){
            ElementView view = ViewFactory.makeView(gv.game,p);
            view.draw(gv.game.getBatch());
        }
        List<CometModel> comets = gv.gameModel.getComets();
        for(CometModel c : comets){
            ElementView view = ViewFactory.makeView(gv.game, c);
            view.draw(gv.game.getBatch());
        }
        List<ExplosionModel> explosions = gv.gameModel.getExplosions();
        for(ExplosionModel e : explosions){
            ElementView view = ViewFactory.makeView(gv.game, e);
        }
    }

    @Override
    public void transition() {

        PlayerModel player = gv.gameModel.getPlayer();
        PlayerView viewPlayer = (PlayerView) ViewFactory.makeView(gv.game,player);
        viewPlayer.setStopped();

        List<ExplosionModel> explosions = gv.gameModel.getExplosions();
        for(ExplosionModel e : explosions){
            ExplosionModel expl = e;
            ExplosionView viewExplosion = (ExplosionView) ViewFactory.makeView(gv.game,expl);
            viewExplosion.setStopped();
        }

        List<CometModel> comets = gv.gameModel.getComets();
        for(CometModel c : comets){
            CometModel cml = c;
            CometView Explosion = (CometView) ViewFactory.makeView(gv.game,cml);
            Explosion.setStopped();
        }

        Gdx.input.setInputProcessor(stage);

        gv.currentSection= gv.pauseFirstSection;
    }

    @Override
    public Stage createStage() {
        ButtonFactory butFac = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        Button resumeButton= butFac.makeButton( gv.game.getAssetManager().get("resume.png",Texture.class),gv.game.getAssetManager().get("resume.png",Texture.class),w/2,2*h/3, (int)(w/2),(int)(h)/4);
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                server.sendMessage("RESUME");
                gv.multiplayerServer.transition();
            }
        });

        Button exitButton=butFac.makeButton(gv.game.getAssetManager().get("exit.png",Texture.class),gv.game.getAssetManager().get("exit.png",Texture.class),w/2,h/3, (int)(w/2),(int)(h)/4);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                server.sendMessage("EXIT");
            }
        });

        Stage stage= new Stage();

        stage.addActor(resumeButton);
        stage.addActor(exitButton);

        return stage;
    }

    public void exitToMainMenu(){
        server.stop();
        gv.gameModel=new GameModel();
        gv.gameController = new GameController(gv.gameModel);
        gv.menuSection.transition();
    }


    public void setServer(Server server){
        this.server=server;
    }
}
