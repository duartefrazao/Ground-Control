package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.view.ScreenModules.ConnectServerSection;
import com.groundcontrol.game.view.ScreenModules.GameOverFirstSection;
import com.groundcontrol.game.view.ScreenModules.GameOverSecondSection;
import com.groundcontrol.game.view.ScreenModules.GameOverSection;
import com.groundcontrol.game.view.ScreenModules.GameSection;
import com.groundcontrol.game.view.ScreenModules.LostConnectionSection;
import com.groundcontrol.game.view.ScreenModules.MenuSection;
import com.groundcontrol.game.view.ScreenModules.MultiplayerClientSection;
import com.groundcontrol.game.view.ScreenModules.MultiplayerSectionSelector;
import com.groundcontrol.game.view.ScreenModules.ConnectClientSection;
import com.groundcontrol.game.view.ScreenModules.MultiplayerServerSection;
import com.groundcontrol.game.view.ScreenModules.PauseFirstSection;
import com.groundcontrol.game.view.ScreenModules.PauseSecondSection;
import com.groundcontrol.game.view.ScreenModules.PauseSection;
import com.groundcontrol.game.view.ScreenModules.Section;

/**
 * MVC View. Provides an UI and shows all the elements to the user.
 */
public class GameView extends ScreenAdapter {

    /**
     * The current game instance.
     * Provides access to the asset manager.
     */
    public final GroundControl game;
    private final OrthographicCamera camera;

    /**
     * Pixel to meter relation
     */
    public final static float PIXEL_TO_METER = 0.009f;

    /**
     * Viewport width
     */
    public static final float VIEWPORT_WIDTH = 50;

    /**
     * Represents our game current section, i.e., game section, pause section etc...
     */
    public Section currentSection;

    public GameSection gameSection;
    public  ConnectClientSection connectClientSection;
    public  ConnectServerSection connectServerSection;
    public  MenuSection menuSection;
    public  PauseSection pauseSection;
    public  MultiplayerSectionSelector multiplayerSectionSelector;
    public  MultiplayerServerSection multiplayerServer;
    public  MultiplayerClientSection multiplayerClient;
    public PauseSecondSection pauseSecondSection;
    public PauseFirstSection pauseFirstSection;
    public GameOverSection gameOverSection;
    public LostConnectionSection lostConnectionSection;
    public GameOverFirstSection gameOverFirstSection;
    public GameOverSecondSection gameOverSecondSection;



    /**
     * The Controller of the MVC design
     */
    public GameController gameController;

    /**
     * The Model of the MVC design
     */
    public GameModel gameModel;
    public GameView(GroundControl game, GameModel gameModel, GameController gameController){
        this.game= game;

        this.loadAssets();

        this.menuSection= new MenuSection(this);
        menuSection.transition();


        this.gameModel=gameModel;
        this.gameController=gameController;
        camera = createCamera();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        return camera;
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
        currentSection.drawStages(delta);
    }


    private void loadAssets(){

        String nums[] = {"zero","one","two","three","four","five","six","seven","eight","nine","correct","point"};

        for(int i = 0; i < nums.length; i++){
            game.getAssetManager().load("Numbers/" + nums[i]+ ".png", Texture.class);
        }

        game.getAssetManager().load("IPAdress_Insert_2ndPlayer.png", Texture.class);
        game.getAssetManager().load("connect.png", Texture.class);
        game.getAssetManager().load("start.png", Texture.class);

        game.getAssetManager().load("IPAdress_Insert.png", Texture.class);

        game.getAssetManager().load("restart.png", Texture.class);
        game.getAssetManager().load("exitMM.png", Texture.class);
        game.getAssetManager().load("gameOver.png", Texture.class);

        game.getAssetManager().load("menu_background.png", Texture.class);
        game.getAssetManager().load("exit.png", Texture.class);
        game.getAssetManager().load("multiplayer.png", Texture.class);

        game.getAssetManager().load("backgroundSecond.png", Texture.class);

        game.getAssetManager().load("exit.png", Texture.class);
        game.getAssetManager().load("first.png", Texture.class);
        game.getAssetManager().load("second.png", Texture.class);


        game.getAssetManager().load("pauseBack.png", Texture.class);

        this.loadGameAssets();

        game.getAssetManager().finishLoading();

    }

    private void loadGameAssets(){

        game.getAssetManager().load("Planets/BigPlanet.png", Texture.class);
        game.getAssetManager().load("Planets/MediumBigPlanet.png", Texture.class);
        game.getAssetManager().load("Planets/MediumPlanet.png", Texture.class);
        game.getAssetManager().load("Planets/SmallPlanet.png", Texture.class);

        game.getAssetManager().load("IdleAssassin.png", Texture.class);
        game.getAssetManager().load("assassin.png", Texture.class);
        game.getAssetManager().load("RunningAssassin.png", Texture.class);
        game.getAssetManager().load("FlyingAssassin.png", Texture.class);

        game.getAssetManager().load("Comet/comet.png", Texture.class);
        game.getAssetManager().load("Comet/Comet_Array.png", Texture.class);

        this.loadExplosion();

        game.getAssetManager().load("background.png", Texture.class);
        game.getAssetManager().load("Buttons/left.png", Texture.class);
        game.getAssetManager().load("Buttons/right.png", Texture.class);
        game.getAssetManager().load("Buttons/pause.png", Texture.class);
        game.getAssetManager().load("resume.png", Texture.class);

    }

    private void loadExplosion() {

        for (int i = 1; i <= 8; i++) {
            game.getAssetManager().load("Explosion/explosion[" + i + "].png", Texture.class);
        }

    }

}
