package com.groundcontrol.game.model;

import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;



public class GameModel {


    private static final boolean DEBUG = false;

    /**
     * Player character
     */
    private PlayerModel player;


    private static final int PLANET_COUNT = 15;

    private int score = 0;

    /**
     * Planets
     */
    private List<PlanetModel> planets;

    public GameModel() {
        planets = new ArrayList<PlanetModel>();
        player = new PlayerModel(5, 5, 0);


        for (int i = 0; i < PLANET_COUNT; i++) {
            planets.add(new PlanetModel(
                    random.nextFloat() * ARENA_WIDTH,
                    random.nextFloat() * ARENA_HEIGHT,
                    (float) Math.toRadians(random.nextFloat() * 360),
                    random.nextBoolean() ? PlanetModel.PlanetSize.BIG : PlanetModel.PlanetSize.MEDIUM));
        }


        /*
         *  Test Purposes
         */

        if(DEBUG) {
            for (int i = 0; i < 1; i++) {
                planets.add(new PlanetModel(
                        17,
                        15,
                        (float) Math.toRadians(random.nextFloat() * 360),
                        random.nextBoolean() ? PlanetModel.PlanetSize.BIG : PlanetModel.PlanetSize.MEDIUM));
            }

        }

    }

    public void update(float delta) {

        //TODO
        return;

    }

    public void setScore(int score){ this.score = score;}

    public int getScore(){ return this.score; }

    public PlayerModel getPlayer() {
        return player;
    }

    public List<PlanetModel> getPlanets() {
        return planets;
    }

}
