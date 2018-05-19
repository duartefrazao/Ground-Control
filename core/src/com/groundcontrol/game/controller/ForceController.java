package com.groundcontrol.game.controller;

import com.badlogic.gdx.math.Vector2;

public class ForceController {

    private Vector2 force;

    private int minimumVy = 6;

    private int maxForceValue = 25;

    public ForceController(){

        force = new Vector2(0, -minimumVy);

    }


    public void updateForce(float x, float y){

        force.x += x;
        force.y += y;

        force.limit(maxForceValue);
    }

    public Vector2 getForce() {return force;}

}
