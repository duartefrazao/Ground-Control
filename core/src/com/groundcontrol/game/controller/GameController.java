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
import com.groundcontrol.game.controller.elements.ElementController;
import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.controller.state.InputDecoder;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;
import com.groundcontrol.game.view.GameView;

import java.util.ArrayList;
import java.util.List;

public class GameController implements ContactListener {

    public static final int ARENA_WIDTH = 50;

    public static final int ARENA_HEIGHT = 100;

    public static final double G = Math.pow(3.667, -2);

    private final World world;

    private float accumulator;

    private GameModel gameModel;

    private final PlayerController playerController;

    private ArrayList<Body> planets = new ArrayList<Body>();

    private ArrayList<ElementController> planetControllers = new ArrayList<ElementController>();

    private InputDecoder decoder;

    private ScoreController scoreController;

    private ForceController forceController;

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

        world.setContactListener(this);
    }

    public void update(float delta) {

        this.gameModel.update(delta);

        this.scoreController.update(delta);

        applyGravityToPlanets();

        playerController.update(planets, delta);

        for(ElementController ec : this.planetControllers)
            ec.verifyBounds();

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;

        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;
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
    }


    public World getWorld() {
        return this.world;
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
}
