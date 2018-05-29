package com.groundcontrol.game.controller.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.model.elements.PlayerModel;

import static java.lang.Math.abs;

public class FloatState implements PlayerState {


    private final static float INTIAL_TIME = 30f;

    private static float floatTime = INTIAL_TIME;

    public float getTime(){
        return this.floatTime;
    }

    public FloatState(){
        this.floatTime = INTIAL_TIME;
    }

    public FloatState(float time){
        floatTime = time;
    }

    public void updateTime(PlayerController context, float delta){

    }

    @Override
    public void handleInput(PlayerController context, InputDecoder.Input input) {

        if (input == InputDecoder.Input.PLANET_LAND) {
            context.setState(new IdleState(this.floatTime));
        }

    }

    public void setRotation(PlayerController context, Array<Body> objects) {

        for (Body e : objects) {

            if(e == null) {
                continue;
            }
            else if(e.getUserData() instanceof PlayerModel)
                continue;

            float distance = abs(e.getPosition().x - context.getX());
            distance += abs(e.getPosition().y - context.getY());

            if (distance < 8) {

                context.setTransform(context.getX(), context.getY(), context.getAngleBetween(e));

            }

        }

    }


    public void applyPullForce(PlayerController context, Array<Body> objects) {

        for (Body e : objects) {
            Vector2 force = context.calculatePullForce(e);
            context.applyForceToCenter(force, true);
        }

    }
}
