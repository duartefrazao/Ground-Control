package com.groundcontrol.game.view.ScreenModules;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Interface used to define the several sections of our game, during it's life cycle
 */
public interface Section {

    /**
     * Updates all the section elements in each step
     * @param delta the time elapsed after the last step
     */
    void update(float delta);

    /**
     * Display all the section elements
     * @param delta the time elapsed after the last step
     */
    void display(float delta);

    /**
     * Makes a transition to a new section, clearing every not needed element
     */
    void transition();

    /**
     * Creates the needed stage to hold all elements in a section
     * @return the stage created
     */
    Stage createStage();


    /**
     * Draws all the stages created for the section
     * @param delta the time elapsed after the last step
     */
    void drawStages(float delta);

    /**
     * Draws the background of the section
     */
    void drawBackground();
}
