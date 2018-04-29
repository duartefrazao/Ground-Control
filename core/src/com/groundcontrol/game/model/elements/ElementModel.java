package com.groundcontrol.game.model.elements;

public abstract class ElementModel {

    public enum ModelType{ BigPlanet, MediumPlanet, Player};

    private float x;

    private float y;

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
}
