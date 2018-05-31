package com.groundcontrol.game.view.ScreenModules;

import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.elements.ElementView;
import com.groundcontrol.game.view.elements.ViewFactory;

import java.util.List;

public class Utils {

    public static <T extends ElementModel> void drawAllElements(List<T> models, GameView gv) {

        for (T e : models) {

            ElementView view = ViewFactory.makeView(gv.game, e);
            view.update(e);
            view.draw(gv.game.getBatch());

        }

    }

    public static <T extends ElementModel> void drawPausedElements(List<T> models, GameView gv){

        for(T e : models){
            ElementView view = ViewFactory.makeView(gv.game, e);
            view.setStopFrame();
        }



    }

}
