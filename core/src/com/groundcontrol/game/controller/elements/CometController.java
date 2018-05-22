package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.model.elements.ElementModel;


public class CometController extends  ElementController{

    private Vector2 initialVelocity = new Vector2(15 * ( MathUtils.random.nextBoolean() ? 1 : -1 ), 15 * ( MathUtils.random.nextBoolean() ? 1 : -1 ));


    private float maxAngularVelocity = 0.5f;

    public  float getMaxVelocity(){
        return this.initialVelocity.len();
    }

    public float getMaxAngular(){
        return this.maxAngularVelocity;
    }

    public CometController(World world, ElementModel model){

        super(world, model, BodyDef.BodyType.DynamicBody);

        float density = 11000f,
                friction = 1f,
                restitution = 0.0f;
        this.width = 233;
        this.height = 400;

        createFixture(body, new float[]{
                    94, 283,
                    57, 316,
                    73, 346,
                    142, 357,
                    169, 334,
                    139, 293,
                    109, 289,
                    99, 295
                }, width, height, density, friction, restitution, COMET_BODY, (short) (COMET_BODY |PLANET_BODY | PLAYER_BODY));

        this.body.setGravityScale(0);

    }

    public void applyInitialVelocity(){

        this.body.setLinearVelocity(initialVelocity);

        this.body.setTransform(this.body.getPosition().x, this.body.getPosition().y,(float) Math.PI - this.initialVelocity.angleRad());



    }

}
