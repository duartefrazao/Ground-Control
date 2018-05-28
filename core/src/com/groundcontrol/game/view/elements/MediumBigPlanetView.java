package com.groundcontrol.game.view.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.groundcontrol.game.GroundControl;

public class MediumBigPlanetView extends ElementView{

    public MediumBigPlanetView(GroundControl game){
        super(game);
    }

    public Sprite createSprite(GroundControl game){
        Texture texture = game.getAssetManager().get("MediumBigPlanet.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

}
