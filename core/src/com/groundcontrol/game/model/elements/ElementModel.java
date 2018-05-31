package com.groundcontrol.game.model.elements;

/**
 * Abstract element model.
 * Represents all the elements in our model
 */
public abstract class ElementModel {

    /**
     * Different type of models
     */
    public enum ModelType{ BigPlanet, MediumPlanet, MediumBigPlanet, SmallPlanet,  Player, Comet, Explosion}

    private float x;

    private float y;

    private boolean toBeRemoved = false;

    /**
     * Returns the model rotation
     * @return rotation
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the model rotation
     * @param rotation the new rotation
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    private float rotation;

    /**
     * Creates a new model in a given position with a given rotation
     * @param x x-position
     * @param y y-position
     * @param rotation rotation
     */
    ElementModel(float x, float y, float rotation){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     * Returns the X position of the model
     * @return x position
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x position of the model
     * @param x the new x position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Returns the Y position of the model
     * @return y position
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y position of the model
     * @param y the new x position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Tells the type of the model
     * @return the model type
     */
    public abstract ModelType getType();

    /**
     * Tells if a model is to be removed in the next step or not
     * @return true if so, false otherwise
     */
    public boolean isToBeRemoved(){
        return toBeRemoved;
    }

    /**
     * Sets a model to be removed or not
     * @param flag
     */
    public void setToBeRemoved(boolean flag){
        this.toBeRemoved = flag;
    }
}
