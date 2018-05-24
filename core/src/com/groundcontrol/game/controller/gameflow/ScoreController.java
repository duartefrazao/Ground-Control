package com.groundcontrol.game.controller.gameflow;

public class ScoreController {

    private double time;

    private float elapsedTime;

    private int score;

    private float timeToScoreRatio = 9f / 40f;

    private int scoreMultiplier;

    private float initialScore = 2;

    public ScoreController() {
        this.time = 0;
        this.score = 0;
        this.elapsedTime = 0;
    }

    public int getScore() {
        return this.score;
    }

    public void update(float delta) {
        time += delta;
        elapsedTime += delta;
        updateScoreMultiplier();
        updateScore();
    }

    private void updateScoreMultiplier() {

        scoreMultiplier = (int) (timeToScoreRatio * elapsedTime + initialScore);

    }

    private void updateScore() {

        if (time >= 0.05) {
            score += scoreMultiplier;
            time = 0;
        }

    }
}
