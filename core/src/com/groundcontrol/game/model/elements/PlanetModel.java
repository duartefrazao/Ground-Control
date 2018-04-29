package com.groundcontrol.game.model.elements;

public class PlanetModel extends ElementModel {


    public enum PlanetSize {
        SMALL,
        MEDIUM,
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
        else
            return ModelType.MediumPlanet;
    }


}
