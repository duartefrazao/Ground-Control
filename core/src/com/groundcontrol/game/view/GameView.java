package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.view.ScreenModules.ConnectServerSection;
import com.groundcontrol.game.view.ScreenModules.GameSection;
import com.groundcontrol.game.view.ScreenModules.MenuSection;
import com.groundcontrol.game.view.ScreenModules.MultiplayerClientSection;
import com.groundcontrol.game.view.ScreenModules.MultiplayerSectionSelector;
import com.groundcontrol.game.view.ScreenModules.ConnectClientSection;
import com.groundcontrol.game.view.ScreenModules.MultiplayerServerSection;
import com.groundcontrol.game.view.ScreenModules.PauseFirstSection;
import com.groundcontrol.game.view.ScreenModules.PauseSecondSection;
import com.groundcontrol.game.view.ScreenModules.PauseSection;
import com.groundcontrol.game.view.ScreenModules.Section;

public class GameView extends ScreenAdapter {

    public final GroundControl game;
    private final OrthographicCamera camera;
    public final static float PIXEL_TO_METER = 0.009f;
    private static final float VIEWPORT_WIDTH = 50;
    public Section currentSection;
    public GameSection gameSection;
    public final ConnectClientSection connectClientSection;
    public final ConnectServerSection connectServerSection;
    public final MenuSection menuSection;
    public final PauseSection pauseSection;
    public  final MultiplayerSectionSelector multiplayerSectionSelector;
    public  MultiplayerServerSection multiplayerServer;
    public  MultiplayerClientSection multiplayerClient;




    public GameController gameController;
    public GameModel gameModel;
    public PauseSecondSection pauseSecondSection;
    public PauseFirstSection pauseFirstSection;

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
