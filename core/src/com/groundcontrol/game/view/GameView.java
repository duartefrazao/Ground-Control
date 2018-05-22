package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.view.ScreenModules.GameSection;
import com.groundcontrol.game.view.ScreenModules.PauseSection;
import com.groundcontrol.game.view.ScreenModules.Section;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class GameView extends ScreenAdapter implements GestureDetector.GestureListener {

    public final static float PIXEL_TO_METER = 0.009f;
    private static final float VIEWPORT_WIDTH = 50;

    private StateInput currentInput = StateInput.IDLE;

    public final Stage pauseStage;
    public final InputMultiplexer ip;
    public final GameSection gameSection;
    public final PauseSection pauseSection;
    public final GroundControl game;
    private final OrthographicCamera camera;
    public boolean paused;
    public GameController gameController;
    public GameModel gameModel;
    public Stage stage;
    public Section currentSection;
    boolean flinged = false;

    //Score Components
    private int score;
    private BitmapFont font;
    private Label scoreLabel;
    private Table scoreTable;
    private Color whiteColor = new Color(Color.WHITE);
    public GameView(GroundControl game, GameModel gameModel, GameController gameController) {

        this.game = game;
        loadAssets();

        this.gameModel = gameModel;

        this.gameController = gameController;

        camera = createCamera();


        gameSection = new GameSection(this);
        pauseSection = new PauseSection(this);
        currentSection = gameSection;

        ip = new InputMultiplexer();
        stage = gameSection.createStage();
        pauseStage = pauseSection.createStage();
        scoreTable = createScoreTable();
        stage.addActor(scoreTable);

        ip.addProcessor(stage);
        ip.addProcessor(new GestureDetector(this));
        Gdx.input.setInputProcessor(ip);


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

    private OrthographicCamera createCamera() {

        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;

    }

    private void loadAssets() {
        this.game.getAssetManager().load("planet.png", Texture.class);
        this.game.getAssetManager().load("player.png", Texture.class);
        this.game.getAssetManager().load("comet.png", Texture.class);
        this.game.getAssetManager().load("big_planet.png", Texture.class);
        this.game.getAssetManager().load("runningSheet.png", Texture.class);
        this.game.getAssetManager().load("cometSheet.png", Texture.class);
        this.game.getAssetManager().load("idleSheet.png", Texture.class);
        this.game.getAssetManager().load("background.png", Texture.class);
        this.game.getAssetManager().load("Buttons/left.png", Texture.class);
        this.game.getAssetManager().load("Buttons/right.png", Texture.class);
        this.game.getAssetManager().load("Buttons/pause.png", Texture.class);
        this.game.getAssetManager().load("pauseScreen.png", Texture.class);
        this.game.getAssetManager().load("resume.png", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {


        currentSection.update(delta);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(103 / 255f, 69 / 255f, 117 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        game.getBatch().begin();

        currentSection.display(delta);

        game.getBatch().end();
        stage.draw();
        this.scoreLabel.setText(Integer.toString(this.gameModel.getScore()));



    }

    public void handleInputs(float delta) {

        gameController.handleInput(currentInput);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.gameController.handleInput(StateInput.LEFT_BUTTON);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.gameController.handleInput(StateInput.RIGHT_BUTTON);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.gameController.handleInput(StateInput.SPACE_BUTTON);
        }

        if (flinged) {
            flinged = false;
            this.gameController.handleInput(StateInput.SPACE_BUTTON);
        }


        boolean accAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        if (accAvailable) {

            float vx = Gdx.input.getAccelerometerX();
            float vy = Gdx.input.getAccelerometerY();

            this.gameController.setPlanetForce(delta, -vx, -vy);

        }


    }

    public void setCurrentInput(StateInput state){

        this.currentInput = state;

    }

    public void drawBackGround() {
        Texture background = game.getAssetManager().get("background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int) (ARENA_WIDTH / PIXEL_TO_METER), (int) (ARENA_HEIGHT / PIXEL_TO_METER));

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (paused) return false;

        flinged = true;
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (paused) return false;

        flinged = true;
        return false;
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
