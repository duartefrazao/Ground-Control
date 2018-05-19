package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.controller.state.FloatState;
import com.groundcontrol.game.controller.state.InputDecoder;
import com.groundcontrol.game.controller.state.PlayerState;
import com.groundcontrol.game.model.elements.ElementModel;

import java.util.ArrayList;

public class PlayerController extends ElementController {

    private PlayerState state;

    public final static float jumpMultiplier = 50000000;

    public final static float walkMultiplier = 150;

    private final static float maxVelocity = 15;

    private final static float walkPull = 10;

    private final static float maxAngularVelocity = 0.02f;

    private Body currentPlanet = null;

    private boolean rightSide = true;

    public boolean isRightSide() {
        return rightSide;
    }

    public void setRightSide(boolean side) {
        this.rightSide = side;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public void handleInput(InputDecoder.Input input) {
        this.state.handleInput(this, input);
    }

    public float getWalkPull() {
        return this.walkPull;
    }

    public PlayerController(World world, ElementModel model) {
        super(world, model, BodyDef.BodyType.DynamicBody);

        state = new FloatState();

        float density = 20f,
                friction = 0.7f,
                restitution = 0.0f;
        int width = 244, height = 423;

        //Head
        createFixture(body, new float[]{
                62, 186,
                32, 122,
                57, 67,
                98, 48,
                160, 53,
                207, 123,
                193, 195,
                62, 186,
        }, width, height, density, friction, restitution, PLAYER_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        //Corns
        createFixture(body, new float[]{
                114, 49,
                118, 33,
                109, 19,
                142, 13,
                142, 26,
                129, 33,
                114, 49,
        }, width, height, density, friction, restitution, PLAYER_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        createFixture(body, new float[]{
                191, 83,
                207, 66,
                215, 52,
                219, 26,
                241, 34,
                232, 52,
                219, 76,
                191, 83,
        }, width, height, density, friction, restitution, PLAYER_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        //Arms
        createFixture(body, new float[]{
                61, 196,
                23, 198,
                3, 217,
                21, 268,
                61, 196,
        }, width, height, density, friction, restitution, PLAYER_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        createFixture(body, new float[]{
                150, 229,
                175, 285,
                166, 316,
                156, 330,
                150, 229,
        }, width, height, density, friction, restitution, PLAYER_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        //Legs
        createFixture(body, new float[]{
                31, 332,
                37, 370,
                36, 401,
                31, 416,
                90, 418,
                85, 403,
                81, 374,
                31, 332,
        }, width, height, density, friction, restitution, PLAYER_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        createFixture(body, new float[]{
                107, 359,
                102, 395,
                106, 418,
                161, 417,
                144, 397,
                107, 359,
                152, 327,
        }, width, height, density, friction, restitution, PLAYER_BODY, (short) (PLANET_BODY | PLAYER_BODY));

        //Belly
        createFixture(body, new float[]{
                75, 219,
                17, 283,
                41, 346,
                90, 364,
                143, 330,
                151, 280,
                138, 227,
                75, 219,
        }, width, height, density, friction, restitution, PLAYER_BODY, (short) (PLANET_BODY | PLAYER_BODY));


        System.out.println("Player mass: " + this.body.getMass());
    }

    public void jump() {

        float rot = this.getAngleBetween(this.getPlanet());

        rot -= Math.PI / 2.0;

        Vector2 direction = new Vector2((float) Math.cos(rot), (float) Math.sin(rot)).nor();

        this.applyForceToCenter(direction.rotate(180).scl((float) Math.pow(this.calculatePullForce(this.getPlanet()).len(), 2)), true);

        this.removeFromPlanet();

    }

    public boolean isInPlanet() {
        return this.currentPlanet != null;
    }

    public void setInPlanet(Body b) {
        this.currentPlanet = b;
    }

    public void removeFromPlanet() {
        this.currentPlanet = null;
    }

    public Body getPlanet() {
        return this.currentPlanet;
    }

    public void setRotation(ArrayList<Body> objects) {
        this.state.setRotation(this, objects);
    }

    public void applyPullForce(ArrayList<Body> objects) {
        this.state.applyPullForce(this, objects);
    }

    public float getMaxVelocity() {
        return this.maxVelocity;
    }

    public float getMaxAngular() {
        return this.maxAngularVelocity;
    }

    public float getGravityPercentage() {
        return 0;
    }

    public void verifyInPlanet() {
        if (!this.isInPlanet())
            return;
        if (this.getPosition().dst2(this.getPlanet().getPosition()) > 100) {
            this.removeFromPlanet();
            this.setState(new FloatState());
        }
    }

}
