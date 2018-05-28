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


import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

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
        ButtonFactory buttonFactory = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();


        Button first= buttonFactory.makeButton(gv.game.getAssetManager().get("first.png",Texture.class),gv.game.getAssetManager().get("first.png",Texture.class),  2*w/5,h/6, (int)(w/2),(int)(h)/6);
        first.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.connectServerSection.transition();

            }
        });

        Button second=buttonFactory.makeButton(gv.game.getAssetManager().get("second.png",Texture.class),gv.game.getAssetManager().get("second.png",Texture.class),2*w/5,3*h/6, (int)(w/2),(int)(h)/6);
        second.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.connectClientSection.transition();
            }
        });

        Button exitButton=buttonFactory.makeButton(gv.game.getAssetManager().get("exit.png",Texture.class),gv.game.getAssetManager().get("exit.png",Texture.class),2*w/5,5*h/6, (int)(w/2),(int)(h)/6);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.menuSection.transition();
            }
        });

        Stage stage= new Stage();

        stage.addActor(first);
        stage.addActor(second);
        stage.addActor(exitButton);

        return stage;
    }

    @Override
    public void loadAssets() {
        gv.game.getAssetManager().load("background.png", Texture.class);
        gv.game.getAssetManager().load("exit.png", Texture.class);
        gv.game.getAssetManager().load("first.png", Texture.class);
        gv.game.getAssetManager().load("second.png", Texture.class);

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
