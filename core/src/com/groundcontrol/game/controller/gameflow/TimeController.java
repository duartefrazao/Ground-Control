package com.groundcontrol.game.controller.gameflow;

public abstract class TimeController {

    protected double currTime;

    protected float elapsedTime;

    protected int currentValue;

    protected float timeToValueRation;

    protected int valueMultiplier;

    protected float initialValue;


    public void update(float delta) {
        currTime += delta;
        elapsedTime += delta;
        updateValueMultiplier();
        updateValue();
    }

    protected void updateValueMultiplier() {

        valueMultiplier = (int) (timeToValueRation * elapsedTime + initialValue);

    }

    protected abstract void updateValue();



}
