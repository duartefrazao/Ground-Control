package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;

/**
 * Comet Controller
 */
public class CometController extends ElementController {

    private Vector2 initialVelocity = new Vector2((float) (Math.random() + 0.7) * 20, (float) (Math.random() + 0.7) * 20);

    private static float  maxAngularVelocity = 0.5f;

    private static int imageWidth = 218;

    private static int imageHeight = 371;

    private static float density = 1f;

    private static float friction = 1f;

    private static float restitution = 0.3f;

    /**
     * Creates a new comer Controller and add its to the current world.
     * @param world the current world
     * @param model the comet model
     */
    public CometController(World world, ElementModel model) {

        super(world, model, BodyDef.BodyType.DynamicBody);

        this.width = imageWidth;
        this.height = imageHeight;

        FixtureInfo info = new FixtureInfo(new float[]{94, 283,
                57, 316,
                73, 346,
                142, 357,
                169, 334,
                139, 293,
                109, 289,
                99, 295}, width, height);

        info.physicsComponents(density, friction, restitution);

        info.collisionComponents(COMET_BODY, (short) (COMET_BODY | PLANET_BODY | PLAYER_BODY | COMET_BODY) );

        createFixture(body, info);

        this.body.setGravityScale(0);

    }

    @Override
    public float getMaxVelocity() {
        return this.initialVelocity.len();
    }

    @Override
    public float getMaxAngular() {
        return this.maxAngularVelocity;
    }

    /**
     * Applies a default initial velocity to the comet.
     * The velocity direction will be dependant on the region that the comet is created
     * @param vx_direction
     * @param vy_direction
     */
    public void applyInitialVelocity(int vx_direction, int vy_direction) {

        this.initialVelocity.x *= vx_direction;
        this.initialVelocity.y *= vy_direction;

        this.body.setLinearVelocity(initialVelocity);

        this.body.setTransform(this.body.getPosition().x, this.body.getPosition().y, (float) (Math.PI / 2.0 + this.initialVelocity.angleRad()));

    }

}
