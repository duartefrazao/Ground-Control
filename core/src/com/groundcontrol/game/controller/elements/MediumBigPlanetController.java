package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;

public class MediumBigPlanetController extends ElementController {

    private final static int maxVelocity = 9;

    private final static float maxAngularVelocity = 0.02f;

    private static int imageWidth = 853;

    private static int imageHeight = 812;

    private static float density = 8000f;

    private static float friction = 1f;

    private static float restitution = 0.0f;

    public MediumBigPlanetController(World world, ElementModel model) {

        super(world, model, BodyDef.BodyType.DynamicBody);

        this.width = imageWidth;
        this.height = imageHeight;

        FixtureInfo info = new FixtureInfo(new float[]{
                216, 21,
                49, 139,
                39, 250,
                1, 389,
                6, 469,
                51, 624,
                147, 753,
                531, 1
        }, width, height);

        info.physicsComponents(density, friction, restitution);

        info.collisionComponents(PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY | COMET_BODY));

        createFixture(body, info);

        info.vertexes = new float[]{
                531, 1,
                784, 127,
                825, 187,
                849, 429,
                850, 522,
                625, 753,
                396, 808,
                447, 3};

        createFixture(body, info);

        info.vertexes = new float[]{
                396, 808,
                259, 780,
                159, 760,
                663, 55};

        createFixture(body, info);


        this.body.setAngularDamping(0.9f);

    }

    public float getMaxVelocity() {
        return this.maxVelocity;
    }

    public float getMaxAngular() {
        return this.maxAngularVelocity;
    }
}
