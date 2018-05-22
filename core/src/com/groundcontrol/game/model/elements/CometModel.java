package com.groundcontrol.game.model.elements;

public class CometModel extends ElementModel {

    public CometModel(float x, float y, float rotation){
        super(x,y, rotation);
    }

    @Override
    public ModelType getType() {
        return ModelType.Comet;
    }

}
