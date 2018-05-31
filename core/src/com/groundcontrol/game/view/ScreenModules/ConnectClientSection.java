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

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.*;
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

        int[] keys = {NUM_0,NUM_1,NUM_2,NUM_3,NUM_4,NUM_5,NUM_6,NUM_7,NUM_8,NUM_9};
        for(int i = 0; i < keys.length;i++){
            if(Gdx.input.isKeyJustPressed(keys[i]))
                ip+=Integer.toString(keys[i]-7);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) {
            ip += ".";
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            ip = ip.substring(0,ip.length()-1);
        }



    }

    @Override
    public void display(float delta) {
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

        Stage stage= new Stage();

        final String nums[] = {"zero","one","two","three","four","five","six","seven","eight","nine"};
        ArrayList<Button> buts = new ArrayList<Button>();


        for(int i = 0; i < nums.length; i++){
            buts.add(buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/" + nums[i]+".png",Texture.class),gv.game.getAssetManager().get("Numbers/"+nums[i]+".png",Texture.class), w/4,h/6, (int)(w/3),(int)(h)/8));
            final int ind = i;
            buts.get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    ip = ip + nums[ind];
                }
            });
            stage.addActor(buts.get(i));
        }

        Button dot= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/zero.png",Texture.class),gv.game.getAssetManager().get("Numbers/zero.png",Texture.class),  w/4,4*h/6, (int)(w/3)/2,(int)(h)/8/2);
        dot.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ip += ".";
            }
        });

        Button correct= buttonFactory.makeButton(gv.game.getAssetManager().get("Numbers/correct.png",Texture.class),gv.game.getAssetManager().get("Numbers/correct.png",Texture.class),  3*w/4,4*h/6, (int)(w/3)/5,(int)(h)/8/5);
        correct.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(ip.length()>0)
                    ip = ip.substring(0,ip.length()-1);
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


        stage.addActor(connectButton);
        stage.addActor(exitButton);
        stage.addActor(dot);
        stage.addActor(correct);

        return stage;
    }

    @Override
    public void loadAssets() {
        String nums[] = {"zero","one","two","three","four","five","six","seven","eight","nine","correct"};

        for(int i = 0; i < nums.length; i++){
            gv.game.getAssetManager().load("Numbers/" + nums[i]+ ".png", Texture.class);
        }

        gv.game.getAssetManager().load("background.png", Texture.class);
        gv.game.getAssetManager().load("connect.png", Texture.class);
        gv.game.getAssetManager().load("exit.png", Texture.class);
        gv.game.getAssetManager().load("start.png", Texture.class);

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
