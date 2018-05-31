package com.groundcontrol.game.view.elements;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.model.elements.ElementModel;
import static com.groundcontrol.game.view.GameView.PIXEL_TO_METER;

public abstract class ElementView {

    Sprite sprite;

    protected boolean stopFrame;

    /**
     * Stops or resumes the animation of a certain view
     * @param pause
     */
    public void setStopFrame(boolean pause){
        this.stopFrame = pause;
    }

    /**
     * Default constructor
     * @param game the game
     */
    ElementView(GroundControl game){
        sprite = createSprite(game);
    }

    /**
     * Creates the dedicated sprite
     * @param game the game
     * @return the sprite
     */
    public abstract Sprite createSprite(GroundControl game);

    /**
     * Draws the view sprite into the game sprite batch
     * @param batch
     */
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    /**
     * Updates the view sprite to the coordinates of the model at each step
     * @param model the model containing the information
     */
    public void update(ElementModel model){
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        sprite.setRotation((float) Math.toDegrees(model.getRotation()));
    }


}
