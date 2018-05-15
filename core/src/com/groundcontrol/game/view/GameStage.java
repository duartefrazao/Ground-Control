package com.groundcontrol.game.view;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.groundcontrol.game.GroundControl;

public class GameStage extends Stage{

   // private final World world;

    GameStage(GroundControl game){
        //world= new World()
    }

    @Override
    public void act(float delta){
        super.act();

        //world.step(delta,6,2);
    }


}
