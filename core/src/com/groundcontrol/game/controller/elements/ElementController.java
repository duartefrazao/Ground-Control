package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.elements.ElementModel;

import static com.groundcontrol.game.view.GameView.PIXEL_TO_METER;

/**
 * Abstract element controller.
 * Represents all the elements in our controller
 */
public abstract class ElementController {

    //Collision Handling
    final static short PLANET_BODY = 0x0001;
    final static short PLAYER_BODY = 0x0002;
    final static short COMET_BODY = 0x0003;

    protected Body body;

    protected int width;
    protected int height;

    protected int width_meters;
    protected int height_meters;

    /**
     * Creates a new element controller in a world, following a body type and a model
     * @param world
     * @param model
     * @param bodyType
     */
    ElementController(World world, ElementModel model, BodyDef.BodyType bodyType) {

        BodyDef bodydef = new BodyDef();

        bodydef.type = bodyType;

        bodydef.position.set(model.getX(), model.getY());

        bodydef.angle = model.getRotation();

        body = world.createBody(bodydef);

        body.setUserData(model);
    }


    final private void fixVertexes(float[] vertexes) {

        for (int i = 0; i < vertexes.length; i++) {
            if (i % 2 == 0)
                vertexes[i] -= width / 2;
            if (i % 2 != 0)
                vertexes[i] -= height / 2;

            if (i % 2 != 0)
                vertexes[i] *= -1;

            vertexes[i] *= PIXEL_TO_METER;
        }


    }

    final void createFixture(Body body, FixtureInfo info) {

        this.height_meters = (int) ((float) this.height * PIXEL_TO_METER);

        this.width_meters = (int) ((float) this.width * PIXEL_TO_METER);

        this.fixVertexes(info.vertexes);

        PolygonShape polygon = new PolygonShape();
        polygon.set(info.vertexes);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;

        fixtureDef.density = info.density;

        fixtureDef.friction = info.friction;
        fixtureDef.restitution = info.restitution;
        fixtureDef.filter.categoryBits = info.category;
        fixtureDef.filter.maskBits = info.mask;

        body.createFixture(fixtureDef);

        polygon.dispose();

    }

    /**
     * Returns the element body
     * @return body
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * Wraps the getX method from the Box2D body class.
     *
     * @return the x-coordinate of this body.
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * Wraps the getY method from the Box2D body class.
     *
     * @return the y-coordinate of this body.
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     * Wraps the setTransform method from the Box2D body class.
     *
     * @param x the new x-coordinate for this body
     * @param y the new y-coordinate for this body
     * @param angle the new rotation angle for this body
     */
    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    /**
     * Wraps the applyForceToCenter method from the Box2D body class.
     *
     * @param v the force to be applied
     * @param awake should the body be awaken
     */
    public void applyForceToCenter(Vector2 v, boolean awake) {
        body.applyForceToCenter(v, awake);
    }

    /**
     * Wraps the applyLinearImpulseToCenter method from the Box2D body class.
     *
     * @param v the force to be applied
     * @param awake should the body be awaken
     */
    public void applyLinearImpulseToCenter(Vector2 v, boolean awake) {
        body.applyLinearImpulse(v, body.getLocalCenter(), awake);
    }

    /**
     * Wraps the getPosition method from the Box2D body class.
     *
     * @return the position of this body.
     */
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /**
     * Wraps the getMass method from the Box2D body class.
     *
     * @return the mass of this body.
     */
    public float getMass() {
        return body.getMass();
    }

    /**
     * Returns the angle between this body and another
     * @param body the other body
     * @return the angle between them
     */
    public float getAngleBetween(Body body) {
        float rot = (float) Math.atan2(body.getPosition().y - this.getY(), body.getPosition().x - this.getX());

        rot += Math.PI / 2.0;

        return rot;
    }

    /**
     * Calculates the pull force between two bodies following Newton's formula
     * @param body the other body
     * @return the vector representing the force
     */
    public Vector2 calculatePullForce(Body body) {

        double distanceSquared = body.getPosition().dst2(this.getPosition());

        double planet_mass = body.getMass();

        double player_mass = this.getMass();

        double force_module = GameController.G * (planet_mass * player_mass) / distanceSquared;

        Vector2 force = body.getPosition().sub(this.getPosition()).nor();

        force.setLength((float) force_module);

        return force;
    }

    /**
     * Returns the max velocity allowed for this controller
     * @return the max velocity
     */
    public abstract float getMaxVelocity();

    /**
     * Limits this controller velocity to it's max velocity
     */
    public void limitVelocity() {

        float x = this.body.getLinearVelocity().x;
        float y = this.body.getLinearVelocity().y;

        if (Math.abs(x) > getMaxVelocity())
            x = getMaxVelocity() * Math.signum(x);

        if (Math.abs(y) > getMaxVelocity())
            y = getMaxVelocity() * Math.signum(y);

        this.body.setLinearVelocity(x, y);
    }


    /**
     * Returns the max angular velocity allowed for this controller
     * @return the max angular velocity
     */
    public abstract float getMaxAngular();

    /**
     * Limits this controller angular velocity to it's max angular velocity
     */
    public void limitAngularVelocity() {

        float omega = this.body.getAngularVelocity();

        if (Math.abs(omega) > getMaxVelocity())
            omega = getMaxAngular() * Math.signum(omega);

        this.body.setAngularVelocity(omega);
    }


}
