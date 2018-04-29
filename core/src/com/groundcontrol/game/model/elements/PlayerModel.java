package com.groundcontrol.game.model.elements;

public class PlayerModel extends ElementModel {

    private boolean accelerating=true;

    public PlayerModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }

    @Override
    public ModelType getType() {
        return ModelType.Player;
    }

    public void setAccelerating(boolean b) {
        accelerating=b;
    }

    public boolean isAccelerating(){
        return accelerating;
    }
}
