package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;

public class SmallPlanetController extends ElementController{


    private final static int maxVelocity = 12;

    private final static float maxAngularVelocity = 0.02f;

    public  float getMaxVelocity(){
        return this.maxVelocity;
    }

    public float getMaxAngular(){
        return this.maxAngularVelocity;
    }


    public SmallPlanetController(World world, ElementModel model){

        super(world, model, BodyDef.BodyType.DynamicBody);

        float density = 8000f,
                friction = 1f,
                restitution = 0.0f;
        this.width = 612;
        this.height = 601;

        createFixture(body, new float[]{
                1, 327,
                63, 457,
                107, 494,
                160, 565,
                241, 589,
                301, 583,
                147, 53,
                73, 103
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY|PLAYER_BODY));

        createFixture(body, new float[]{
                301, 583,
                391, 597,
                518, 552,
                610, 293,
                610, 236,
                504, 36,
                329, 1,
                147, 53
        },width, height, density, friction, restitution, PLANET_BODY,  (short) (PLANET_BODY|PLAYER_BODY));

        this.body.setAngularDamping(0.9f);
    }

}
