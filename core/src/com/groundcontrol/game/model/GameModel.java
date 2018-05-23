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


public class GameModel {

    /**
     * Player character
     */
    private PlayerModel player;


    private static final int PLANET_COUNT = 7;

    private int score = 0;

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


    Pool<CometModel> cometPoll = new Pool<CometModel>(){

        @Override
        protected CometModel newObject(){
            return new CometModel(0, 0, 0);
        }

    };


    public GameModel() {
        planets = new ArrayList<PlanetModel>();
        comets = new ArrayList<CometModel>();
        explosions = new ArrayList<ExplosionModel>();
        player = new PlayerModel(ARENA_WIDTH / 2, ARENA_HEIGHT / 2, 0);


        for (int i = 0; i < PLANET_COUNT; i++) {
            planets.add(new PlanetModel(
                    random.nextFloat() * ARENA_WIDTH,
                    random.nextFloat() * ARENA_HEIGHT,
                    (float) Math.toRadians(random.nextFloat() * 360),
                    random.nextBoolean() ? PlanetModel.PlanetSize.BIG : PlanetModel.PlanetSize.MEDIUM));
        }


    }

    public void update(float delta) {

        Iterator<ExplosionModel> iter = this.explosions.iterator();

        while(iter.hasNext()){

            ExplosionModel em = iter.next();

            if(em.isExplosionOver(delta)){

                iter.remove();

            }

        }

    }

    public void setScore(int score){ this.score = score;}

    public int getScore(){ return this.score; }

    public PlayerModel getPlayer() {
        return player;
    }

    public List<PlanetModel> getPlanets() {
        return planets;
    }

    public List<CometModel> getComets(){
        return comets;
    }

    public List<ExplosionModel> getExplosions(){
        return explosions;
    }

    public CometModel createComet(float x, float y){

        CometModel comet = cometPoll.obtain();

        comet.setToBeRemoved(false);
        comet.setX(x);
        comet.setY(y);
        comet.setRotation(0);

        comets.add(comet);

        return comet;
    }

    public PlanetModel createPlanet(float x, float y){

        PlanetModel planet = new PlanetModel(x, y, 0, random.nextBoolean() ? PlanetModel.PlanetSize.BIG : PlanetModel.PlanetSize.MEDIUM);

        planet.setToBeRemoved(false);

        planets.add(planet);

        return  planet;
    }

    public void createExplosion(float x, float y){

        ExplosionModel explosion = new ExplosionModel(0, 0, 0);

        explosion.setToBeRemoved(false);
        explosion.setX(x);
        explosion.setY(y);
        explosion.setRotation(0);
        explosion.initializeExplosionTime();

        explosions.add(explosion);
    }

    public void removeModel(ElementModel model){

        if (model instanceof  PlanetModel)
            this.planets.remove(model);
        else if (model instanceof CometModel){
            this.comets.remove(model);
            this.cometPoll.free((CometModel) model);
        }


    }

}
