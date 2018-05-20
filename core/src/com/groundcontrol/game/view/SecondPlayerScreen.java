package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.groundcontrol.game.GroundControl;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class SecondPlayerScreen extends ScreenAdapter{
    private final GroundControl game;

    public enum StateInput { RIGHT_BUTTON, LEFT_BUTTON, SPACE_BUTTON, RIGHT_LEFT_BUTTONS}

    public final static float PIXEL_TO_METER = 0.009f;

    private static final float VIEWPORT_WIDTH = 35;

    private final OrthographicCamera camera;



    public SecondPlayerScreen(GroundControl game) {
        this.game = game;
        loadAssets();

        camera = createCamera();

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
            if(vx+vy!=0)System.out.println("Received input");
        }
    }



    public void drawBackGround(){
        Texture background = game.getAssetManager().get("backgroundSecond.png", Texture.class);
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(ARENA_WIDTH / PIXEL_TO_METER), (int) (ARENA_HEIGHT / PIXEL_TO_METER));

    }

}
