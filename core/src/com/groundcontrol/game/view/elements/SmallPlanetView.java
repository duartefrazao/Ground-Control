package com.groundcontrol.game.view.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.groundcontrol.game.GroundControl;

public class SmallPlanetView extends ElementView{

    public SmallPlanetView(GroundControl game){
        super(game);
    }

    public Sprite createSprite(GroundControl game){
        Texture texture = game.getAssetManager().get("Planets/SmallPlanet.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
