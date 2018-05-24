package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;

public class BigPlanetController extends ElementController {

    private final static int maxVelocity = 8;

    private final static float maxAngularVelocity = 0.02f;

    public  float getMaxVelocity(){
        return this.maxVelocity;
    }

    public float getMaxAngular(){
        return this.maxAngularVelocity;
    }

    public BigPlanetController(World world, ElementModel model) {

        super(world, model, BodyDef.BodyType.DynamicBody);

        float density = 8000f,
                friction = 1f,
                restitution = 0.0f;
        this.width = 1024;
        this.height = 1024;

        createFixture(body, new float[]{
                247, 135,
                199, 190,
                79, 499,
                78, 589,
                180, 721,
                409, 855,
                486, 877,
                324, 109
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        createFixture(body, new float[]{
                324, 109,
                588, 97,
                808, 156,
                892, 229,
                962, 583,
                922, 699,
                721, 847,
                486, 877
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        this.body.setAngularDamping(0.9f);
        this.artificialGravity = (float) Math.random() + 0.5f;

    }
}
