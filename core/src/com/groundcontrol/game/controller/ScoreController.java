package com.groundcontrol.game.controller;

import com.badlogic.gdx.utils.TimeUtils;

public class ScoreController {

    private double time;

    private int score;

    public ScoreController(){

        this.time = 0;
        score = 0;

    }

    public int getScore(){
        return this.score;
    }

    public void update(float delta){
        time += delta;
        updateScore();
    }

    private void updateScore(){

        if(time >= 0.05){
            score += 2;
            time = 0;
        }


    }
}
