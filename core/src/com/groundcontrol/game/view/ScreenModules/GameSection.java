package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
import com.groundcontrol.game.view.network.Server;

import java.util.List;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class GameSection implements Section, GestureDetector.GestureListener{

    protected GameView gv;
    protected InputMultiplexer ip = new InputMultiplexer();
    protected  Stage stage;

    public enum StateInput {RIGHT_BUTTON, LEFT_BUTTON, SPACE_BUTTON, IDLE}
    protected StateInput currentInput =StateInput.IDLE;


    //Score Components
    protected int score;
    protected BitmapFont font = new BitmapFont();
    protected Label scoreLabel;
    protected Color whiteColor = new Color(Color.WHITE);
    protected float vx=0,vy=0;

    public GameSection(GameView gameView){

        this.gv =gameView;
        loadAssets();
        stage=createStage();
        stage.addActor(createScoreTable());
        ip.addProcessor(stage);
        ip.addProcessor(new GestureDetector(this));
    }

    private Table createScoreTable() {
        Table table = new Table();
        table.center();
        table.top();
        score = 0;
        font = new BitmapFont();
        scoreLabel = new Label(Integer.toString(score), new Label.LabelStyle(font, whiteColor));
        table.add(scoreLabel).height(Gdx.graphics.getHeight() / 10);
        table.setFillParent(true);
        return table;
    }

    @Override
    public void update(float delta) {

        gv.gameController.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            gv.gameController.handleInput(StateInput.LEFT_BUTTON);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gv.gameController.handleInput(StateInput.RIGHT_BUTTON);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            gv.gameController.handleInput(StateInput.SPACE_BUTTON);
        }

        boolean accAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        if(accAvailable) {

            vx = Gdx.input.getAccelerometerX();
            vy = Gdx.input.getAccelerometerY();
        }

        gv.gameController.setPlanetForce(delta, -vx, -vy);


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
    public void transition() {
        PlayerModel player = gv.gameModel.getPlayer();
        PlayerView viewPlayer = (PlayerView) ViewFactory.makeView(gv.game,player);
        viewPlayer.removeStopped();
        Gdx.input.setInputProcessor(this.ip);

        gv.currentSection= gv.gameSection;

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
                gv.currentSection=gv.pauseSection;
                gv.currentSection.transition();
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
    public void loadAssets() {
        gv.game.getAssetManager().load("planet.png", Texture.class);
        gv.game.getAssetManager().load("comet.png", Texture.class);
        gv.game.getAssetManager().load("big_planet.png", Texture.class);
        gv.game.getAssetManager().load("runningSheet.png", Texture.class);
        gv.game.getAssetManager().load("cometSheet.png", Texture.class);
        gv.game.getAssetManager().load("idleSheet.png", Texture.class);
        gv.game.getAssetManager().load("explosionSheet.png", Texture.class);
        gv.game.getAssetManager().load("background.png", Texture.class);
        gv.game.getAssetManager().load("Buttons/left.png", Texture.class);
        gv.game.getAssetManager().load("Buttons/right.png", Texture.class);
        gv.game.getAssetManager().load("Buttons/pause.png", Texture.class);
        gv.game.getAssetManager().load("pauseScreen.png", Texture.class);
        gv.game.getAssetManager().load("resume.png", Texture.class);

        gv.game.getAssetManager().finishLoading();
    }

    @Override
    public void drawStages(float delta) {
        stage.draw();
        this.scoreLabel.setText(Integer.toString(gv.gameModel.getScore()));

    }

    @Override
    public void drawBackground() {
        Texture background = gv.game.getAssetManager().get("background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        gv.game.getBatch().draw(background, 0, 0, 0, 0, (int) (ARENA_WIDTH / gv.PIXEL_TO_METER), (int) (ARENA_HEIGHT / gv.PIXEL_TO_METER));

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
        this.gv.gameController.handleInput(StateInput.SPACE_BUTTON);


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
