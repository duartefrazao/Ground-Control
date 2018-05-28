package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;

public class MediumBigPlanetController extends ElementController{

    private final static int maxVelocity = 9;

    private final static float maxAngularVelocity = 0.02f;

    public  float getMaxVelocity(){
        return this.maxVelocity;
    }

    public float getMaxAngular(){
        return this.maxAngularVelocity;
    }

    public MediumBigPlanetController(World world, ElementModel model) {

        super(world, model, BodyDef.BodyType.DynamicBody);

        float density = 8000f,
                friction = 1f,
                restitution = 0.0f;
        this.width = 854;
        this.height = 812;

        createFixture(body, new float[]{
                216, 21,
                49, 139,
                39, 250,
                1, 389,
                6, 469,
                51, 624,
                147, 753,
                531, 1
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        createFixture(body, new float[]{
                531, 1,
                784, 127,
                825, 187,
                849, 429,
                850, 522,
                625, 753,
                396, 808,
                447, 3
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY));


        createFixture(body, new float[]{
                396, 808,
                259, 780,
                159, 760,
                663, 55
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY));


        this.body.setAngularDamping(0.9f);
        this.artificialGravity = (float) Math.random() + 0.5f;

    }
}
