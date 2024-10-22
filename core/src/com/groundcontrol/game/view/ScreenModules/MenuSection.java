package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;

/**
 * Section responsible for menu view
 */
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
        stage = createStage();
    }


    @Override
    public void update(float delta) {

    }

    @Override
    public void display(float delta) {
    }

    @Override
    public void transition() {
        gv.gameModel=new GameModel();
        gv.gameController = new GameController(gv.gameModel);
        Gdx.input.setInputProcessor(stage);
        gv.currentSection=gv.menuSection;
    }

    @Override
    public Stage createStage() {
        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        ButtonFactory buttonFactory = new ButtonFactory();
        exitButton= buttonFactory.makeButton(game.getAssetManager().get("exit.png",Texture.class),game.getAssetManager().get("exit.png",Texture.class), w/2,3*h/20, (int)(w/2),(int)(h)/8);

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
               exitGame();
            }
        });
        gameButton = buttonFactory.makeButton(game.getAssetManager().get("start.png",Texture.class),game.getAssetManager().get("start.png",Texture.class),  w/2,11*h/20, (int)(w/2),(int)(h)/8);
        gameButton .addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(gv.gameSection ==null)
                    gv.gameSection = new GameSection(gv);
                gv.gameSection.transition();

            }
        });

        mpButton = buttonFactory.makeButton(game.getAssetManager().get("multiplayer.png",Texture.class),game.getAssetManager().get("multiplayer.png",Texture.class),  w/2,7*h/20, (int)(w/2),(int)(h)/8);
        mpButton .addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(gv.multiplayerSectionSelector ==null)
                    gv.multiplayerSectionSelector = new MultiplayerSectionSelector(gv);
                gv.multiplayerSectionSelector.transition();
            }
        });

        Image background = new Image(new Texture(Gdx.files.internal("menu_background.png")));

        background.setBounds(Gdx.graphics.getWidth(),0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        background.setPosition(0,0 );

        Stage stage= new Stage();

        stage.addActor(background);
        stage.addActor(exitButton);
        stage.addActor(gameButton);
        stage.addActor(mpButton);

        return stage;
    }

    private void exitGame() {
        Gdx.app.exit();
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
