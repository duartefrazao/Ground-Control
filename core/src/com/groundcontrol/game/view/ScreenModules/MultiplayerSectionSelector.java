package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.network.Server;


import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

/**
 * Section responsible for player selection
 */
public class MultiplayerSectionSelector implements Section{

    private final GameView gv;
    private final GroundControl game;
    private final Stage stage;


    private Server server;
    private boolean serverUp;


    public MultiplayerSectionSelector(GameView gameView) {

        this.gv=gameView;
        this.game = gv.game;
        loadAssets();

        stage = createStage();

    }

    @Override
    public void transition() {


        Gdx.input.setInputProcessor(stage);
        gv.currentSection = gv.multiplayerSectionSelector;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void display(float delta) {
    }

    @Override
    public Stage createStage() {

        Stage stage= new Stage();

        Image background = new Image(new Texture(Gdx.files.internal("background.png")));

        background.setBounds(Gdx.graphics.getWidth(),0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        background.setPosition(0,0 );
        stage.addActor(background);


        ButtonFactory buttonFactory = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();


        Button first= buttonFactory.makeButton(gv.game.getAssetManager().get("first.png",Texture.class),gv.game.getAssetManager().get("first.png",Texture.class), 2*w/4,5*h/6, (int)(w/2),(int)(h)/6);
        first.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.connectServerSection.transition();

            }
        });

        Button second=buttonFactory.makeButton(gv.game.getAssetManager().get("second.png",Texture.class),gv.game.getAssetManager().get("second.png",Texture.class),2* w/4,3*h/6, (int)(w/2),(int)(h)/6);
        second.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.connectClientSection.transition();
            }
        });

        Button exitButton=buttonFactory.makeButton(gv.game.getAssetManager().get("exit.png",Texture.class),gv.game.getAssetManager().get("exit.png",Texture.class),2*w/4,h/6, (int)(w/2),(int)(h)/6);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.menuSection.transition();
            }
        });


        stage.addActor(first);
        stage.addActor(second);
        stage.addActor(exitButton);

        return stage;
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
