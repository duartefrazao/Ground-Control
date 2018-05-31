package com.groundcontrol.game.model.elements;

/**
 * Explosion Model
 */
public class ExplosionModel extends ElementModel{

    private float timeLeft;

    private final static float EXPLOSION_TIME = 0.7f;

    /**
     * Creates a new explosion model in a position
     * @param x explosion's x position
     * @param y explosion's y position
     * @param rotation explosion's rotation
     */
    public ExplosionModel(float x, float y, float rotation){
        super(x,y, rotation);
        this.timeLeft = EXPLOSION_TIME;
    }

    /**
     * Checks if an explosion is over
     * @param delta time elapsed after the last step
     * @return true if explosion over, false otherwise
     */
    public boolean isExplosionOver(float delta){

        timeLeft -= delta;

        return timeLeft <= 0;
    }

    /**
     * Initializes the explosion timer to a default value
     */
    public void initializeExplosionTime(){
        this.timeLeft = EXPLOSION_TIME;
    }

    @Override
    public ElementModel.ModelType getType() {
        return ModelType.Explosion;
    }

}
