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

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.decrementExact;

public class GameController implements ContactListener {

    public static final int ARENA_WIDTH = 100;

    public static final int ARENA_HEIGHT = 50;

    private float pRot=0;
    private float newRot=0;

    private final World world;

    private float accumulator;

    private static float VELOCITY_LIMIT = 10f;
    private final  PlayerController playerController;
    private ArrayList<ElementController> planetControllers = new ArrayList<ElementController>();

    private GameModel gameModel;


    private static float ANGULAR_LIMIT = 0.02f;

    private Vector2 planetForce = new Vector2(0, 0);

    public void setPlanetForce(float x, float y) {
        this.planetForce.x = x * 10;
        this.planetForce.y = y * 10;
    }

    public GameController(GameModel gameModel){
        world = new World(new Vector2(0, 0), true);

        this.gameModel = gameModel;

        List<PlanetModel> planets = this.gameModel.getPlanets();

        ElementController planetC;

        playerController = new PlayerController(world,this.gameModel.getPlayer());

        for (PlanetModel p : planets) {

            if (p.getSize() == PlanetModel.PlanetSize.MEDIUM) {

                planetC = new PlanetController(world, p);
                planetControllers.add(planetC);
            }
            else {
                planetC = new BigPlanetController(world, p);
                planetControllers.add(planetC);
            }
        }


        world.setContactListener(this);


    }


    private void applyGravityToPlanets() {
        //Array<Body> bodies = new Array<Body>();

      //  world.getBodies(bodies);

        for(ElementController p : planetControllers){
            p.setLinearVelocity(planetForce);
        }


        /*
        for (Body body : bodies) {
            //body.applyForceToCenter(planetForce, true);
            body.setLinearVelocity(planetForce);
        }
        */
    }

    private void limitVelocities(Body body) {


        float x = body.getLinearVelocity().x;

        if (x > VELOCITY_LIMIT)
            x = VELOCITY_LIMIT;
        else if (x < (-VELOCITY_LIMIT))
            x = -VELOCITY_LIMIT;

        float y = body.getLinearVelocity().y;

        if (y > VELOCITY_LIMIT)
            y = VELOCITY_LIMIT;
        else if (y < (-VELOCITY_LIMIT))
            y = -VELOCITY_LIMIT;


        float omega = body.getAngularVelocity();

        if (omega > ANGULAR_LIMIT)
            omega = ANGULAR_LIMIT;
        else if (omega < (-ANGULAR_LIMIT))
            omega = -ANGULAR_LIMIT;

        body.setLinearVelocity(x, y);
        body.setAngularVelocity(omega);
    }


    public void update(float delta) {

        this.gameModel.update(delta);

        float frameTime = Math.min(delta, 0.25f);

        accumulator += frameTime;

        applyGravityToPlanets();

        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);




        for (Body body : bodies) {
            verifyBounds(body);
            limitVelocities(body);
            ((ElementModel) body.getUserData()).setX(body.getPosition().x);
            ((ElementModel) body.getUserData()).setY(body.getPosition().y);
            //((ElementModel) body.getUserData()).setRotation(body.getAngle());

        }
        getPlayerRotation(delta);


    }

    private void getPlayerRotation(float delta) {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        Float distance=0f;

        Float angle=0f;
        for (ElementController planet:planetControllers){
            distance= abs(planet.getX()- playerController.getX());
            distance+=abs(planet.getY()- playerController.getY());

            if(distance <5){
                //System.out.println(playerController.getAngle());
                //System.out.println((float) ( (planet.getY() - playerController.getY()) / (planet.getX()- playerController.getX()) * 180.0d / Math.PI));

                PlayerModel p =  ((PlayerModel) playerController.getUserData());

                pRot=  (float) ( (planet.getY() - playerController.getY()) / (planet.getX()- playerController.getX()) * 180.0d / Math.PI);
                newRot+= (pRot-newRot)*delta;

               // System.out.println(Math.toDegrees(Math.atan2(planet.getY() - playerController.getY(), planet.getX()- playerController.getX())));
                //Vector2()
                double degrees =  (Math.toDegrees(Math.atan2(planet.getY() - playerController.getY(), planet.getX()- playerController.getX())));
                degrees+=90;

                //if(pRot< pRot+
                pRot= (float)Math.toRadians(degrees);
               //pRot-=Mat;
                 System.out.println(pRot);
                ((PlayerModel) playerController.getUserData()).setRotation(pRot);
            }
            System.out.println(pRot);
            //System.out.println(delta);


            //System.out.println(playerController.getAngle());

            /*
             pRotation = ((tPos.y - pPos.y) / (tPos.x - pPos.x) * 180.0d / Math.PI);
            pNewRotation += (pRotation - pNewRotation) * Gdx.graphics.getDeltaTime();
             */
          //  System.out.println(playerController.getAngle()-);

           // velocity.set(planet.getX() - playerController.getX(),planet.getY() - playerController.getY()).nor().scl(playerController.dst(planet.getX(), planet.getY())));
            //System.out.println(distance);

        }

    }



    private void verifyBounds(Body body) {
        if (body.getPosition().x < 0)
            body.setTransform(ARENA_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < 0)
            body.setTransform(body.getPosition().x, ARENA_HEIGHT, body.getAngle());

        if (body.getPosition().x > ARENA_WIDTH)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > ARENA_HEIGHT)
            body.setTransform(body.getPosition().x, 0, body.getAngle());
    }

    @Override
    public void beginContact(Contact contact) {

        Body A = contact.getFixtureA().getBody();
        Body B = contact.getFixtureB().getBody();

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


    /*
    public void moveLeft(float delta) {
        playerController.applyForceToCenter(-10,0,true);
        ((PlayerModel) playerController.getUserData()).setAccelerating(true);


    }

    public void moveRight(float delta) {
        playerController.applyForceToCenter(10,0,true);
        ((PlayerModel) playerController.getUserData()).setAccelerating(true);
    }


    public void moveUp(float delta) {
        playerController.applyForceToCenter(0,10,true);
        ((PlayerModel) playerController.getUserData()).setAccelerating(true);
    }

    public void moveDown(float delta) {
        playerController.applyForceToCenter(10,-10,true);
        ((PlayerModel) playerController.getUserData()).setAccelerating(true);
    }

    public void rotateLeft(float delta) {
        ((PlayerModel) playerController.getUserData()).setRotation(10);
        ((PlayerModel) playerController.getUserData()).setAccelerating(true);
    }

    */
}
