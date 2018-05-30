package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.PlanetModel;

public class PlanetFactory {

    public static void createPlanet(PlanetModel p, World world){

        if (p.getSize() == PlanetModel.PlanetSize.MEDIUM)
            new MediumPlanetController(world, p);
        else if(p.getSize() == PlanetModel.PlanetSize.MEDIUM_BIG)
            new MediumBigPlanetController(world, p);
        else if(p.getSize() == PlanetModel.PlanetSize.BIG)
            new BigPlanetController(world, p);
        else
            new SmallPlanetController(world, p);

    }


}
