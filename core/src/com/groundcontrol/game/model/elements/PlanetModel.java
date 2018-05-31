package com.groundcontrol.game.model.elements;

public class PlanetModel extends ElementModel {


    private PlanetSize size;

    public PlanetModel(float x, float y, float rotation) {


        super(x, y, rotation);

        this.size = PlanetSize.values()[(int) (Math.random() * PlanetModel.PlanetSize.values().length)];

    }

    public PlanetSize getSize() {
        return this.size;
    }

    @Override
    public ModelType getType() {

        switch (this.size) {
            case BIG:
                return ModelType.BigPlanet;
            case MEDIUM_BIG:
                return ModelType.MediumBigPlanet;
            case MEDIUM:
                return ModelType.MediumPlanet;
            default:
                return ModelType.SmallPlanet;
        }

    }

    public enum PlanetSize {
        SMALL,
        MEDIUM,
        MEDIUM_BIG,
        BIG
    }


}
