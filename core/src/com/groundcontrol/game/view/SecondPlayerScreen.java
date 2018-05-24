package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.view.network.Client;

import java.io.IOException;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class SecondPlayerScreen extends ScreenAdapter{
    private final GroundControl game;
    private final Client client;

    public enum StateInput { RIGHT_BUTTON, LEFT_BUTTON, SPACE_BUTTON, RIGHT_LEFT_BUTTONS}

    public final static float PIXEL_TO_METER = 0.009f;

    private static final float VIEWPORT_WIDTH = 15;

    private final OrthographicCamera camera;



    public SecondPlayerScreen(GroundControl game, Client client) {
        this.game = game;
        loadAssets();

        camera = createCamera();
        
        this.client = client;
        //System.out.println(this.client.startConnection("172.30.16.118", 25000) == 1);
    }

    private OrthographicCamera createCamera(){

        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;

    }

    private void loadAssets(){
        this.game.getAssetManager().load("backgroundSecond.png", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta){

        if(client.isAlive()){
            client.tick();
            //receiveInputs(delta);
        }
        handleInputs(delta);


        //camera.position.set(GameModel.getInstance().getPlayer().getX()/PIXEL_TO_METER,GameModel.getInstance().getPlayer().getY()/PIXEL_TO_METER,0);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackGround();
        game.getBatch().end();

    }

    private void handleInputs(float delta){

        boolean accAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);


        if(accAvailable){

            float vx = Gdx.input.getAccelerometerX();
            float vy = Gdx.input.getAccelerometerY();

            client.sendMessage("vx"+Float.toString(vx));
            client.sendMessage("vy"+Float.toString(vy));
        }
    }



    public void drawBackGround(){
        Texture background = game.getAssetManager().get("backgroundSecond.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(ARENA_WIDTH / PIXEL_TO_METER), (int) (ARENA_HEIGHT / PIXEL_TO_METER));
    }

}
