package com.groundcontrol.game.view.elements;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.controller.state.IdleState;
import com.groundcontrol.game.controller.state.PlayerState;

public class PlayerView extends ElementView {

    private static final int numberOfRunningStates = 6;

    private static final float FRAME_TIME = 0.1f;

    private float stateTime = 0;

    private Animation<TextureRegion> runningAnimation;

    private Animation<TextureRegion> walkingAnimation;

    private Animation<TextureRegion> jumpingAnimation;

    private Animation<TextureRegion> createRunningAnimation(GroundControl game){

        Texture thrustTexture = game.getAssetManager().get("runningSheet.png");
        TextureRegion[][] thrustRegion = TextureRegion.split(thrustTexture, thrustTexture.getWidth() / numberOfRunningStates, thrustTexture.getHeight());

        TextureRegion[] frames = new TextureRegion[6];

        System.arraycopy(thrustRegion[0], 0, frames, 0, 6);

        return new Animation<TextureRegion>(FRAME_TIME, frames);

    }

    public PlayerView(GroundControl game){
        super(game);
    }


    public Sprite createSprite(GroundControl game) {
        Texture texture = game.getAssetManager().get("player.png");
        this.runningAnimation = createRunningAnimation(game);
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void update(ElementModel model){
        super.update(model);
    }

    @Override
    public void draw(SpriteBatch batch){

        this.stateTime += Gdx.graphics.getDeltaTime();

        sprite.setRegion(runningAnimation.getKeyFrame(stateTime, true));

        sprite.draw(batch);

    }
}
