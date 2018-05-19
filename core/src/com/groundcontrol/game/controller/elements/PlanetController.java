package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlanetModel;

import javax.xml.bind.Element;

public class PlanetController extends ElementController{


    private final static int maxVelocity = 12;

    private final static float maxAngularVelocity = 0.02f;

    private final static float artificialGravity = 0.9f;


    public  float getMaxVelocity(){
        return this.maxVelocity;
    }

    public float getMaxAngular(){
        return this.maxAngularVelocity;
    }


    public PlanetController(World world, ElementModel model){

        super(world, model, BodyDef.BodyType.DynamicBody);

        float density = 11000f,
                friction = 1f,
                restitution = 0f;
        int width = 512, height = 512;

        createFixture(body, new float[]{
                204, 57,
                101, 83,
                36, 254,
                44, 318,
                139, 374,
                239, 398,
                345, 398,
                248, 52
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY|PLAYER_BODY));

        createFixture(body, new float[]{
                420, 360,
                459, 250,
                414, 122,
                296, 54,
                247, 49,
                345, 398,
        },width, height, density, friction, restitution, PLANET_BODY,  (short) (PLANET_BODY|PLAYER_BODY));

        this.body.setAngularDamping(0.9f);
    }

    public float getGravityPercentage(){
        return this.artificialGravity;
    }

}
