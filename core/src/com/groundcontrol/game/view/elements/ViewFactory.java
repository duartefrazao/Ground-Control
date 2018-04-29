package com.groundcontrol.game.view.elements;

import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.model.elements.ElementModel;

import java.util.HashMap;
import java.util.Map;

import static com.groundcontrol.game.model.elements.ElementModel.ModelType.BigPlanet;
import static com.groundcontrol.game.model.elements.ElementModel.ModelType.MediumPlanet;
import static com.groundcontrol.game.model.elements.ElementModel.ModelType.Player;

public class ViewFactory {

    private static Map<ElementModel, ElementView> cache = new HashMap<ElementModel, ElementView>();

    public static ElementView makeView(GroundControl game, ElementModel model){

        if(!cache.containsKey(model)){
            if(model.getType()== BigPlanet)
                cache.put(model, new BigPlanetView(game));
            else if(model.getType()== MediumPlanet)
                cache.put(model, new PlanetView(game));
            else if(model.getType() == Player)
                cache.put(model, new PlayerView(game));
        }

        return cache.get(model);

    }

}
