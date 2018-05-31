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

public class ViewFactory {

    private static Map<ElementModel, ElementView> cache = new HashMap<ElementModel, ElementView>();

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

    public static <T extends ElementModel> void drawAllElements(List<T> models, GameView gv) {

        for (T e : models) {

            drawElement(e, gv);

        }

    }

    public static <T extends ElementModel> void updatePauseElements(List<T> models, GameView gv, boolean pause) {

        for (T e : models) {
            updatePause(e, gv, pause);
        }

    }

    public static <T extends ElementModel> void drawElement(T model, GameView gv) {

        ElementView view = ViewFactory.makeView(gv.game, model);
        view.update(model);
        view.draw(gv.game.getBatch());

    }

    public static <T extends ElementModel> void updatePause(T model, GameView gv, boolean pause) {

        ElementView view = ViewFactory.makeView(gv.game, model);
        view.setStopFrame(pause);

    }
}
