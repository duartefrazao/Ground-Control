package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;
import com.groundcontrol.game.view.elements.ElementView;
import com.groundcontrol.game.view.elements.ViewFactory;

import java.util.List;

public class GameView extends ScreenAdapter {

    private final GroundControl game;

    public enum StateInput { RIGHT_BUTTON, lEFT_BUTTON, JUMP_BUTTON}

    public final static float PIXEL_TO_METER = 0.005f;

    private static final float VIEWPORT_WIDTH = 20;

    private final OrthographicCamera camera;

    private GameController gameController;

    private GameModel gameModel;

    public GameView(GroundControl game, GameModel gameModel, GameController gameController){

        this.game = game;
        loadAssets();

        this.gameModel = gameModel;

        this.gameController = gameController;

        camera = createCamera();
    }


    private OrthographicCamera createCamera(){

        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;

    }

    private void loadAssets(){
        this.game.getAssetManager().load("planet.png", Texture.class);
        this.game.getAssetManager().load("player.png", Texture.class);
        this.game.getAssetManager().load("big_planet.png", Texture.class);
        this.game.getAssetManager().load("runningSheet.png", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta){

        handleInputs(delta);

        this.gameController.update(delta);

        //camera.position.set(GameModel.getInstance().getPlayer().getX()/PIXEL_TO_METER,GameModel.getInstance().getPlayer().getY()/PIXEL_TO_METER,0);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawElements();
        game.getBatch().end();

    }

    private void handleInputs(float delta){

        /*

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            GameController.getInstance().moveLeft(delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            GameController.getInstance().moveRight(delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            GameController.getInstance().moveUp(delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            GameController.getInstance().moveDown(delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.R)){
            GameController.getInstance().rotateLeft(delta);
        }

        */


        boolean accAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        if(accAvailable){

            float vx = Gdx.input.getAccelerometerX();
            float vy = Gdx.input.getAccelerometerY();

            this.gameController.setPlanetForce(-vx, -vy);

        }


    }


    public void drawElements(){


        PlayerModel player = this.gameModel.getPlayer();
        ElementView viewPlayer = ViewFactory.makeView(game,player);
        viewPlayer.update(player);
        viewPlayer.draw(game.getBatch());

        List<PlanetModel> planets = this.gameModel.getPlanets();
        for(PlanetModel p : planets){
            ElementView view = ViewFactory.makeView(game,p);
            view.update(p);
            view.draw(game.getBatch());
        }
    }

}
