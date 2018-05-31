package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;

public class MediumPlanetController extends ElementController {

    private final static int maxVelocity = 11;

    private final static float maxAngularVelocity = 0.02f;

    public  float getMaxVelocity(){
        return this.maxVelocity;
    }

    public float getMaxAngular(){
        return this.maxAngularVelocity;
    }

    public MediumPlanetController(World world, ElementModel model) {

        super(world, model, BodyDef.BodyType.DynamicBody);

        float density = 9000f,
                friction = 1f,
                restitution = 0.0f;
        this.width = 712;
        this.height = 678;

        createFixture(body, new float[]{
                342, 1,
                479, 1,
                662, 80,
                699, 152,
                710, 406,
                462, 675,
                336, 669,
                165, 56
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        createFixture(body, new float[]{
                165, 56,
                52, 92,
                1, 247,
                5, 343,
                73, 567,
                160, 645,
                336, 669,
                221, 42
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        this.body.setAngularDamping(0.9f);

    }

}
