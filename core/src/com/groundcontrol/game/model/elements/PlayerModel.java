package com.groundcontrol.game.model.elements;

/**
 * Player Model
 */
public class PlayerModel extends ElementModel {

    private boolean rightSide;

    private animationState currentState;

    /**
     * Creates a new player model in a position
     *
     * @param x        player's x position
     * @param y        player's y position
     * @param rotation player's rotation
     */
    public PlayerModel(float x, float y, float rotation) {
        super(x, y, rotation);

        this.rightSide = true;

        this.currentState = animationState.IDLE;

    }

    @Override
    public ModelType getType() {
        return ModelType.Player;
    }

    /**
     * Returns the current animation state
     * @return the current animation state
     */
    public animationState getCurrentState() {
        return this.currentState;
    }

    /**
     * Changes the current animation state
     * @param state
     */
    public void setCurrentState(animationState state) {
        this.currentState = state;
    }

    /**
     * Checks if the player is facing the right side
     * @return true if right side, false otherwise
     */
    public boolean isRightSide() {
        return rightSide;
    }

    /**
     * Sets the player to face a certain side
     * @param side if true, facing right, left otherwise
     */
    public void setRightSide(boolean side) {
        this.rightSide = side;
    }

    /**
     * Player possible animations
     */
    public enum animationState {IDLE, RUNNING}
}
