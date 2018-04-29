package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlanetModel;

public class BigPlanetController extends ElementController {

    public BigPlanetController(World world, ElementModel model){

        super(world, model, BodyDef.BodyType.KinematicBody);

        float density = 1f,
                friction = 0.4f,
                restitution = 0.5f;
        int width = 1024, height = 1024;

        createFixture(body, new float[]{
                247, 135,
                199, 190,
                79, 499,
                78, 589,
                180, 721,
                409, 855,
                486, 877,
                324, 109
        }, width, height, density, friction, restitution, PLANET_BODY, PLANET_BODY);

        createFixture(body, new float[]{
                324, 109,
                588, 97,
                808, 156,
                892, 229,
                962, 583,
                922, 699,
                721, 847,
                486, 877
        },width, height, density, friction, restitution, PLANET_BODY, PLANET_BODY);


    }
}
