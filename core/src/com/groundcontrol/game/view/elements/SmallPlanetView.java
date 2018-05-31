package com.groundcontrol.game.view.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.groundcontrol.game.GroundControl;

/**
 * Creates a view for a small planet, loading and creating it's sprite
 */
public class SmallPlanetView extends ElementView{

    /**
     * Default constructor
     * @param game the game to which the view will be associated
     */
    public SmallPlanetView(GroundControl game){
        super(game);
    }

    @Override
    public Sprite createSprite(GroundControl game){
        Texture texture = game.getAssetManager().get("Planets/SmallPlanet.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
