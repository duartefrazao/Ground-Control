package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.PlanetModel;

public class PlanetFactory {

    public static void createPlanet(PlanetModel p, World world){

        switch ((p.getSize())){
            case BIG:
                new BigPlanetController(world, p);
                return;
            case MEDIUM_BIG:
                new MediumBigPlanetController(world, p);
                return;
            case MEDIUM:
                new MediumPlanetController(world, p);
                return;
            case SMALL:
                new SmallPlanetController(world, p);
                return;
        }

    }


}
