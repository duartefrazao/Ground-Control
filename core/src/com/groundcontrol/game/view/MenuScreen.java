package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.view.ScreenModules.GameSection;
import com.groundcontrol.game.view.ScreenModules.NetworkSection;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;

import java.io.IOException;
import java.net.Socket;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class MenuScreen extends ScreenAdapter {


    private final GroundControl game;

    public final static float PIXEL_TO_METER = 0.009f;

    Stage stage;

    private Button exitButton;
    private Button gameButton;
    private Button mpButton;

    NetworkSection networkSection;
    private boolean started = false;
    private boolean clickedGame =false;
    private BitmapFont font = new BitmapFont();

    public MenuScreen(GroundControl game) {
        this.game = game;
        loadAssets();

        stage = createMenuStage();

        Gdx.input.setInputProcessor(stage);
    }

    private Stage createMenuStage(){

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        ButtonFactory buttonFactory = new ButtonFactory();
        exitButton= buttonFactory.makeButton(game.getAssetManager().get("exit.png",Texture.class),game.getAssetManager().get("exit.png",Texture.class), w/2,h/4, (int)(w/2),(int)(h)/8);

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                exitGame();
            }
        });
        gameButton = buttonFactory.makeButton(game.getAssetManager().get("start.png",Texture.class),game.getAssetManager().get("start.png",Texture.class),  w/2,3*h/4, (int)(w/2),(int)(h)/8);
        gameButton .addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                clickedGame=true;
                startGame();
            }
        });

        mpButton = buttonFactory.makeButton(game.getAssetManager().get("multiplayer.png",Texture.class),game.getAssetManager().get("multiplayer.png",Texture.class),  w/2,2*h/4, (int)(w/2),(int)(h)/8);
        mpButton .addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                startMP();
            }
        });

        Stage stage= new Stage();

        stage.addActor(exitButton);
        stage.addActor(gameButton);
        stage.addActor(mpButton);

        return stage;
    }



    private void exitGame() {
        System.exit(0);
    }

    private void startGame(){
        game.startNewGame();
    }

    private void startMP(){
        game.startMP();
    }

    private void loadAssets(){
        this.game.getAssetManager().load("background.png", Texture.class);
        this.game.getAssetManager().load("exit.png", Texture.class);
        this.game.getAssetManager().load("start.png", Texture.class);
        this.game.getAssetManager().load("multiplayer.png", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta){


        super.render(delta);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();

        drawBackGround();
        if(!started){
            Socket s = null;
            String serverIP="";
            try {
                s = new Socket("google.pt", 80);

                serverIP = s.getLocalAddress().getHostAddress();

               // System.out.println("IP->" +serverIP);
                s.close();
            } catch (IOException e) {
                font.draw(game.getBatch(), "Couldn't get device ip", Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/20);
                e.printStackTrace();
            }
            if(!serverIP.equals(""))
                font.draw(game.getBatch(), "Enter the following ip on the other device" + serverIP, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/20);
        }
        game.getBatch().end();

        stage.act(delta);
        stage.draw();

    }



    public void drawBackGround(){
        Texture background = game.getAssetManager().get("background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(ARENA_WIDTH / PIXEL_TO_METER), (int) (ARENA_HEIGHT / PIXEL_TO_METER));

    }

    /**
     * Resize the stage viewport when the screen is resized
     *
     * @param width the new screen width
     * @param height the new screen height
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


}
