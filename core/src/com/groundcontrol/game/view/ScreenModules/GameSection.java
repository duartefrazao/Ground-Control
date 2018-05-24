package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.model.elements.CometModel;
import com.groundcontrol.game.model.elements.ExplosionModel;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.elements.ElementView;
import com.groundcontrol.game.view.elements.PlayerView;
import com.groundcontrol.game.view.elements.ViewFactory;

import java.util.List;

public class GameSection implements Section, GestureDetector.GestureListener{

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
        List<CometModel> comets = gv.gameModel.getComets();
        for(CometModel c : comets){
            ElementView view = ViewFactory.makeView(gv.game, c);
            view.update(c);
            view.draw(gv.game.getBatch());
        }
        List<ExplosionModel> explosions = gv.gameModel.getExplosions();
        for(ExplosionModel e : explosions){
            ElementView view = ViewFactory.makeView(gv.game, e);
            view.update(e);
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
                gv.setCurrentInput(GameView.StateInput.LEFT_BUTTON);
                return true;
            }

            @Override
            public void touchUp (InputEvent e, float x, float y, int pointer, int button){
                gv.setCurrentInput(GameView.StateInput.IDLE);
            }
        });

        Button rightButton=butFac.makeButton(gv.game.getAssetManager().get("Buttons/right.png",Texture.class),gv.game.getAssetManager().get("Buttons/right.png",Texture.class),2*w/3f,2*h/20f,(int)(w+h)/16,(int)(h+w)/16);
        rightButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                gv.setCurrentInput(GameView.StateInput.RIGHT_BUTTON);
                return true;
            }

            @Override
            public void touchUp (InputEvent e, float x, float y, int pointer, int button){
                gv.setCurrentInput(GameView.StateInput.IDLE);
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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (gv.paused) return false;

        this.gv.gameController.handleInput(GameView.StateInput.SPACE_BUTTON);

        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }


}
