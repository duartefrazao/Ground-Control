package TestLogic;

import com.groundcontrol.game.model.GameModel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestModelCreation {
    @Test
    public void testDefaultPlanetsNumber(){
        GameModel gm = new GameModel();
        assertEquals(7,gm.getPlanets().size());
    }

    @Test
    public void cometCreation(){
        
    }
}
