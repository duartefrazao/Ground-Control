package com.groundcontrol.game.view.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.groundcontrol.game.GroundControl;

public class ExplosionView extends ElementView{

    private static final int numberOfStates =13;

    private static final float FRAME_TIME = 0.1f;

    private boolean stop = false;

    private float stateTime = 0;

    private Animation<TextureRegion> explosionAnimation;

    public ExplosionView(GroundControl game) {
        super(game);
    }

    private Animation<TextureRegion> createRunningAnimation(GroundControl game) {

        Texture exploAnimation = game.getAssetManager().get("explosionSheet.png");
        TextureRegion[][] exploRegion = TextureRegion.split(exploAnimation, exploAnimation.getWidth() / numberOfStates, exploAnimation.getHeight());

        TextureRegion[] frames = new TextureRegion[numberOfStates];

        System.arraycopy(exploRegion[0], 0, frames, 0, numberOfStates);

        return new Animation<TextureRegion>(FRAME_TIME, frames);

    }


    public Sprite createSprite(GroundControl game) {
        Texture texture = game.getAssetManager().get("explosionSheet.png");
        this.explosionAnimation = createRunningAnimation(game);
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

    public void setStopped() {
        this.stop = true;
    }

    public void removeStopped() {
        this.stop = false;
    }


    @Override
    public void draw(SpriteBatch batch) {

        if (!stop)
            this.stateTime += Gdx.graphics.getDeltaTime();

        sprite.setRegion(explosionAnimation.getKeyFrame(stateTime, false));


        sprite.draw(batch);

    }
}
