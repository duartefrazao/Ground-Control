package com.groundcontrol.game.model;

import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.groundcontrol.game.controller.GameController.ARENA_HEIGHT;
import static com.groundcontrol.game.controller.GameController.ARENA_WIDTH;

public class GameModel {


    /**
     * Player character
     */
    private PlayerModel player;


    private static final int PLANET_COUNT = 10;

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

    }

    public void update(float delta) {

        //TODO
        return;

    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public List<PlanetModel> getPlanets() {
        return planets;
    }

    public void setPlanets(List<PlanetModel> planets) {
        this.planets = planets;
    }
}
