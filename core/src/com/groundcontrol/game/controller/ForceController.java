package com.groundcontrol.game.controller;

import com.badlogic.gdx.math.Vector2;

public class ForceController {

    private Vector2 force;

    private int minimumVy = 6;

    private int currentMax = 15;

    private int absoluteMax = 30;

    private double elapsedTime;

    public ForceController() {

        force = new Vector2(0, -minimumVy);

        elapsedTime = 0;

    }


    public void updateForce(float delta, float x, float y) {

        elapsedTime += delta;
        force.x += x;
        force.y += y;
        updateMaxForceValue();

        force.limit(currentMax);
    }

    public Vector2 getForce() {
        return force;
    }

    private void updateMaxForceValue() {

        if (currentMax >= absoluteMax)
            return;

        currentMax = (int) ((9f / 37f) * elapsedTime + 10);

    }

}
