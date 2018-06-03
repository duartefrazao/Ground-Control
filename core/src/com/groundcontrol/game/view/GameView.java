package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    public final ConnectClientSection connectClientSection;
    public final ConnectServerSection connectServerSection;
    public final MenuSection menuSection;
    public final PauseSection pauseSection;
    public final MultiplayerSectionSelector multiplayerSectionSelector;
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

        this.menuSection= new MenuSection(this);
        this.gameSection= new GameSection(this);
        this.connectClientSection= new ConnectClientSection(this);
        this.connectServerSection= new ConnectServerSection(this);
        this.pauseSection= new PauseSection(this);
        this.multiplayerSectionSelector= new MultiplayerSectionSelector(this);
        this.multiplayerServer= new MultiplayerServerSection(this);
        this.multiplayerClient= new MultiplayerClientSection(this);
        this.pauseSecondSection = new PauseSecondSection(this);
        this.pauseFirstSection = new PauseFirstSection(this);
        this.gameOverSection = new GameOverSection(this);
        this.gameOverFirstSection = new GameOverFirstSection(this);
        this.gameOverSecondSection = new GameOverSecondSection(this);


        menuSection.transition();


        currentSection.loadAssets();
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

}
