package com.groundcontrol.game.controller.elements;

/**
 * Object created to hold all the fixture information
 */
public class FixtureInfo {

    /**
     * Fixture width
     */
    public int width;

    /**
     * Fixture height
     */
    public int height;

    /**
     * The body's to which the fixture is associated density
     */
    public float density;

    /**
     * The body's to which the fixture is associated friction
     */
    public float friction;

    /**
     * The body's to which the fixture is associated restitution
     */
    public float restitution;

    /**
     * The body's to which the fixture is associated category
     */
    public short category;

    /**
     * The body's to which the fixture is associated mask
     */
    public short mask;

    /**
     * The fixture vertexes
     */
    public float[] vertexes;

    /**
     * Creates a new fixture info with the vertexes, width, height
     * @param vertexes
     * @param width
     * @param height
     */
    public FixtureInfo(float[] vertexes, int width, int height) {
        this.vertexes = vertexes;
        this.width = width;
        this.height = height;
    }

    /**
     * Adds all the physics components to the fixture info
     * @param density
     * @param friction
     * @param restitution
     */
    public void physicsComponents(float density, float friction, float restitution) {
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
    }

    /**
     * Adds all the collision components to the fixture info
     * @param category
     * @param mask
     */
    public void collisionComponents(short category, short mask) {
        this.category = category;
        this.mask = mask;
    }
}
