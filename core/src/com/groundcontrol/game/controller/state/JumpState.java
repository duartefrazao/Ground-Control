package com.groundcontrol.game.controller.state;

import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.view.GameView.StateInput;

import java.util.ArrayList;

public class JumpState implements PlayerState {



    public PlayerState handleInput(PlayerController context, StateInput input, ArrayList<PlanetController> planets){

        PlanetController nearest =closeToPlanet(context, planets);

        if(nearest == null) return null;

        //Process gravitational force

        

        return null;
    }

    private PlanetController closeToPlanet(PlayerController context, ArrayList<PlanetController> planets) {
        int distance;
        PlanetController nearestPlanet = null;
        for(PlanetController planet : planets){
            distance = (int) (Math.abs(planet.getX() - context.getX()) +  Math.abs(planet.getY() - context.getY()));

            if(distance <5) nearestPlanet= planet;
        }

        return nearestPlanet;
    }

    @Override
    public void enter(PlayerController context) {

    }
}
