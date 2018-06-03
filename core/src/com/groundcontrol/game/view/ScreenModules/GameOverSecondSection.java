package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.UiFactory.ButtonFactory;
import com.groundcontrol.game.view.elements.ViewFactory;
import com.groundcontrol.game.view.network.Client;


public class GameOverSecondSection extends GameOverSection{

    private Client client;

    public GameOverSecondSection(GameView gameView) {
        super(gameView);
    }

    @Override
    public Stage createStage() {

        Stage stage= new Stage();

        Image backgroundSec = new Image(new Texture(Gdx.files.internal("backgroundSecond.png")));

        backgroundSec.setBounds(Gdx.graphics.getWidth(),0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        backgroundSec.setPosition(0,0 );
        stage.addActor(backgroundSec);


        Image background = new Image(new Texture(Gdx.files.internal("gameOver.png")));

        background.setBounds(Gdx.graphics.getWidth(),0,Gdx.graphics.getWidth() -Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/3);
        background.setPosition(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/6 );
        stage.addActor(background);


        ButtonFactory butFac = new ButtonFactory();

        float w=Gdx.graphics.getWidth(), h=Gdx.graphics.getHeight();



        Button exitButton=butFac.makeButton(gv.game.getAssetManager().get("exitMM.png",Texture.class),gv.game.getAssetManager().get("exitMM.png",Texture.class),w/2,2.8f*h/6, (int)(w/2),(int)(h)/8);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gv.menuSection.transition();
                client.stop();
            }
        });

        stage.addActor(exitButton);

        return stage;
    }

    @Override
    public void transition() {

        ViewFactory.updatePause(gv.gameModel.getPlayer(), gv, true);

        ViewFactory.updatePauseElements(gv.gameModel.getExplosions(), gv, true);

        ViewFactory.updatePauseElements(gv.gameModel.getComets(), gv, true);

        Gdx.input.setInputProcessor(stage);

        gv.currentSection= gv.gameOverSecondSection;
    }

    @Override
    public void drawBackground() {
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
