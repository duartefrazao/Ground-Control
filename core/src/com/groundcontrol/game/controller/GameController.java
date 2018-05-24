package com.groundcontrol.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.groundcontrol.game.controller.elements.BigPlanetController;
import com.groundcontrol.game.controller.elements.CometController;
import com.groundcontrol.game.controller.elements.ElementController;
import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.controller.gameflow.ForceController;
import com.groundcontrol.game.controller.gameflow.ScoreController;
import com.groundcontrol.game.controller.state.InputDecoder;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.CometModel;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;
import com.groundcontrol.game.view.GameView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameController implements ContactListener {

    public static final int ARENA_WIDTH = 50;

    public static final int ARENA_HEIGHT = 100;

    public static final double G = Math.pow(3.667, -2);

    private static final float TIME_BETWEEN_COMETS = 3f;

    protected final World world;
    protected final PlayerController playerController;
    protected float accumulator;
    protected GameModel gameModel;
    protected ArrayList<Body> planets = new ArrayList<Body>();

    protected ArrayList<ElementController> planetControllers = new ArrayList<ElementController>();
    protected ScoreController scoreController;
    protected ForceController forceController;
    protected float timeToNextComet;
    private int planetsToAddCounter;
    private InputDecoder decoder;

    private ArrayList<ElementController> cometControllers = new ArrayList<ElementController>();

    public GameController(GameModel gameModel) {
        world = new World(new Vector2(0, 0), true);

        this.gameModel = gameModel;

        List<PlanetModel> planets = this.gameModel.getPlanets();

        ElementController planetC;

        playerController = new PlayerController(world, this.gameModel.getPlayer());

        for (PlanetModel p : planets) {

            if (p.getSize() == PlanetModel.PlanetSize.MEDIUM)
                planetC = new PlanetController(world, p);
            else
                planetC = new BigPlanetController(world, p);

            this.planets.add(planetC.getBody());
            this.planetControllers.add(planetC);
        }

        this.decoder = new InputDecoder();

        this.scoreController = new ScoreController();

        this.forceController = new ForceController();

        this.timeToNextComet = TIME_BETWEEN_COMETS;

        this.planetsToAddCounter = 0;

        world.setContactListener(this);
    }

    public void setPlanetForce(float delta, float x, float y) {

        this.forceController.updateForce(delta, x, y);
    }

    private void applyGravityToPlanets() {

        for (ElementController c : planetControllers) {
            c.applyArtificialGravity(forceController.getForce());
        }

    }

    public void handleInput(GameView.StateInput input) {

        this.playerController.handleInput(this.decoder.convertViewInput(input));

    }


    public void update(float delta) {

        this.scoreController.update(delta);

        applyGravityToPlanets();

        playerController.update(planets, delta);

        this.checkForNewComet(delta);

        float frameTime = Math.min(delta, 0.25f);

        accumulator += frameTime;

        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;

        }

        for (ElementController ec : this.planetControllers) {
            ec.verifyBounds();
            ec.limitAngularVelocity();
        }



        Array<Body> bodies = new Array<Body>();

        world.getBodies(bodies);

        for (Body body : bodies) {
            ((ElementModel) body.getUserData()).setX(body.getPosition().x);
            ((ElementModel) body.getUserData()).setY(body.getPosition().y);
            ((ElementModel) body.getUserData()).setRotation(body.getAngle());
        }

        ((PlayerModel) playerController.getBody().getUserData()).setRightSide(playerController.isRightSide());

        this.gameModel.setScore(scoreController.getScore());

        this.gameModel.update(delta);

        this.checkForCometsOutOfBonds();

        removeFlagged(bodies);

        randomInsertPlanets();
    }

    private void randomInsertPlanets() {

        while (this.planetsToAddCounter > 0) {

            Vector2 r = generateRandomPeripheralPoints(20);

            PlanetModel newPlanet = this.gameModel.createPlanet(r.x, r.y);

            ElementController planet;

            if (newPlanet.getSize() == PlanetModel.PlanetSize.MEDIUM)
                planet = new PlanetController(world, newPlanet);
            else
                planet = new BigPlanetController(world, newPlanet);


            this.planets.add(planet.getBody());
            this.planetControllers.add(planet);

            --planetsToAddCounter;

            if (random.nextBoolean())
                break;

        }

    }

    private void checkForNewComet(float delta) {

        this.timeToNextComet -= delta;

        if (timeToNextComet <= 0) {

            Vector2 r = generateRandomPeripheralPoints(0);

            CometModel comet = this.gameModel.createComet(r.x, r.y);
            CometController cometC = new CometController(world, comet);

            int vx_direction = r.x > ARENA_WIDTH / 2.0f ? -1 : 1;
            int vy_direction = r.y > ARENA_HEIGHT / 2.0f ? -1 : 1;

            cometC.applyInitialVelocity(vx_direction, vy_direction);

            this.cometControllers.add(cometC);

            this.timeToNextComet = TIME_BETWEEN_COMETS;

        }

    }

    @Override
    public void beginContact(Contact contact) {

        Body A = contact.getFixtureA().getBody();
        Body B = contact.getFixtureB().getBody();

        if (A.getUserData() instanceof CometModel) {

            if (B.getUserData() instanceof PlayerModel)
                cometPlayerCollision(A, B);
            else
                cometObjectCollision(A, B);

        } else if (B.getUserData() instanceof CometModel) {

            if (A.getUserData() instanceof PlayerModel)
                cometPlayerCollision(B, A);
            else
                cometObjectCollision(B, A);


        } else if (A.getUserData() instanceof PlayerModel)
            this.playerPlanetCollision(B);
        else if (B.getUserData() instanceof PlayerModel)
            this.playerPlanetCollision(A);


    }

    private void cometPlayerCollision(Body comet, Body player) {

        System.out.println("YOU LOST!");

    }

    private void cometObjectCollision(Body comet, Body planet) {


        if (this.playerController.getPlanet() == planet)
            playerController.jump();

        this.gameModel.createExplosion(planet.getPosition().x, planet.getPosition().y);

        ((ElementModel) comet.getUserData()).setToBeRemoved(true);
        ((ElementModel) planet.getUserData()).setToBeRemoved(true);

        this.removeFromPlanetControllers(planet);
        planetsToAddCounter++;
        this.planets.remove(planet);

    }

    private void playerPlanetCollision(Body planet) {

        if (playerController.isInPlanet())
            return;

        playerController.setInPlanet(planet);

        playerController.handleInput(InputDecoder.Input.PLANET_LAND);

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

    private void removeFlagged(Array<Body> bodies) {

        Iterator<Body> iterator = bodies.iterator();

        while (iterator.hasNext()) {

            Body body = iterator.next();

            if (((ElementModel) body.getUserData()).isToBeRemoved()) {

                this.gameModel.removeModel((ElementModel) body.getUserData());
                world.destroyBody(body);

                iterator.remove();
            }


        }

    }

    private void checkForCometsOutOfBonds() {

        Iterator<ElementController> iterator = this.cometControllers.iterator();

        while (iterator.hasNext()) {

            ElementController cc = iterator.next();

            if (cc.isOutOfBonds()) {
                ((ElementModel) cc.getBody().getUserData()).setToBeRemoved(true);
                iterator.remove();
            }

        }

    }

    private void removeFromPlanetControllers(Body body) {

        Iterator<ElementController> iterator = this.planetControllers.iterator();

        while (iterator.hasNext()) {

            ElementController em = iterator.next();

            if (em.getBody().getPosition().x == body.getPosition().x && em.getBody().getPosition().y == body.getPosition().y) {
                iterator.remove();
                break;
            }
        }

    }


    private Vector2 generateRandomPeripheralPoints(float offset) {

        float x;
        float y;
        float r = random.nextFloat();

        if (random.nextBoolean()) {
            x = random.nextBoolean() ? -offset : ARENA_WIDTH + offset;
            y = r * ARENA_HEIGHT;
        } else {
            y = random.nextBoolean() ? -offset : ARENA_HEIGHT + offset;
            x = r * ARENA_WIDTH;
        }

        return new Vector2(x, y);

    }
}
