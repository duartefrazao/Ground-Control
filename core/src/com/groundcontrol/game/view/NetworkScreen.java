package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class NetworkScreen extends ScreenAdapter {


    private final GroundControl game;

    public final static float PIXEL_TO_METER = 0.009f;

    Stage stage;

    ButtonFactory buttonFactory;

    BitmapFont font=new BitmapFont();

    String ip = "";

    public NetworkScreen(GroundControl game) {
        this.game = game;
        loadAssets();


        stage = createMenuStage();

        Gdx.input.setInputProcessor(stage);
    }

    public Button createButton(String filePath, float x, float y, int w, int h,  String ipAdd){
        Button but= buttonFactory.makeButton(game.getAssetManager().get(filePath,Texture.class),game.getAssetManager().get(filePath,Texture.class), w/2,h/4, (int)(w/2),(int)(h)/8);

        final String toAdd= ipAdd;
        but.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + toAdd;
            }
        });

        return but;
    }


    public String hexToDec(String hex){
        String result="";

        for(int i = 0;i < hex.length();i+=2){
            result+=Integer.parseInt(hex.substring(i,i+2),16)+".";
        }
        return result.substring(0,result.length()-1);
    }

    private Stage createMenuStage(){

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        buttonFactory = new ButtonFactory();



        Button exitButton = buttonFactory.makeButton(game.getAssetManager().get("exit.png",Texture.class),game.getAssetManager().get("exit.png",Texture.class),  2*w/3,5*h/6, (int)(w/3),(int)(h)/8);
        exitButton .addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.startMainMenu();
            }
        });

        Button mpButton = buttonFactory.makeButton(game.getAssetManager().get("multiplayer.png",Texture.class),game.getAssetManager().get("multiplayer.png",Texture.class),  w/3,5*h/6, (int)(w/3),(int)(h)/8);
        mpButton .addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.startNewMut(ip);
            }
        });

        Stage stage= new Stage();

        stage.addActor(exitButton);
        stage.addActor(mpButton);

        return stage;
    }

    private void loadAssets(){
        this.game.getAssetManager().load("background.png", Texture.class);
        this.game.getAssetManager().load("exit.png", Texture.class);
        this.game.getAssetManager().load("start.png", Texture.class);
        this.game.getAssetManager().load("Numbers/one.png", Texture.class);
        this.game.getAssetManager().load("Numbers/two.png", Texture.class);
        this.game.getAssetManager().load("Numbers/three.png", Texture.class);
        this.game.getAssetManager().load("Numbers/four.png", Texture.class);
        this.game.getAssetManager().load("Numbers/five.png", Texture.class);
        this.game.getAssetManager().load("Numbers/six.png", Texture.class);
        this.game.getAssetManager().load("Numbers/seven.png", Texture.class);
        this.game.getAssetManager().load("Numbers/eight.png", Texture.class);
        this.game.getAssetManager().load("Numbers/nine.png", Texture.class);
        this.game.getAssetManager().load("Numbers/zero.png", Texture.class);
        this.game.getAssetManager().load("Numbers/correct.png", Texture.class);

        this.game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta){


        super.render(delta);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        //drawBackGround();

        font.draw(game.getBatch(), "---- "+ ip, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/20);
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
