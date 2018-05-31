package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.elements.ViewFactory;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class PauseSection implements Section{

    protected final GameView gv;
    protected final GroundControl game;
    protected final Stage stage;


    public PauseSection (GameView gameView) {
        this.gv=gameView;
        this.game = gameView.game;

        loadAssets();

        stage = createStage();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void display(float delta) {
        drawBackground();

        ViewFactory.drawElement(gv.gameModel.getPlayer(), gv);

        ViewFactory.drawAllElements(gv.gameModel.getPlanets(), gv);

        ViewFactory.drawAllElements(gv.gameModel.getComets(), gv);
      /*  List<ExplosionModel> explosions = gv.gameModel.getExplosions();
        for(ExplosionModel e : explosions){
            ElementView view = ViewFactory.makeView(gv.game, e);
            view.draw(gv.game.getBatch());
        }*/
    }


    @Override
    public void transition() {

        ViewFactory.updatePause(gv.gameModel.getPlayer(), gv, true);

        ViewFactory.updatePauseElements(gv.gameModel.getExplosions(), gv, true);

        ViewFactory.updatePauseElements(gv.gameModel.getComets(), gv, true);

        Gdx.input.setInputProcessor(stage);

        gv.currentSection= gv.pauseSection;
    }


    @Override
    public Stage createStage() {
        ButtonFactory butFac = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();

        Button resumeButton= butFac.makeButton( gv.game.getAssetManager().get("resume.png",Texture.class),gv.game.getAssetManager().get("resume.png",Texture.class),w/2,2*h/3, (int)(w/2),(int)(h)/4);
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.gameSection.transition();
            }
        });

        Button exitButton=butFac.makeButton(gv.game.getAssetManager().get("exit.png",Texture.class),gv.game.getAssetManager().get("exit.png",Texture.class),w/2,h/3, (int)(w/2),(int)(h)/4);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.gameModel=new GameModel();
                gv.gameController = new GameController(gv.gameModel);
                gv.menuSection.transition();
            }
        });

        Stage stage= new Stage();

        stage.addActor(resumeButton);
        stage.addActor(exitButton);

        return stage;
    }

    @Override
    public void loadAssets() {
        gv.game.getAssetManager().load("resume.png", Texture.class);
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
