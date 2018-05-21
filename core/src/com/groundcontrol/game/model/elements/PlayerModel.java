package com.groundcontrol.game.model.elements;


public class PlayerModel extends ElementModel {

    private boolean rightSide;

    private animationState currentState;

    public enum animationState {IDLE, RUNNING, JUMPING }

    public PlayerModel(float x, float y, float rotation) {
        super(x, y, rotation);

        this.rightSide = true;

        this.currentState = animationState.IDLE;

    }

    @Override
    public ModelType getType() {
        return ModelType.Player;
    }

    public animationState getCurrentState() { return this.currentState; }

    public void setCurrentState(animationState state){
        this.currentState = state;
    }

    public boolean isRightSide(){
        return rightSide;
    }

    public void setRightSide(boolean side){
        this.rightSide = side;
    }
}
