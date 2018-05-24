package TestLogic;

import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.CometModel;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlanetModel;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class TestModelCreation {
    @Test
    public void testDefaultPlanetsNumber(){
        GameModel gm = new GameModel();
        assertEquals(7,gm.getPlanets().size());
    }

    @Test
    public void testCometCreation(){
        GameModel gm = new GameModel();
        CometModel cm = gm.createComet(5,6);
        assertEquals(5, cm.getX(), 0.1);
        assertEquals(6, cm.getY(),0.1);
        assertEquals(0,cm.getRotation(),0.1);
    }

    @Test
    public void testInitialScore(){
        GameModel gm = new GameModel();
        assertEquals(0,gm.getScore());
    }

    @Test
    public void testRemoveModel(){
        GameModel gm = new GameModel();
        CometModel cm = gm.createComet(5,5);

        assertEquals(1,gm.getComets().size());
        gm.removeModel(cm);
        assertEquals(0,gm.getComets().size());
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
}
