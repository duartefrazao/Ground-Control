package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.NetworkScreen;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class MenuSection implements Section{

    private Button exitButton;
    private Button gameButton;
    private Button mpButton;
    private GameView gv;

    private final GroundControl game;

    Stage stage;

    public MenuSection(GameView gameView){
        this.game = gameView.game;
        this.gv=gameView;
        loadAssets();

        stage = createStage();
    }

    public String decToHex(String dec){
        String result="",tmp="";
        String spl[] = dec.split("\\.");
        for(String s:spl) {

            tmp=""+Integer.toHexString(Integer.parseInt(String.valueOf(s)));
            if(tmp.length()==1)tmp="0"+tmp;
            result+=tmp;
        }
        return result;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void display(float delta) {
        drawBackground();
    }

    @Override
    public void transition() {
        Gdx.input.setInputProcessor(stage);
        gv.currentSection=gv.menuSection;
    }

    @Override
    public Stage createStage() {
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
                gv.gameSection.transition();

            }
        });

        mpButton = buttonFactory.makeButton(game.getAssetManager().get("multiplayer.png",Texture.class),game.getAssetManager().get("multiplayer.png",Texture.class),  w/2,2*h/4, (int)(w/2),(int)(h)/8);
        mpButton .addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.multiplayerSectionSelector.transition();
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

    @Override
    public void loadAssets() {
        this.game.getAssetManager().load("background.png", Texture.class);
        this.game.getAssetManager().load("exit.png", Texture.class);
        this.game.getAssetManager().load("start.png", Texture.class);
        this.game.getAssetManager().load("multiplayer.png", Texture.class);
        this.game.getAssetManager().load("player.png", Texture.class);
        this.game.getAssetManager().finishLoading();
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
