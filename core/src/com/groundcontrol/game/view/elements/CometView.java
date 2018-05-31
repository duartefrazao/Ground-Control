package com.groundcontrol.game.view.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.groundcontrol.game.GroundControl;

public class CometView extends ElementView {

    private static final int numberOfStates =6;

    private static final float FRAME_TIME = 0.1f;

    private float stateTime = 0;

    private Animation<TextureRegion> burningAnimation;

    public CometView(GroundControl game) {
        super(game);
    }

    private Animation<TextureRegion> createBurningAnimation(GroundControl game) {

        Texture burningAnimation = game.getAssetManager().get("Comet/Comet_Array.png");
        TextureRegion[][] burnRegion = TextureRegion.split(burningAnimation, burningAnimation.getWidth() / numberOfStates, burningAnimation.getHeight());

        TextureRegion[] frames = new TextureRegion[numberOfStates];

        System.arraycopy(burnRegion[0], 0, frames, 0, numberOfStates);

        return new Animation<TextureRegion>(FRAME_TIME, frames);

    }


    public Sprite createSprite(GroundControl game) {
        Texture texture = game.getAssetManager().get("Comet/comet.png");
        this.burningAnimation = createBurningAnimation(game);
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(SpriteBatch batch) {

        if (!stopFrame)
            this.stateTime += Gdx.graphics.getDeltaTime();


        sprite.setRegion(burningAnimation.getKeyFrame(stateTime, true));


        sprite.draw(batch);

    }



}
