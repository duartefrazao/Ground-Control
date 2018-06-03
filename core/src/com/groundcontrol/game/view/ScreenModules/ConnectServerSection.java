package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.network.Server;
import com.groundcontrol.game.view.network.ServerConnector;

import java.io.IOException;
import java.net.Socket;

/**
 * Section responsible for server  (first player) connection view
 */
public class ConnectServerSection implements Section{

    private final GameView gv;
    private final GroundControl game;
    private final Stage stage;

    BitmapFont font=new BitmapFont();

    private Server server;
    private boolean serverUp;
    protected Color whiteColor = new Color(Color.WHITE);
    Label ipLabel;

    String message;
    ServerConnector connector;

    public ConnectServerSection(GameView gameView) {

        this.gv=gameView;
        this.game = gv.game;
        loadAssets();

        server= new Server();


        stage = createStage();
    }

    @Override
    public void transition() {

        this.serverUp=false;
        this.message="";
        connector= new ServerConnector(server);
        connector.start();

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

        if(!serverIP.equals("")) {
            message=""+ serverIP;
            this.ipLabel.setText(message);
        }else{
            this.ipLabel.setText("Couldn't get device ip");
        }
    }

    @Override
    public void display(float delta) {
    }


    @Override
    public Stage createStage() {

        Stage stage= new Stage();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        ButtonFactory buttonFactory = new ButtonFactory();

        Button exitButton=buttonFactory.makeButton(gv.game.getAssetManager().get("exit.png",Texture.class),gv.game.getAssetManager().get("exit.png",Texture.class),2*w/4,2*h/5, (int)(w/2),(int)(h)/10);
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

                gv.menuSection.transition();
            }
        });

        Image background = new Image(new Texture(Gdx.files.internal("IPAdress_Insert.png")));

        background.setBounds(Gdx.graphics.getWidth(),0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        background.setPosition(0,0 );







        stage.addActor(background);
        stage.addActor(createIpTable());
        stage.addActor(exitButton);

        return stage;
    }

    private Table createIpTable() {
        Table table = new Table();
        table.center();

        font = new BitmapFont();
        font.getData().scale(3);
        ipLabel = new Label(message, new Label.LabelStyle(font, whiteColor));
        table.add(ipLabel);
        table.padBottom(Gdx.graphics.getHeight()/3);
        table.setFillParent(true);
        return table;
    }

    @Override
    public void loadAssets() {
    }

    @Override
    public void drawStages(float delta) {

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void drawBackground() {
    }
}
