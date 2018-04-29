package com.groundcontrol.game.view.elements;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.model.elements.ElementModel;
import static com.groundcontrol.game.view.GameView.PIXEL_TO_METER;

public abstract class ElementView {

    Sprite sprite;


    ElementView(GroundControl game){
        sprite = createSprite(game);
    }


    public abstract Sprite createSprite(GroundControl game);

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void update(ElementModel model){
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        sprite.setRotation((float) Math.toDegrees(model.getRotation()));
    }


}
