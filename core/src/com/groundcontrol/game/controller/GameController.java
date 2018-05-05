package com.groundcontrol.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.groundcontrol.game.controller.elements.BigPlanetController;
import com.groundcontrol.game.controller.elements.ElementController;
import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;
import com.groundcontrol.game.view.GameView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.decrementExact;
import static com.badlogic.gdx.math.MathUtils.random;

public class GameController implements ContactListener {

    public static final int ARENA_WIDTH = 50;

    public static final int ARENA_HEIGHT = 100;

    private static final double G = 6.667;

    private static final float VELOCITY_LIMIT = 8f;

    private static final float ANGULAR_LIMIT = 0.02f;

    private static float PULL_LIMIT = 10f;

    private final World world;

    private float accumulator;

    private GameModel gameModel;

    private final PlayerController playerController;

    private ArrayList<ElementController> planetControllers = new ArrayList<ElementController>();

    private Vector2 planetForce = new Vector2(0, 0);

    public void setPlanetForce(float x, float y) {
        this.planetForce.x = x;
        this.planetForce.y = y;
    }

    public void handleInput(GameView.StateInput input){
        this.playerController.handleInput(input);
    }

    public GameController(GameModel gameModel) {
        world = new World(new Vector2(0, 0), true);

        this.gameModel = gameModel;

        List<PlanetModel> planets = this.gameModel.getPlanets();

        ElementController planetC;

        playerController = new PlayerController(world, this.gameModel.getPlayer());

        for (PlanetModel p : planets) {

            if (p.getSize() == PlanetModel.PlanetSize.MEDIUM) {

                planetC = new PlanetController(world, p);
            } else {
                planetC = new BigPlanetController(world, p);
            }

            planetControllers.add(planetC);
        }


        world.setContactListener(this);


    }


    private void applyGravityToPlanets() {

        for (ElementController p : planetControllers) {
            p.setLinearVelocity(planetForce);
        }


    }

    private Vector2 calculatePullForce(ElementController planet) {

        double distanceSquared = planet.getPosition().dst2(playerController.getPosition());

        double planet_mass = planet.getMass();

        double player_mass = playerController.getMass();

        double force_module = G * (planet_mass * player_mass) / distanceSquared;

        Vector2 force = planet.getPosition().sub(playerController.getPosition()).nor();

        force.setLength((float) force_module);

        //force.limit(PULL_LIMIT);

        return force;
    }


    private Vector2 calculatePullForce(Body body){

        double distanceSquared = body.getPosition().dst2(playerController.getPosition());

        double planet_mass = body.getMass();

        double player_mass = playerController.getMass();

        double force_module = G  * (planet_mass * player_mass) / distanceSquared;

        Vector2 force = body.getPosition().sub(playerController.getPosition()).nor();

        force.setLength((float) force_module);

        //force.limit(PULL_LIMIT);

        return force;


    }

    private void applyPullForceToPlayer() {

        if(playerController.isInPlanet()){
            Vector2 force = calculatePullForce(playerController.getPlanet());
            playerController.applyForceToCenter(force.scl(7), true);
            return;
        }

        for (ElementController e : planetControllers) {
            Vector2 force = calculatePullForce(e.getBody());
            playerController.applyForceToCenter(force, true);
        }

    }

    private void limitVelocities(Body body) {

        float x = limitV(body.getLinearVelocity().x);
        float y = limitV(body.getLinearVelocity().y);

        float omega = limitOmega(body.getAngularVelocity());

        body.setLinearVelocity(x, y);
        body.setAngularVelocity(omega);
    }

    private float limitV(float v) {
        if (v > VELOCITY_LIMIT)
            v = VELOCITY_LIMIT;
        else if (v < (-VELOCITY_LIMIT))
            v = -VELOCITY_LIMIT;

        return v;
    }

    private float limitOmega(float omega) {
        if (omega > ANGULAR_LIMIT)
            omega = ANGULAR_LIMIT;
        else if (omega < (-ANGULAR_LIMIT))
            omega = -ANGULAR_LIMIT;

        return omega;
    }


    public void update(float delta) {

        this.gameModel.update(delta);

        float frameTime = Math.min(delta, 0.25f);

        accumulator += frameTime;

        applyGravityToPlanets();

        applyPullForceToPlayer();

        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        getPlayerRotation(delta);

        for (Body body : bodies) {
            verifyBounds(body);
            limitVelocities(body);
            ((ElementModel) body.getUserData()).setX(body.getPosition().x);
            ((ElementModel) body.getUserData()).setY(body.getPosition().y);
            ((ElementModel) body.getUserData()).setRotation(body.getAngle());

        }
    }

    private void getPlayerRotation(float delta) {

        Float distance;

        for (ElementController planet : planetControllers) {
            distance = abs(planet.getX() - playerController.getX());
            distance += abs(planet.getY() - playerController.getY());

            if (distance < 8) {

                /*

                float rot = (float) Math.atan2(planet.getY() - playerController.getY(), planet.getX() - playerController.getX());

                rot += Math.PI / 2.0;

                */

                float rot = playerController.getAngleBetween(planet.getBody());

                playerController.setTransform(playerController.getX(), playerController.getY(), rot);
            }

        }

    }


    public World getWorld() {
        return this.world;
    }


    private void verifyBounds(Body body) {
        if (body.getPosition().x < 0)
            body.setTransform(ARENA_WIDTH, body.getPosition().y * random.nextFloat(), body.getAngle());

        if (body.getPosition().y < 0)
            body.setTransform(body.getPosition().x * random.nextFloat(), ARENA_HEIGHT, body.getAngle());

        if (body.getPosition().x > ARENA_WIDTH)
            body.setTransform(0, body.getPosition().y * random.nextFloat(), body.getAngle());

        if (body.getPosition().y > ARENA_HEIGHT)
            body.setTransform(body.getPosition().x * random.nextFloat(), 0, body.getAngle());
    }

    @Override
    public void beginContact(Contact contact) {

        Body A = contact.getFixtureA().getBody();
        Body B = contact.getFixtureB().getBody();

        if (A.getUserData() instanceof PlayerModel)
            this.playerPlanetCollision(B);
        else if (B.getUserData() instanceof PlayerModel)
            this.playerPlanetCollision(A);

    }

    public void playerPlanetCollision(Body planet) {

        playerController.setInPlanet(planet);

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
