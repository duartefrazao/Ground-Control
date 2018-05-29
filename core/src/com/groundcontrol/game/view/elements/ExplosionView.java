package com.groundcontrol.game.view.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.groundcontrol.game.GroundControl;

public class ExplosionView extends ElementView{

    private static final int numberOfStates =8;

    private static final float FRAME_TIME = 0.1f;

    private boolean stop = false;

    private float stateTime = 0;

    private Animation<TextureRegion> explosionAnimation;

    public ExplosionView(GroundControl game) {
        super(game);
    }

    private Animation<TextureRegion> createExplosionAnimation(GroundControl game) {

        TextureRegion[] frames = new TextureRegion[numberOfStates];

        for(int i = 1; i <= 8; i++){
                Texture tFrame = game.getAssetManager().get("Explosion/explosion[" + i + "].png");
                TextureRegion rFrame = new TextureRegion(tFrame);
                frames[i-1] = rFrame;
        }

        return new Animation<TextureRegion>(FRAME_TIME, frames);

    }


    public Sprite createSprite(GroundControl game) {
        Texture texture = game.getAssetManager().get("Explosion/explosion[3].png");
        this.explosionAnimation = createExplosionAnimation(game);
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
