package TestController;

import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestControllerCreation {
    @Test
    public void testDefaultPlanetsNumber(){
        GameModel gm= new GameModel();
        GameController gc = new GameController(gm);
    }
}
