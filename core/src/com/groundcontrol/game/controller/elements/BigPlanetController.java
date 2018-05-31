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
        this.height = 1006;

        createFixture(body, new float[]{
                504, 1,
                183, 165,
                48, 346,
                1, 547,
                85, 715,
                214, 888,
                876, 223,
                568, 3
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        createFixture(body, new float[]{
                879, 229,
                1017, 549,
                1012, 664,
                759, 958,
                549, 999,
                361, 964,
                226, 892,
                852, 207
        }, width, height, density, friction, restitution, PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        this.body.setAngularDamping(0.9f);

    }
}
