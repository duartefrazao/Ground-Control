package com.groundcontrol.game.model.elements;

public class ExplosionModel extends ElementModel{

    private float timeLeft;

    private final static float EXPLOSION_TIME = 3f;

    public ExplosionModel(float x, float y, float rotation){
        super(x,y, rotation);
        this.timeLeft = EXPLOSION_TIME;
    }

    public boolean isExplosionOver(float delta){

        timeLeft -= delta;

        return timeLeft <= 0;
    }

    public void initializeExplosionTime(){
        this.timeLeft = EXPLOSION_TIME;
    }

    @Override
    public ElementModel.ModelType getType() {
        return ModelType.Explosion;
    }

}
