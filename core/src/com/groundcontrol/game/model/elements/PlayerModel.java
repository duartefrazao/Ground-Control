package com.groundcontrol.game.model.elements;

public class PlayerModel extends ElementModel {

    private boolean accelerating=true;

    private boolean rightSide = true;

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

    public boolean isRightSide(){
        return rightSide;
    }

    public void setRightSide(boolean side){
        this.rightSide = side;
    }
}
