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

public class NetworkScreen extends ScreenAdapter {


    private final GroundControl game;

    public final static float PIXEL_TO_METER = 0.009f;

    Stage stage;

    private Button gameButton;
    private Button mpButton;

    ButtonFactory buttonFactory;

    NetworkSection networkSection;

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

    private Stage createMenuStage(){

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        buttonFactory = new ButtonFactory();
       /* Button one= createButton("Numbers/one.png", w/3,h/5, (int)(w/2),(int)(h)/8, "1");
        Button two= createButton("Numbers/two.png", 2*w/3,h/5, (int)(w/2),(int)(h)/8, "2");
        Button three= createButton("Numbers/three.png", 3*w/3,h/5, (int)(w/2),(int)(h)/8, "3");
        Button four= createButton("Numbers/four.png", w/3,2*h/5, (int)(w/2),(int)(h)/8, "4");
        Button five = createButton("Numbers/five.png", 2*w/3,2*h/5, (int)(w/2),(int)(h)/8, "5");
        Button six= createButton("Numbers/six.png", 3*w/3,2*h/5, (int)(w/2),(int)(h)/8, "6");
        Button seven= createButton("Numbers/seven.png", w/3,3*h/5, (int)(w/2),(int)(h)/8, "7");
        Button eight = createButton("Numbers/eight.png", 2*w/3,3*h/5, (int)(w/2),(int)(h)/8, "8");
        Button nine = createButton("Numbers/nine.png", w/3,3*h/5, (int)(w/2),(int)(h)/8, "9");
        Button zero = createButton("Numbers/zero.png", 2*w/3,4*h/5, (int)(w/2),(int)(h)/8, "0");
*/

        Button one= buttonFactory.makeButton(game.getAssetManager().get("Numbers/one.png",Texture.class),game.getAssetManager().get("Numbers/one.png",Texture.class), w/4,h/6, (int)(w/3),(int)(h)/8);
        one.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "1";
                System.out.println(ip);
            }
        });
        Button two= buttonFactory.makeButton(game.getAssetManager().get("Numbers/two.png",Texture.class),game.getAssetManager().get("Numbers/two.png",Texture.class),  2*w/4,h/6, (int)(w/3),(int)(h)/8);
        two.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "2";
                System.out.println(ip);
            }
        });
        Button three= buttonFactory.makeButton(game.getAssetManager().get("Numbers/three.png",Texture.class),game.getAssetManager().get("Numbers/three.png",Texture.class), 3*w/4,h/6, (int)(w/3),(int)(h)/8);
        three.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "3";
                System.out.println(ip);
            }
        });

        Button four= buttonFactory.makeButton(game.getAssetManager().get("Numbers/four.png",Texture.class),game.getAssetManager().get("Numbers/four.png",Texture.class), w/4,2*h/6, (int)(w/3),(int)(h)/8);
        four.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "4";
                System.out.println(ip);
            }
        });
        Button five= buttonFactory.makeButton(game.getAssetManager().get("Numbers/five.png",Texture.class),game.getAssetManager().get("Numbers/five.png",Texture.class),  2*w/4,2*h/6, (int)(w/3),(int)(h)/8);
        five.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "5";
                System.out.println(ip);
            }
        });
        Button six= buttonFactory.makeButton(game.getAssetManager().get("Numbers/six.png",Texture.class),game.getAssetManager().get("Numbers/six.png",Texture.class), 3*w/4,2*h/6, (int)(w/3),(int)(h)/8);
        six.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "6";
                System.out.println(ip);
            }
        });

        Button seven= buttonFactory.makeButton(game.getAssetManager().get("Numbers/seven.png",Texture.class),game.getAssetManager().get("Numbers/seven.png",Texture.class), w/4,3*h/6, (int)(w/3),(int)(h)/8);
        seven.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "7";
                System.out.println(ip);
            }
        });
        Button eight= buttonFactory.makeButton(game.getAssetManager().get("Numbers/eight.png",Texture.class),game.getAssetManager().get("Numbers/eight.png",Texture.class),  2*w/4,3*h/6, (int)(w/3),(int)(h)/8);
        eight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "8";
                System.out.println(ip);
            }
        });
        Button nine= buttonFactory.makeButton(game.getAssetManager().get("Numbers/nine.png",Texture.class),game.getAssetManager().get("Numbers/nine.png",Texture.class), 3*w/4,3*h/6, (int)(w/3),(int)(h)/8);
        nine.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "9";
                System.out.println(ip);
            }
        });

        Button zero= buttonFactory.makeButton(game.getAssetManager().get("Numbers/zero.png",Texture.class),game.getAssetManager().get("Numbers/zero.png",Texture.class),  2*w/4,4*h/6, (int)(w/3),(int)(h)/8);
        zero.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "0";
                System.out.println(ip);
            }
        });

        Button dot= buttonFactory.makeButton(game.getAssetManager().get("Numbers/zero.png",Texture.class),game.getAssetManager().get("Numbers/zero.png",Texture.class),  w/4,4*h/6, (int)(w/3)/2,(int)(h)/8/2);
        dot.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + ".";
                System.out.println(ip);
            }
        });

        Button correct= buttonFactory.makeButton(game.getAssetManager().get("Numbers/correct.png",Texture.class),game.getAssetManager().get("Numbers/correct.png",Texture.class),  3*w/4,4*h/6, (int)(w/3)/5,(int)(h)/8/5);
        correct.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip.substring(0,ip.length()-1);
                System.out.println(ip);
            }
        });



        Button exitButton = buttonFactory.makeButton(game.getAssetManager().get("exit.png",Texture.class),game.getAssetManager().get("exit.png",Texture.class),  2*w/3,5*h/6, (int)(w/3),(int)(h)/8);
        exitButton .addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.startMainMenu();
            }
        });

        mpButton = buttonFactory.makeButton(game.getAssetManager().get("multiplayer.png",Texture.class),game.getAssetManager().get("multiplayer.png",Texture.class),  w/3,5*h/6, (int)(w/3),(int)(h)/8);
        mpButton .addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                game.startNewMut(ip);
            }
        });

        Stage stage= new Stage();

        stage.addActor(exitButton);
        stage.addActor(mpButton);
        stage.addActor(zero);
        stage.addActor(one);
        stage.addActor(two);
        stage.addActor(three);
        stage.addActor(four);
        stage.addActor(five);
        stage.addActor(six);
        stage.addActor(seven);
        stage.addActor(eight);
        stage.addActor(nine);
        stage.addActor(dot);
        stage.addActor(correct);

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
