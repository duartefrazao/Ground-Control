package com.groundcontrol.game.model.elements;

/**
 * Planet Model
 */
public class PlanetModel extends ElementModel {


    private PlanetSize size;


    /**
     * Creates a new planet model in a position
     *
     * @param x        planet's x position
     * @param y        planet's y position
     * @param rotation planet's rotation
     */
    public PlanetModel(float x, float y, float rotation) {

        super(x, y, rotation);

        this.size = PlanetSize.values()[(int) (Math.random() * PlanetModel.PlanetSize.values().length)];

    }

    /**
     * Returns the planet size
     * @return planet size
     */
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

    /**
     * Planets possible sizes
     */
    public enum PlanetSize {
        SMALL,
        MEDIUM,
        MEDIUM_BIG,
        BIG
    }


}
