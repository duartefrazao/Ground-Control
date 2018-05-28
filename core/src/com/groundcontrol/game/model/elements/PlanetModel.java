package com.groundcontrol.game.model.elements;

public class PlanetModel extends ElementModel {


    public enum PlanetSize {
        SMALL,
        MEDIUM,
        MEDIUM_BIG,
        BIG
    }

    private PlanetSize size;

    public PlanetModel(float x, float y, float rotation, PlanetSize size) {

        super(x, y, rotation);
        this.size = size;
    }

    public PlanetSize getSize() {
        return this.size;
    }

    @Override
    public ModelType getType() {
        if (this.size == PlanetSize.BIG)
            return ModelType.BigPlanet;
        else if(this.size == PlanetSize.MEDIUM_BIG)
            return ModelType.MediumBigPlanet;
        else if(this.size == PlanetSize.MEDIUM)
            return ModelType.MediumPlanet;
        else
            return ModelType.SmallPlanet;
    }


}
