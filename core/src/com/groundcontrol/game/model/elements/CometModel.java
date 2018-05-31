package com.groundcontrol.game.model.elements;

/**
 * Comet Model
 */
public class CometModel extends ElementModel {

    private float time;

    private final static float INITIAL_TIME = 6f;

    /**
     * Creates a new comet model in a position
     * @param x model's x position
     * @param y model's y position
     * @param rotation model's rotation
     */
    public CometModel(float x, float y, float rotation){
        super(x,y, rotation);
        this.time = INITIAL_TIME;
    }

    /**
     * Checks if a comet has gone out of bonds
     * @param delta the time elapsed after the last step
     * @return true if out of bonds, false otherwise
     */
    public boolean isOutOfBonds(float delta){

        time -= delta;

        return time <= 0;

    }

    /**
     * Resets the comet time
     */
    public void resetTime(){
        this.time = INITIAL_TIME;
    }

    @Override
    public ModelType getType() {
        return ModelType.Comet;
    }

}
