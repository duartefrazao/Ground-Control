package com.groundcontrol.game.model.elements;

public class ExplosionModel extends ElementModel{

    private float timeLeft;

    private final static float EXPLOSION_TIME = 1.3f;

    public ExplosionModel(float x, float y, float rotation){
        super(x,y, rotation);
        this.timeLeft = EXPLOSION_TIME;
    }

    public boolean isExplosionOver(float delta){

        timeLeft -= delta;

        if(timeLeft <= 0)
            return true;

        return  false;
    }

    public void initializeExplosionTime(){
        this.timeLeft = EXPLOSION_TIME;
    }

    @Override
    public ElementModel.ModelType getType() {
        return ModelType.Explosion;
    }

}
