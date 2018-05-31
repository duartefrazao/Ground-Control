package com.groundcontrol.game.view.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.groundcontrol.game.GroundControl;



/**
 * Creates a view for a medium planet, loading and creating it's sprite
 */
public class MediumPlanetView extends  ElementView{

    /**
     * Default constructor
     * @param game the game to which the view will be associated
     */
    public MediumPlanetView(GroundControl game){
        super(game);
    }

    @Override
    public Sprite createSprite(GroundControl game){
        Texture texture = game.getAssetManager().get("Planets/MediumPlanet.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
