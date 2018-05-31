package com.groundcontrol.game.view.elements;

import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.view.GameView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groundcontrol.game.model.elements.ElementModel.ModelType.BigPlanet;
import static com.groundcontrol.game.model.elements.ElementModel.ModelType.Comet;
import static com.groundcontrol.game.model.elements.ElementModel.ModelType.Explosion;
import static com.groundcontrol.game.model.elements.ElementModel.ModelType.MediumBigPlanet;
import static com.groundcontrol.game.model.elements.ElementModel.ModelType.MediumPlanet;
import static com.groundcontrol.game.model.elements.ElementModel.ModelType.Player;
import static com.groundcontrol.game.model.elements.ElementModel.ModelType.SmallPlanet;


/**
 * Factory that creates view for all the gameModel elements, using the factory pattern
 */
public class ViewFactory {

    private static Map<ElementModel, ElementView> cache = new HashMap<ElementModel, ElementView>();

    /**
     * Creates a view following a model to the game
     * @param game the game being played
     * @param model the model to which the view will be created
     * @return the model view
     */
    public static ElementView makeView(GroundControl game, ElementModel model) {

        if (!cache.containsKey(model)) {
            if (model.getType() == BigPlanet)
                cache.put(model, new BigPlanetView(game));
            else if (model.getType() == MediumBigPlanet)
                cache.put(model, new MediumBigPlanetView(game));
            else if (model.getType() == MediumPlanet)
                cache.put(model, new MediumPlanetView(game));
            else if (model.getType() == SmallPlanet)
                cache.put(model, new SmallPlanetView(game));
            else if (model.getType() == Player)
                cache.put(model, new PlayerView(game));
            else if (model.getType() == Comet)
                cache.put(model, new CometView(game));
            else if (model.getType() == Explosion)
                cache.put(model, new ExplosionView(game));

        }

        return cache.get(model);

    }


    /**
     * Draws all the list element into the game current section
     * @param models the models to be drawn
     * @param gv the game view
     * @param <T> any class that extends Element Model
     */
    public static <T extends ElementModel> void drawAllElements(List<T> models, GameView gv) {

        for (T e : models) {

            drawElement(e, gv);

        }

    }

    /**
     * Pauses or unpauses all the animations present in models
     * @param models the models to be paused/unpaused
     * @param gv the game view
     * @param pause true to be paused, false will contnue
     * @param <T> any class that extends Element Model
     */
    public static <T extends ElementModel> void updatePauseElements(List<T> models, GameView gv, boolean pause) {

        for (T e : models) {
            updatePause(e, gv, pause);
        }

    }

    /**
     * Draws the view of a model into the game view
     * @param model the model to be drawn
     * @param gv the game view
     * @param <T> any class that extends ElementModel
     */
    public static <T extends ElementModel> void drawElement(T model, GameView gv) {

        ElementView view = ViewFactory.makeView(gv.game, model);
        view.update(model);
        view.draw(gv.game.getBatch());

    }

    /**
     * Pauses/Unpauses a certain element
     * @param model the model
     * @param gv the gameview
     * @param pause true if to be paused, false to continue
     * @param <T> any class that extends Element Model
     */
    public static <T extends ElementModel> void updatePause(T model, GameView gv, boolean pause) {

        ElementView view = ViewFactory.makeView(gv.game, model);
        view.setStopFrame(pause);

    }
}
