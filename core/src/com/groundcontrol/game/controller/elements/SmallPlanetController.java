package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;

/**
 * Small planet controller
 */
public class SmallPlanetController extends ElementController {

    private final static int maxVelocity = 12;

    private final static float maxAngularVelocity = 0.02f;

    private static int imageWidth = 612;

    private static int imageHeight = 601;

    private static float density = 8000f;

    private static float friction = 1f;

    private static float restitution = 0.0f;

    /**
     * Creates a new small planet Controller and add its to the current world.
     * @param world the current world
     * @param model the planet model
     */
    public SmallPlanetController(World world, ElementModel model) {

        super(world, model, BodyDef.BodyType.DynamicBody);

        this.width = imageWidth;
        this.height = imageHeight;

        FixtureInfo info = new FixtureInfo(new float[]{
                1, 327,
                63, 457,
                107, 494,
                160, 565,
                241, 589,
                301, 583,
                147, 53,
                73, 103
        }, width, height);

        info.physicsComponents(density, friction, restitution);

        info.collisionComponents(PLANET_BODY, (short) (PLANET_BODY | PLAYER_BODY | COMET_BODY));

        createFixture(body, info);

        info.vertexes = new float[]{
                301, 583,
                391, 597,
                518, 552,
                610, 293,
                610, 236,
                504, 36,
                329, 1,
                147, 53};

        createFixture(body, info);

        this.body.setAngularDamping(0.9f);
    }

    @Override
    public float getMaxVelocity() {
        return this.maxVelocity;
    }

    @Override
    public float getMaxAngular() {
        return this.maxAngularVelocity;
    }

}
