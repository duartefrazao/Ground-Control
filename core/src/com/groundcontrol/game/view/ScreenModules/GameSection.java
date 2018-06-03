package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
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
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.elements.ViewFactory;

import java.text.DecimalFormat;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

/**
 * Section responsible for single player game and main components for multiplayer
 */
public class GameSection implements Section, GestureDetector.GestureListener {

    protected GameView gv;
    protected InputMultiplexer ip = new InputMultiplexer();
    protected Stage stage;
    protected StateInput currentInput = StateInput.IDLE;

    //Time Components
    protected BitmapFont font = new BitmapFont();
    protected Color whiteColor = new Color(Color.WHITE);
    protected float vx = 0, vy = 0;
    protected float timeLeft;
    protected Label timeLabel;
    private DecimalFormat df = new DecimalFormat();

    Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/space_sound.mp3"));

    public GameSection(GameView gameView) {

        this.gv = gameView;
        loadAssets();
        stage = createStage();
        stage.addActor(createTimeTable());
        ip.addProcessor(stage);
        ip.addProcessor(new GestureDetector(this));

        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        backgroundMusic.setLooping(true);

    }

    private Table createTimeTable() {
        Table table = new Table();
        table.center().top();
        timeLeft = 0;
        font = new BitmapFont();
        font.getData().setScale(4);
        timeLabel = new Label(Float.toString(timeLeft), new Label.LabelStyle(font, whiteColor));
        table.add(timeLabel).height(Gdx.graphics.getHeight() / 5);
        table.setFillParent(true);
        return table;
    }

    @Override
    public void update(float delta) {

        gv.gameController.removeFlagged();

        gv.gameController.createNewPlanets();

        gv.gameController.checkForNewComet(delta);

        gv.gameController.handleInput(currentInput);

        boolean accAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        if (accAvailable) {
            vx = Gdx.input.getAccelerometerX();
            vy = Gdx.input.getAccelerometerY();
        }

        gv.gameController.setPlanetForce(delta, -vx, -vy);

        gv.gameController.update(delta);

    }

    @Override
    public void display(float delta) {
        drawBackground();

        ViewFactory.drawElement(gv.gameModel.getPlayer(), gv);

        ViewFactory.drawAllElements(gv.gameModel.getPlanets(), gv);
        ViewFactory.drawAllElements(gv.gameModel.getComets(), gv);
        ViewFactory.drawAllElements(gv.gameModel.getExplosions(), gv);

        if(gv.gameModel.getPlayer().hasLost()){
            backgroundMusic.stop();
            gv.gameOverSection.transition();
        }

    }

    @Override
    public void transition() {

        ViewFactory.updatePause(gv.gameModel.getPlayer(), gv, false);
        ViewFactory.updatePauseElements(gv.gameModel.getComets(), gv, false);
        ViewFactory.updatePauseElements(gv.gameModel.getComets(), gv, false);

        Gdx.input.setInputProcessor(this.ip);

        gv.currentSection = gv.gameSection;

        backgroundMusic.play();

    }

    @Override
    public Stage createStage() {
        ButtonFactory butFac = new ButtonFactory();

        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();

        Button leftButton = butFac.makeButton(gv.game.getAssetManager().get("Buttons/left.png", Texture.class), gv.game.getAssetManager().get("Buttons/left.png", Texture.class), w / 3f, 2 * h / 20f, (int) (w + h) / 16, (int) (h + w) / 16);
        leftButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                currentInput = StateInput.LEFT_BUTTON;
                return true;
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int pointer, int button) {
                currentInput = StateInput.IDLE;
            }
        });

        Button rightButton = butFac.makeButton(gv.game.getAssetManager().get("Buttons/right.png", Texture.class), gv.game.getAssetManager().get("Buttons/right.png", Texture.class), 2 * w / 3f, 2 * h / 20f, (int) (w + h) / 16, (int) (h + w) / 16);
        rightButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                currentInput = StateInput.RIGHT_BUTTON;
                return true;
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int pointer, int button) {
                currentInput = StateInput.IDLE;
            }
        });

        Button pauseButton = butFac.makeButton(gv.game.getAssetManager().get("Buttons/pause.png", Texture.class), gv.game.getAssetManager().get("Buttons/pause.png", Texture.class), -20 + 19 * w / 20f, 19 * h / 20f, (int) (w + h) / 20, (int) (h + w) / 20);
        pauseButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                backgroundMusic.stop();
                gv.pauseSection.transition();
                return true;
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int pointer, int button) {
            }
        });


        Stage stage = new Stage();

        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(pauseButton);


        return stage;
    }

    @Override
    public void loadAssets() {

        gv.game.getAssetManager().load("Planets/BigPlanet.png", Texture.class);
        gv.game.getAssetManager().load("Planets/MediumBigPlanet.png", Texture.class);
        gv.game.getAssetManager().load("Planets/MediumPlanet.png", Texture.class);
        gv.game.getAssetManager().load("Planets/SmallPlanet.png", Texture.class);

        gv.game.getAssetManager().load("IdleAssassin.png", Texture.class);
        gv.game.getAssetManager().load("assassin.png", Texture.class);
        gv.game.getAssetManager().load("RunningAssassin.png", Texture.class);
        gv.game.getAssetManager().load("FlyingAssassin.png", Texture.class);

        gv.game.getAssetManager().load("Comet/comet.png", Texture.class);
        gv.game.getAssetManager().load("Comet/Comet_Array.png", Texture.class);

        this.loadExplosion();

        gv.game.getAssetManager().load("background.png", Texture.class);
        gv.game.getAssetManager().load("Buttons/left.png", Texture.class);
        gv.game.getAssetManager().load("Buttons/right.png", Texture.class);
        gv.game.getAssetManager().load("Buttons/pause.png", Texture.class);
        gv.game.getAssetManager().load("resume.png", Texture.class);

        gv.game.getAssetManager().finishLoading();
    }

    private void loadExplosion() {

        for (int i = 1; i <= 8; i++) {
            gv.game.getAssetManager().load("Explosion/explosion[" + i + "].png", Texture.class);
        }

    }

    @Override
    public void drawStages(float delta) {

        this.timeLabel.setText(df.format((gv.gameModel.getTimeLeft())));

        stage.draw();

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

    public enum StateInput {RIGHT_BUTTON, LEFT_BUTTON, SPACE_BUTTON, IDLE}


}
