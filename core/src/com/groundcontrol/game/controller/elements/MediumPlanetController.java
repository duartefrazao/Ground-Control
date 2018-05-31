package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;

public class MediumPlanetController extends ElementController {

    private final static int maxVelocity = 11;

    private final static float maxAngularVelocity = 0.02f;

    private static int imageWidth = 712;

    private static int imageHeight = 678;

    private static float density = 9000f;

    private static float friction = 1f;

    private static float restitution = 0.0f;

    public MediumPlanetController(World world, ElementModel model) {

        super(world, model, BodyDef.BodyType.DynamicBody);

        this.width = imageWidth;
        this.height = imageHeight;

        FixtureInfo info = new FixtureInfo(new float[]{
                342, 1,
                479, 1,
                662, 80,
                699, 152,
                710, 406,
                462, 675,
                336, 669,
                165, 56
        }, width, height);

        info.physicsComponents(density, friction, restitution);

        info.collisionComponents(PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY | COMET_BODY));

        createFixture(body, info);

        info.vertexes = new float[]{
                165, 56,
                52, 92,
                1, 247,
                5, 343,
                73, 567,
                160, 645,
                336, 669,
                221, 42};

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
