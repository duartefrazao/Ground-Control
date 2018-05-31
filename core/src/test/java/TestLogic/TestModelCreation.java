package TestLogic;

import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.CometModel;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestModelCreation {
    @Test
    public void testDefaultPlanetsNumber(){
        GameModel gm = new GameModel();
        assertEquals(7,gm.getPlanets().size());
    }

    @Test
    public void testCometCreation(){
        GameModel gm = new GameModel();
        int previousSize = gm.getComets().size();
        CometModel cm = gm.createComet(5,6);
        int afterSize = gm.getComets().size();
        assertEquals(1, afterSize-previousSize);
        assertEquals(5, cm.getX(), 0.1);
        assertEquals(6, cm.getY(),0.1);
        assertEquals(0,cm.getRotation(),0.1);

    }

    @Test
    public void testCreateExplosion(){
        GameModel gm = new GameModel();

        int previousSize = gm.getExplosions().size();

        gm.createExplosion(5, 5);

        int afterSize = gm.getExplosions().size();

        assertEquals(1, afterSize - previousSize);

        gm.update(3);

        assertEquals(0, gm.getExplosions().size());

    }


    @Test
    public void testRemoveComet(){
        GameModel gm = new GameModel();
        CometModel cm = gm.createComet(5,5);


        assertEquals(1,gm.getComets().size());
        gm.removeModel(cm);
        assertEquals(0,gm.getComets().size());

        CometModel cm1 =   gm.createComet(1, 1);
        CometModel cm2 = gm.createComet(2, 2);

        assertEquals(2, gm.getComets().size());

        gm.update(20);

        assertEquals(cm1.isToBeRemoved(), true);
        assertEquals(cm2.isToBeRemoved(), true);

        gm.removeModel(cm1);
        gm.removeModel(cm2);

        assertEquals(0, gm.getComets().size());
    }

    @Test
    public void testAddRemovePlanet(){
        GameModel gm = new GameModel();
        int previousSize = gm.getPlanets().size();
        PlanetModel newPlanet = new PlanetModel(5, 5, 0);
        gm.addPlanet(newPlanet);
        int afterSize = gm.getPlanets().size();

        assertEquals(1, afterSize-previousSize);

        gm.removeModel(newPlanet);

        int afterRemoveSize = gm.getPlanets().size();

        assertEquals(previousSize, afterRemoveSize);
    }

    @Test(timeout=1000)
    public void testRandomPlanetSizeGeneration(){

        HashSet<PlanetModel.PlanetSize> en = new HashSet<PlanetModel.PlanetSize>();

        while(en.size()< PlanetModel.PlanetSize.values().length){
            GameModel gm = new GameModel();

            for(PlanetModel p : gm.getPlanets()){
                en.add(p.getSize());
            }
        }
    }

    @Test(timeout=1000)
    public void testRandomPlanetTypeGeneration(){

        HashSet<ElementModel.ModelType> en = new HashSet<ElementModel.ModelType>();

        while(en.size()< 2){
            GameModel gm = new GameModel();

            for(PlanetModel p : gm.getPlanets()){
                en.add(p.getType());
            }
        }
    }

    @Test(timeout = 1000)
    public void testPlayerGeneration(){

        HashSet<ElementModel.ModelType> en = new HashSet<ElementModel.ModelType>();

        while(en.size()< 1){
            GameModel gm = new GameModel();

            en.add(gm.getPlayer().getType());
        }
    }

    @Test
    public void testPlayer(){
        GameModel gm = new GameModel();

        PlayerModel player = gm.getPlayer();

        assertEquals(PlayerModel.animationState.IDLE, player.getCurrentState());
        assertEquals(true, player.isRightSide());


        player.setCurrentState(PlayerModel.animationState.RUNNING);

        assertEquals(PlayerModel.animationState.RUNNING, player.getCurrentState());

        player.setRightSide(false);

        assertEquals(false, player.isRightSide());

    }

}
