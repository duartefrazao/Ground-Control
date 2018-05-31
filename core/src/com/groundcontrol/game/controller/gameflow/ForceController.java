package com.groundcontrol.game.controller.gameflow;

import com.badlogic.gdx.math.Vector2;

public class ForceController {

    private final static int minimumVy = 6;
    private final static int absoluteMax = 27;
    private final static float timeToVelocityRatio = 4f / 70f;
    private final static float initialMaxVelocity = 10;
    private final static float minMult = 0.7f;
    private final static float maxMult = 1.4f;
    private final static float accelaration = 0.8f;
    private static int currentMax = 15;
    private Vector2 force;
    private double elapsedTime;

    public ForceController() {

        force = new Vector2(0, -minimumVy);

        elapsedTime = 0;

    }

    public void updateForce(float delta, float x, float y) {

        elapsedTime += delta;
        force.x += x * accelaration;
        force.y += y * accelaration;
        updateMaxForceValue();

        force.limit(currentMax);
    }

    public Vector2 getForceMult() {

        float randomMultiplier = (float) (minMult + Math.random() * (maxMult - minMult));

        Vector2 randomForce = new Vector2();

        randomForce.x = this.force.x * randomMultiplier;

        randomForce.y = this.force.y * randomMultiplier;

        return randomForce;


    }

    private void updateMaxForceValue() {

        if (currentMax >= absoluteMax)
            return;

        currentMax = (int) (timeToVelocityRatio * elapsedTime + initialMaxVelocity);

    }

}
