package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.network.Server;
import com.groundcontrol.game.view.network.ServerConnector;

import java.io.IOException;
import java.net.Socket;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class ConnectServerSection implements Section{

    private final GameView gv;
    private final GroundControl game;
    private final Stage stage;

    BitmapFont font=new BitmapFont();

    private Server server;
    private boolean serverUp;
    Button connect;

    String message;
    ServerConnector connector;

    public ConnectServerSection(GameView gameView) {

        this.gv=gameView;
        this.game = gv.game;
        loadAssets();

        server= new Server();

        stage = createStage();
        font.getData().scale(10);

    }

    @Override
    public void transition() {

        this.serverUp=false;
        this.message="";

        Gdx.input.setInputProcessor(stage);
        gv.currentSection = gv.connectServerSection;
    }

    @Override
    public void update(float delta) {

        if(server.isAlive()){
            gv.multiplayerServer.setServer(server);
            gv.multiplayerServer.transition();
        }

        Socket s = null;
        String serverIP="";
        try {
            s = new Socket("google.pt", 80);
            serverIP = s.getLocalAddress().getHostAddress();

            s.close();
        } catch (IOException e) {
            message= "Couldn't get device ip";
            e.printStackTrace();
        }

        if(!serverIP.equals(""))
            message="Press start and enter the following ip on the other device" + serverIP;
    }

    @Override
    public void display(float delta) {
        // drawBackground();
        font.draw(game.getBatch(), message, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3);

    }

    public String hexToDec(String hex){
        String result="";

        for(int i = 0;i < hex.length();i+=2){
            result+=Integer.parseInt(hex.substring(i,i+2),16)+".";
        }
        return result.substring(0,result.length()-1);
    }


    @Override
    public Stage createStage() {

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        ButtonFactory buttonFactory = new ButtonFactory();

        connect= buttonFactory.makeButton(gv.game.getAssetManager().get("connect.png",Texture.class),gv.game.getAssetManager().get("connect.png",Texture.class),  3*w/5,3*h/5, (int)(w/3),(int)(h)/10);
        connect.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //substituteButton();
                connector= new ServerConnector(server);
                connector.start();
            }
        });

        Button exitButton=buttonFactory.makeButton(gv.game.getAssetManager().get("exit.png",Texture.class),gv.game.getAssetManager().get("exit.png",Texture.class),2*w/5,3*h/5, (int)(w/6),(int)(h)/10);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(server.serverSocket!=null){
                    try {
                        server.serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    server.serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gv.menuSection.transition();
            }
        });

        Stage stage= new Stage();

        stage.addActor(connect);
        stage.addActor(exitButton);

        return stage;
    }

    @Override
    public void loadAssets() {
        gv.game.getAssetManager().load("background.png", Texture.class);
        gv.game.getAssetManager().load("exit.png", Texture.class);
        gv.game.getAssetManager().load("connect.png", Texture.class);

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
