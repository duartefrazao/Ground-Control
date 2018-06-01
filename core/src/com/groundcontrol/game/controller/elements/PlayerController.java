package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.groundcontrol.game.controller.state.FloatState;
import com.groundcontrol.game.controller.state.InputDecoder;
import com.groundcontrol.game.controller.state.PlayerState;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlayerModel;

/**
 * Player Controller
 * The player has a countdown timer. If the timer reaches 0, he loses.
 * While he is floating through space, the timer freezes. While he is on a planet it works normally.
 * After each jump, the player receives a small time bonus.
 */
public class PlayerController extends ElementController {

    public final static int walkToPullRation = 40;

    private final static float runningRelativeVelocity = 3.5f;

    private final static float maxVelocity = 13;

    private final static float maxAngularVelocity = 0.02f;

    private final static float maximumAllowedDistance = 64;

    private final static float jumpBonusTime = 1f;

    private static int imageWidth = 244;

    private static int imageHeight = 423;

    private static float density = 20;

    private static float friction = 1f;

    private static float restitution = 0.0f;

    private float jumpingTime = 0f;

    private PlayerState state;

    private Body currentPlanet = null;

    private boolean rightSide = true;

    private boolean lost;

    /**
     * Creates a new player Controller and add its to the current world.
     *
     * @param world the current world
     * @param model the player model
     */
    public PlayerController(World world, ElementModel model) {
        super(world, model, BodyDef.BodyType.DynamicBody);

        state = new FloatState();

        this.width = imageWidth;
        this.height = imageHeight;

        //head
        FixtureInfo info = new FixtureInfo(new float[]{
                62, 186,
                32, 122,
                57, 67,
                98, 48,
                160, 53,
                207, 123,
                193, 195,
                62, 186
        }, width, height);

        info.physicsComponents(density, friction, restitution);

        info.collisionComponents(PLAYER_BODY, (short) (PLANET_BODY | PLAYER_BODY | COMET_BODY));

        createFixture(body, info);

        //corns
        info.vertexes = new float[]{
                114, 49,
                118, 33,
                109, 19,
                142, 13,
                142, 26,
                129, 33,
                114, 49};

        createFixture(body, info);

        info.vertexes = new float[]{
                191, 83,
                207, 66,
                215, 52,
                219, 26,
                241, 34,
                232, 52,
                219, 76,
                191, 83};

        createFixture(body, info);

        //arms
        info.vertexes = new float[]{
                61, 196,
                23, 198,
                3, 217,
                21, 268,
                61, 196};

        createFixture(body, info);

        info.vertexes = new float[]{
                150, 229,
                175, 285,
                166, 316,
                156, 330,
                150, 229};

        createFixture(body, info);


        //legs
        info.vertexes = new float[]{
                31, 332,
                37, 370,
                36, 401,
                31, 416,
                90, 418,
                85, 403,
                81, 374,
                31, 332};

        createFixture(body, info);

        info.vertexes = new float[]{
                107, 359,
                102, 395,
                106, 418,
                161, 417,
                144, 397,
                107, 359,
                152, 327};

        createFixture(body, info);


        //Belly
        info.vertexes = new float[]{
                75, 219,
                17, 283,
                41, 346,
                90, 364,
                143, 330,
                151, 280,
                138, 227,
                75, 219};

        createFixture(body, info);

        this.body.setGravityScale(0);
        this.body.setAngularDamping(0.7f);

        this.lost = false;
    }

    /**
     * Tells if the player is facing the right side or left side
     *
     * @return true if facing right side, false otherwise
     */
    public boolean isRightSide() {
        return rightSide;
    }

    /**
     * Sets the player current facing direction
     *
     * @param side if true, sets it to the right, left otherwise
     */
    public void setRightSide(boolean side) {
        this.rightSide = side;
    }

    /**
     * Updates the current player state
     *
     * @param state the new state
     */
    public void setState(PlayerState state) {
        this.state = state;
    }

    /**
     * Handles the input given by the view
     *
     * @param input
     */
    public void handleInput(InputDecoder.Input input) {
        this.state.handleInput(this, input);
    }

    /**
     * Makes the player jump if he is in a planet
     * When it is in a planet, sets the jump time to the default in order to avoid small jumps.
     * Our gravity will only apply after that time has elapsed.
     * After each successful jump, a small bonus is added to the time the player has left
     */
    public void jump() {

        if (this.getPlanet() == null) {
            return;
        }

        float rot = this.getAngleBetween(this.getPlanet());

        this.jumpingTime = 0.5f;

        rot -= Math.PI / 2.0;

        Vector2 direction = new Vector2((float) Math.cos(rot), (float) Math.sin(rot)).nor();

        this.applyForceToCenter(direction.rotate(180).scl((float) (Math.pow(this.calculatePullForce(this.getPlanet()).len(), 4))), true);

        this.limitVelocity();

        this.removeFromPlanet();

        float time = this.state.getTime();

        this.setState(new FloatState(time + jumpBonusTime));

    }

    /**
     * Makes the player walk in the direction the user wants, if he is in a planet
     *
     * @param dir - the direction given by the user
     */
    public void walk(int dir) {

        float rot = this.getAngleBetween(this.getPlanet());

        rot -= Math.PI / 2.0;

        Vector2 direction = new Vector2((float) Math.cos(rot), (float) Math.sin(rot)).nor();

        direction.scl(this.calculatePullForce(this.getPlanet()).len() / walkToPullRation);

        this.applyLinearImpulseToCenter(direction.rotate90(dir), true);
    }

    /**
     * Tells if the user is in a planet
     *
     * @return true if is, false otherwise
     */
    public boolean isInPlanet() {
        return this.currentPlanet != null;
    }

    /**
     * Sets the player in a planet
     *
     * @param b the planet
     */
    public void setInPlanet(Body b) {
        this.currentPlanet = b;
    }

    /**
     * Removes the player from the current planet
     */
    public void removeFromPlanet() {
        this.currentPlanet = null;
    }

    /**
     * Returns the body of the current planet
     *
     * @return the body of the planet
     */
    public Body getPlanet() {
        return this.currentPlanet;
    }

    /**
     * Alters the player rotation, given the current state
     *
     * @param objects - surrounding bodies
     */
    public void setRotation(Array<Body> objects) {
        this.state.setRotation(this, objects);
    }

    /**
     * Applies the current gravity force to the player, given its current state
     * If the player is still in jumping time, it doesn't do nothing
     *
     * @param objects surrounding bodies
     */
    public void applyPullForce(Array<Body> objects) {
        if (this.jumpingTime == 0)
            this.state.applyPullForce(this, objects);
    }

    @Override
    public float getMaxVelocity() {
        return this.maxVelocity;
    }

    @Override
    public float getMaxAngular() {
        return this.maxAngularVelocity;
    }

    /**
     * Checks if the player hasn't drifted away too much from a player
     * If so, removes it from the planet
     */
    public void verifyInPlanet() {
        if (!this.isInPlanet())
            return;
        if (this.getPosition().dst2(this.getPlanet().getPosition()) > maximumAllowedDistance) {
            this.removeFromPlanet();
            this.setState(new FloatState(this.state.getTime()));
        }
    }

    /**
     * Updates all the aspects related to the player in each step
     *
     * @param planets surrounding bodies
     * @param delta   time elapsed after the last sped
     */
    public void update(Array<Body> planets, float delta) {

        this.state.updateTime(this, delta);

        updateJumpTime(delta);

        this.applyPullForce(planets);

        this.limitVelocity();

        this.limitAngularVelocity();

        this.setRotation(planets);

        this.verifyInPlanet();

        this.updateMovementState();
    }


    private void updateJumpTime(float delta) {

        jumpingTime -= delta;

        if (jumpingTime < 0)
            jumpingTime = 0;
    }

    private void updateMovementState() {

        if (!isInPlanet())
            ((PlayerModel) body.getUserData()).setCurrentState(PlayerModel.animationState.FLOATING);
        else {

            if (Math.abs(this.body.getLinearVelocity().len() - this.getPlanet().getLinearVelocity().len()) > runningRelativeVelocity)
                ((PlayerModel) body.getUserData()).setCurrentState(PlayerModel.animationState.RUNNING);
            else
                ((PlayerModel) body.getUserData()).setCurrentState(PlayerModel.animationState.IDLE);

        }

    }

    /**
     * Returns the time the player still has left
     *
     * @return time
     */
    public float getTimeLeft() {
        return this.state.getTime();
    }

    /**
     * Tells if the player has lost or not
     * @return true if lost, false otherwise
     */
    public boolean hasLost(){
        return this.lost;
    }

    /**
     * Sets the lost the condition
     * @param lost
     */
    public void setLost(boolean lost){
        this.lost = lost;
    }


}
