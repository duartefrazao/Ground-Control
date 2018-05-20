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

public class GameSection  implements Section{

    GameView gv;

    public GameSection(GameView gameView){
        this.gv =gameView;
    }

    @Override
    public void update(float delta) {
        gv.handleInputs(delta);
        gv.gameController.update(delta);
    }

    @Override
    public void display(float delta) {
        gv.drawBackGround();
        draw();
    }

    @Override
    public void transition() {
        PlayerModel player = gv.gameModel.getPlayer();
        PlayerView viewPlayer = (PlayerView) ViewFactory.makeView(gv.game,player);
        viewPlayer.setStopped();

        gv.paused=true;
        Gdx.input.setInputProcessor(gv.pauseStage);

        gv.currentSection= gv.pauseSection;
    }

    @Override
    public void draw() {
        PlayerModel player = gv.gameModel.getPlayer();
        ElementView viewPlayer = ViewFactory.makeView(gv.game,player);
        viewPlayer.update(player);
        viewPlayer.draw(gv.game.getBatch());

        List<PlanetModel> planets = gv.gameModel.getPlanets();
        for(PlanetModel p : planets){
            ElementView view = ViewFactory.makeView(gv.game,p);
            view.update(p);
            view.draw(gv.game.getBatch());
        }
    }

    @Override
    public Stage createStage() {
        ButtonFactory butFac = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        Button leftButton=butFac.makeButton( gv.game.getAssetManager().get("Buttons/left.png",Texture.class),gv.game.getAssetManager().get("Buttons/left.png",Texture.class),w/3f,2*h/20f,(int)(w+h)/16,(int)(h+w)/16);
        leftButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                gv.leftButtonClicked=true;
                return true;
            }

            @Override
            public void touchUp (InputEvent e, float x, float y, int pointer, int button){
                gv.leftButtonClicked=false;
            }
        });

        Button rightButton=butFac.makeButton(gv.game.getAssetManager().get("Buttons/right.png",Texture.class),gv.game.getAssetManager().get("Buttons/right.png",Texture.class),2*w/3f,2*h/20f,(int)(w+h)/16,(int)(h+w)/16);
        rightButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                gv.rightButtonClicked=true;
                return true;
            }

            @Override
            public void touchUp (InputEvent e, float x, float y, int pointer, int button){
                gv.rightButtonClicked=false;
            }
        });

        Button pauseButton=butFac.makeButton(gv.game.getAssetManager().get("Buttons/pause.png",Texture.class),gv.game.getAssetManager().get("Buttons/pause.png",Texture.class),19*w/20f,19*h/20f,(int)(w+h)/20,(int)(h+w)/20);
        pauseButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                transition();
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
}
