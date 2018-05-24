package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.groundcontrol.game.controller.elements.PlayerController;

import java.util.ArrayList;

public class IdleState implements PlayerState {

    private float timeInPlanet;

    public void updateTime(PlayerController context, float delta){
        this.timeInPlanet += delta;

        if(this.timeInPlanet > context.getMaxTimeInPlanet()){

            //((ElementModel) context.getPlanet().getUserData()).setToBeRemoved(true);
            context.jump();

        }

    }

    public IdleState(float time){
        this.timeInPlanet = time;
    }

    public IdleState(){
        this.timeInPlanet = 0;
    }

    public void handleInput(PlayerController context, InputDecoder.Input input) {

        if (input == InputDecoder.Input.JUMP) {
            context.jump();
        } else if (input == InputDecoder.Input.RIGHT || input == InputDecoder.Input.LEFT) {
            context.setState(new RunningState(this.timeInPlanet));
        }

    }

    public void setRotation(PlayerController context, ArrayList<Body> objects) {

        context.setTransform(context.getX(), context.getY(), context.getAngleBetween(context.getPlanet()));

    }

    public void applyPullForce(PlayerController context, ArrayList<Body> objects) {
        Vector2 force = context.calculatePullForce(context.getPlanet());
        context.applyForceToCenter(force, true);
    }
}
