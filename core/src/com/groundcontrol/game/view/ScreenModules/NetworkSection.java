package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.elements.ElementView;
import com.groundcontrol.game.view.elements.PlayerView;
import com.groundcontrol.game.view.elements.ViewFactory;

import java.util.List;

public class NetworkSection implements Section{

    private final GameView gv;

    public NetworkSection(GameView gameView) {
        this.gv=gameView;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void display(float delta) {
        gv.drawBackGround();
        draw();
        gv.stage.act(delta);
        gv.stage.draw();
        gv.networkStage.draw();
    }

    @Override
    public void transition() {
        PlayerModel player = gv.gameModel.getPlayer();
        PlayerView viewPlayer = (PlayerView) ViewFactory.makeView(gv.game,player);
        viewPlayer.removeStopped();
        Gdx.input.setInputProcessor(gv.ip);

        gv.currentSection= gv.gameSection;
    }

    @Override
    public void draw() {
        PlayerModel player = gv.gameModel.getPlayer();
        ElementView viewPlayer = ViewFactory.makeView(gv.game,player);
        viewPlayer.draw(gv.game.getBatch());

        List<PlanetModel> planets = gv.gameModel.getPlanets();
        for(PlanetModel p : planets){
            ElementView view = ViewFactory.makeView(gv.game,p);
            view.draw(gv.game.getBatch());
        }
    }

    @Override
    public Stage createStage() {
        ButtonFactory butFac = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        Button resumeButton= butFac.makeButton( gv.game.getAssetManager().get("resume.png",Texture.class),gv.game.getAssetManager().get("resume.png",Texture.class),w/2,2*h/3, (int)(w/2),(int)(h)/4);
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                transition();
            }
        });

        Button exitButton=butFac.makeButton(gv.game.getAssetManager().get("exit.png",Texture.class),gv.game.getAssetManager().get("exit.png",Texture.class),w/2,h/3, (int)(w/2),(int)(h)/4);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.game.startMainMenu();
            }
        });

        Stage stage= new Stage();

        stage.addActor(resumeButton);
        stage.addActor(exitButton);

        return stage;
    }
}
