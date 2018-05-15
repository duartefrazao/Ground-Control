package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;
import com.groundcontrol.game.view.elements.ElementView;
import com.groundcontrol.game.view.elements.ViewFactory;

import java.util.List;

import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class GameView extends ScreenAdapter{

    private static boolean rightButtonClicked=false;
    private static boolean leftButtonClicked = false;

    public enum StateInput { RIGHT_BUTTON, LEFT_BUTTON, SPACE_BUTTON, RIGHT_LEFT_BUTTONS}

    private final GroundControl game;

    public final static float PIXEL_TO_METER = 0.009f;

    private static final float VIEWPORT_WIDTH = 35;

    private final OrthographicCamera camera;

    private GameController gameController;

    private GameModel gameModel;

    private static final boolean DEBUG_PHYSICS = false;

    private Box2DDebugRenderer debugRenderer;

    private Matrix4 debugCamera;

    Stage stage;

    ImageButton upButton;
    ImageButton leftButton;
    ImageButton rightButton;

    Table uiTable;

    public GameView(GroundControl game, GameModel gameModel, GameController gameController){

        this.game = game;
        loadAssets();

        this.gameModel = gameModel;

        this.gameController = gameController;

        stage = new Stage();


        upButton= addUIComponent( "Buttons/up.png");
        leftButton = addUIComponent("Buttons/left.png");
        rightButton = addUIComponent("Buttons/right.png");

        uiTable = createUiTable();

        stage.addActor(uiTable);

        Gdx.input.setInputProcessor(stage);

        camera = createCamera();
    }

    private Table createUiTable(){
        upButton= addUIComponent( "Buttons/up.png");
        upButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameController.handleInput(StateInput.SPACE_BUTTON);
            }
        });

        leftButton = addUIComponent("Buttons/left.png");
        leftButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                //gameController.handleInput(StateInput.RIGHT_BUTTON);
                leftButtonClicked=true;
                return true;
            }

            @Override
            public void touchUp (InputEvent e, float x, float y, int pointer, int button){
                //gameController.handleInput(StateInput.RIGHT_BUTTON);
                leftButtonClicked=false;
            }
        });

        rightButton = addUIComponent("Buttons/right.png");
        rightButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent e, float x, float y, int pointer, int button){
                //gameController.handleInput(StateInput.RIGHT_BUTTON);
                rightButtonClicked=true;
                return true;
            }

            @Override
            public void touchUp (InputEvent e, float x, float y, int pointer, int button){
                //gameController.handleInput(StateInput.RIGHT_BUTTON);
                rightButtonClicked=false;
            }
        });


        Table table=new Table();

        table.bottom();
        table.add(leftButton).height(100).width(100);
        table.add(rightButton).height(100).width(100);
        table.add(upButton).height(100).width(100);
        table.setFillParent(true);


        return table;
    }

    private ImageButton addUIComponent(String fileName){

        Texture buttonTexture=game.getAssetManager().get(fileName,Texture.class);
        TextureRegion buttonTextureReg =new TextureRegion(buttonTexture);
        TextureRegionDrawable buttonTextureRegionDraw = new TextureRegionDrawable(buttonTextureReg);
        return (new ImageButton(buttonTextureRegionDraw));
    }


    private OrthographicCamera createCamera(){

        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;

    }

    private void loadAssets(){
        this.game.getAssetManager().load("planet.png", Texture.class);
        this.game.getAssetManager().load("player.png", Texture.class);
        this.game.getAssetManager().load("big_planet.png", Texture.class);
        this.game.getAssetManager().load("runningSheet.png", Texture.class);
        this.game.getAssetManager().load("background.png", Texture.class);
        this.game.getAssetManager().load("Buttons/up.png",Texture.class);
        this.game.getAssetManager().load("Buttons/left.png",Texture.class);
        this.game.getAssetManager().load("Buttons/right.png",Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta){

        handleInputs(delta);

        this.gameController.update(delta);

        //camera.position.set(GameModel.getInstance().getPlayer().getX()/PIXEL_TO_METER,GameModel.getInstance().getPlayer().getY()/PIXEL_TO_METER,0);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackGround();
        drawElements();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(this.gameController.getWorld(), debugCamera);
        }

        stage.draw();

    }

    private void handleInputs(float delta){

        if(rightButtonClicked) gameController.handleInput(StateInput.RIGHT_BUTTON);
        if(leftButtonClicked) gameController.handleInput(StateInput.LEFT_BUTTON);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            this.gameController.handleInput(StateInput.LEFT_BUTTON);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            this.gameController.handleInput(StateInput.RIGHT_BUTTON);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            this.gameController.handleInput(StateInput.SPACE_BUTTON);
        }



        boolean accAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        if(accAvailable){

            float vx = Gdx.input.getAccelerometerX();
            float vy = Gdx.input.getAccelerometerY();

            this.gameController.setPlanetForce(-vx, -vy);

        }


    }


    public void drawElements(){


        PlayerModel player = this.gameModel.getPlayer();
        ElementView viewPlayer = ViewFactory.makeView(game,player);
        viewPlayer.update(player);
        viewPlayer.draw(game.getBatch());

        List<PlanetModel> planets = this.gameModel.getPlanets();
        for(PlanetModel p : planets){
            ElementView view = ViewFactory.makeView(game,p);
            view.update(p);
            view.draw(game.getBatch());
        }
    }

    public void drawBackGround(){
        Texture background = game.getAssetManager().get("background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(ARENA_WIDTH / PIXEL_TO_METER), (int) (ARENA_HEIGHT / PIXEL_TO_METER));

    }



}
