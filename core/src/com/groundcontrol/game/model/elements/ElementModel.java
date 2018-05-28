package com.groundcontrol.game.model.elements;

public abstract class ElementModel {

    public enum ModelType{ BigPlanet, MediumPlanet, MediumBigPlanet, SmallPlanet,  Player, Comet, Explosion}

    private float x;

    private float y;

    private boolean toBeRemoved = false;

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    private float rotation;

    ElementModel(float x, float y, float rotation){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public abstract ModelType getType();

    public boolean isToBeRemoved(){
        return toBeRemoved;
    }

    public void setToBeRemoved(boolean flag){
        this.toBeRemoved = flag;
    }
}
