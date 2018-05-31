package com.groundcontrol.game.model;

import com.badlogic.gdx.utils.Pool;
import com.groundcontrol.game.model.elements.CometModel;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.ExplosionModel;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

/**
 * MVC Model
 * Simple class that only has the information relative to the position and so on of the game elements.
 */
public class GameModel {

    /**
     * Number of Planets
     */
    private static final int PLANET_COUNT = 7;
    Pool<CometModel> cometPoll = new Pool<CometModel>() {

        @Override
        protected CometModel newObject() {
            return new CometModel(0, 0, 0);
        }

    };
    /**
     * Player character
     */
    private PlayerModel player;
    /**
     * Time Left
     */
    private float timeLeft = 0;
    /**
     * Planets
     */
    private List<PlanetModel> planets;
    /**
     * Comets
     */
    private List<CometModel> comets;
    /**
     * Explosions
     */
    private List<ExplosionModel> explosions;

    /**
     * Default constructor
     * Creates all the needed element model
     */
    public GameModel() {
        planets = new ArrayList<PlanetModel>();
        comets = new ArrayList<CometModel>();
        explosions = new ArrayList<ExplosionModel>();
        player = new PlayerModel(ARENA_WIDTH / 2, ARENA_HEIGHT / 2, 0);


        for (int i = 0; i < PLANET_COUNT; i++) {
            planets.add(new PlanetModel(
                    random.nextFloat() * ARENA_WIDTH,
                    random.nextFloat() * ARENA_HEIGHT,
                    (float) Math.toRadians(random.nextFloat() * 360)));
        }


    }

    /**
     * Updates the model in each step.
     * Checks if the explosions are over, and if so removes them.
     * Checks if the comets are out of bonds, and if so removes them.
     *
     * @param delta time elapsed after the last step
     */
    public void update(float delta) {

        Iterator<ExplosionModel> iterator = this.explosions.iterator();

        while (iterator.hasNext()) {

            ExplosionModel em = iterator.next();

            if (em.isExplosionOver(delta)) {

                iterator.remove();

            }

        }


        for (CometModel comet : this.comets) {

            if (comet.isOutOfBonds(delta))
                comet.setToBeRemoved(true);

        }

    }

    /**
     * Returns the time that the player still has left
     *
     * @return time
     */
    public float getTimeLeft() {
        return this.timeLeft;
    }

    /**
     * Sets the player's timer
     *
     * @param time
     */
    public void setTimeLeft(float time) {
        this.timeLeft = time;
    }

    /**
     * Returns the player model
     *
     * @return player
     */
    public PlayerModel getPlayer() {
        return player;
    }

    /**
     * Returns a list containing all the planets
     *
     * @return planet's list
     */
    public List<PlanetModel> getPlanets() {
        return planets;
    }

    /**
     * Returns a list containing all the comets
     *
     * @return comet's list
     */
    public List<CometModel> getComets() {
        return comets;
    }

    /**
     * Returns a list containing all the explosions
     *
     * @return explosion's list
     */
    public List<ExplosionModel> getExplosions() {
        return explosions;
    }

    /**
     * Creates a new comet in a given position
     *
     * @param x - the x position
     * @param y - the y position
     * @return the comet model
     */
    public CometModel createComet(float x, float y) {

        CometModel comet = cometPoll.obtain();

        comet.setToBeRemoved(false);
        comet.setX(x);
        comet.setY(y);
        comet.setRotation(0);
        comet.resetTime();

        comets.add(comet);

        return comet;
    }

    /**
     * Adds a new planet to the list
     *
     * @param model
     */
    public void addPlanet(PlanetModel model) {
        planets.add(model);
    }

    /**
     * Creates a new explosion in a given position
     *
     * @param x - x position
     * @param y - y position
     */
    public void createExplosion(float x, float y) {

        ExplosionModel explosion = new ExplosionModel(x, y, 0);

        explosion.setToBeRemoved(false);
        explosion.setRotation(0);
        explosion.initializeExplosionTime();

        explosions.add(explosion);
    }

    /**
     * Deletes a model
     *
     * @param model to be deleted
     */
    public void removeModel(ElementModel model) {

        if (model instanceof PlanetModel) {
            this.planets.remove(model);
        } else if (model instanceof CometModel) {
            this.comets.remove(model);
            this.cometPoll.free((CometModel) model);
        }
    }

}
