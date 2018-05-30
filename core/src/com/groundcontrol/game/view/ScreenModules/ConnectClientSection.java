package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class ConnectClientSection implements Section{

    private final GameView gv;
    private final GroundControl game;
    private final Stage stage;

    BitmapFont font=new BitmapFont();

    String ip = "";
    String message;

    public ConnectClientSection(GameView gameView) {

        this.gv=gameView;
        this.game = gv.game;
        loadAssets();

        font.getData().scale(5);
        stage = createStage();

    }

    @Override
    public void update(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            ip = ip + "0";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            ip = ip + "1";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            ip = ip + "2";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            ip = ip + "3";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            ip = ip + "4";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            ip = ip + "5";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            ip = ip + "6";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
            ip = ip + "7";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            ip = ip + "8";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            ip = ip + "9";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) {
            ip = ip + ".";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            ip = ip.substring(0,ip.length()-1);
        }



    }

    @Override
    public void display(float delta) {
       // drawBackground();

        font.draw(gv.game.getBatch(),"--->" + ip,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3);

    }


    @Override
    public void transition() {

        Gdx.input.setInputProcessor(stage);
        gv.currentSection = gv.connectClientSection;
    }



    @Override
    public Stage createStage() {
        ButtonFactory buttonFactory = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        Button one= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/one.png",Texture.class),gv.game.getAssetManager().get("Numbers/one.png",Texture.class), w/4,h/6, (int)(w/3),(int)(h)/8);
        one.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "1";
                System.out.println(ip);
            }
        });
        Button two= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/two.png",Texture.class),gv.game.getAssetManager().get("Numbers/two.png",Texture.class),  2*w/4,h/6, (int)(w/3),(int)(h)/8);
        two.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "2";
                System.out.println(ip);
            }
        });
        Button three= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/three.png",Texture.class),gv.game.getAssetManager().get("Numbers/three.png",Texture.class), 3*w/4,h/6, (int)(w/3),(int)(h)/8);
        three.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "3";
                System.out.println(ip);
            }
        });

        Button four= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/four.png",Texture.class),gv.game.getAssetManager().get("Numbers/four.png",Texture.class), w/4,2*h/6, (int)(w/3),(int)(h)/8);
        four.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "4";
                System.out.println(ip);
            }
        });
        Button five= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/five.png",Texture.class),gv.game.getAssetManager().get("Numbers/five.png",Texture.class),  2*w/4,2*h/6, (int)(w/3),(int)(h)/8);
        five.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "5";
                System.out.println(ip);
            }
        });
        Button six= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/six.png",Texture.class),gv.game.getAssetManager().get("Numbers/six.png",Texture.class), 3*w/4,2*h/6, (int)(w/3),(int)(h)/8);
        six.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "6";
                System.out.println(ip);
            }
        });

        Button seven= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/seven.png",Texture.class),gv.game.getAssetManager().get("Numbers/seven.png",Texture.class), w/4,3*h/6, (int)(w/3),(int)(h)/8);
        seven.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "7";
                System.out.println(ip);
            }
        });
        Button eight= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/eight.png",Texture.class),gv.game.getAssetManager().get("Numbers/eight.png",Texture.class),  2*w/4,3*h/6, (int)(w/3),(int)(h)/8);
        eight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "8";
                System.out.println(ip);
            }
        });
        Button nine= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/nine.png",Texture.class),gv.game.getAssetManager().get("Numbers/nine.png",Texture.class), 3*w/4,3*h/6, (int)(w/3),(int)(h)/8);
        nine.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "9";
                System.out.println(ip);
            }
        });

        Button zero= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/zero.png",Texture.class),gv.game.getAssetManager().get("Numbers/zero.png",Texture.class),  2*w/4,4*h/6, (int)(w/3),(int)(h)/8);
        zero.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + "0";
                System.out.println(ip);
            }
        });

        Button dot= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/zero.png",Texture.class),gv.game.getAssetManager().get("Numbers/zero.png",Texture.class),  w/4,4*h/6, (int)(w/3)/2,(int)(h)/8/2);
        dot.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip + ".";
                System.out.println(ip);
            }
        });

        Button correct= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/correct.png",Texture.class),gv.game.getAssetManager().get("Numbers/correct.png",Texture.class),  3*w/4,4*h/6, (int)(w/3)/5,(int)(h)/8/5);
        correct.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip = ip.substring(0,ip.length()-1);
                System.out.println(ip);
            }
        });

        Button connectButton= buttonFactory.makeButton( gv.game.getAssetManager().get("connect.png",Texture.class),gv.game.getAssetManager().get("connect.png",Texture.class),3*w/5,5*h/6, (int)(w/6),(int)(h)/6);
        connectButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(gv.multiplayerClient.connectClient(ip))
                    gv.multiplayerClient.transition();
                else message= "Error connecting to server";
            }
        });

        Button exitButton=buttonFactory.makeButton(gv.game.getAssetManager().get("exit.png",Texture.class),gv.game.getAssetManager().get("exit.png",Texture.class),2*w/5,5*h/6, (int)(w/6),(int)(h)/6);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
               gv.menuSection.transition();
            }
        });

        Stage stage= new Stage();

        stage.addActor(connectButton);
        stage.addActor(exitButton);
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

    @Override
    public void loadAssets() {
        gv.game.getAssetManager().load("background.png", Texture.class);
        gv.game.getAssetManager().load("connect.png", Texture.class);
        gv.game.getAssetManager().load("exit.png", Texture.class);
        gv.game.getAssetManager().load("start.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/one.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/two.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/three.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/four.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/five.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/six.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/seven.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/eight.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/nine.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/zero.png", Texture.class);
        gv.game.getAssetManager().load("Numbers/correct.png", Texture.class);

        gv.game.getAssetManager().finishLoading();
    }

    @Override
    public void drawStages(float delta) {

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void drawBackground() {
        Texture background = game.getAssetManager().get("background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(ARENA_WIDTH / gv.PIXEL_TO_METER), (int) (ARENA_HEIGHT / gv.PIXEL_TO_METER));


    }
}
