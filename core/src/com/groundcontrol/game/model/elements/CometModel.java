package com.groundcontrol.game.model.elements;

public class CometModel extends ElementModel {

    private float time;

    private final static float INITIAL_TIME = 6f;

    public CometModel(float x, float y, float rotation){
        super(x,y, rotation);
        this.time = INITIAL_TIME;
    }

    public boolean isOutOfBonds(float delta){

        time -= delta;

        return time <= 0;

    }

    public void resetTime(){
        this.time = INITIAL_TIME;
    }

    @Override
    public ModelType getType() {
        return ModelType.Comet;
    }

}
