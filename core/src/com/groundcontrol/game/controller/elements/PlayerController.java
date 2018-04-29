package com.groundcontrol.game.controller.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.groundcontrol.game.controller.state.IdleState;
import com.groundcontrol.game.controller.state.PlayerState;
import com.groundcontrol.game.model.elements.ElementModel;

public class PlayerController extends ElementController {

    private PlayerState state;

    public void handleInput(){

    }

    public PlayerController(World world, ElementModel model) {
        super(world, model, BodyDef.BodyType.DynamicBody);

        state = new IdleState();

        float density = 1f,
                friction = 0.4f,
                restitution = 0.5f;
        int width = 244, height = 423;

        //Head
        createFixture(body, new float[]{
                62, 186,
                32, 122,
                57, 67,
                98,48,
                160,53,
                207,123,
                193,195,
                62, 186,
        }, width, height, density, friction, restitution, PLAYER_BODY,  (short) (PLANET_BODY|PLAYER_BODY));

        //Corns
        createFixture(body, new float[]{
               114,49,
                118,33,
                109,19,
                142,13,
                142,26,
                129,33,
                114,49,
        },width, height, density, friction, restitution, PLAYER_BODY,  (short) (PLANET_BODY|PLAYER_BODY));

        createFixture(body, new float[]{
                191,83,
                207,66,
                215,52,
                219,26,
                241,34,
                232,52,
                219,76,
                191,83,
        },width, height, density, friction, restitution, PLAYER_BODY, PLAYER_BODY);

        //Arms
        createFixture(body, new float[]{
                61,196,
                23,198,
                3,217,
                21,268,
                61,196,
        },width, height, density, friction, restitution, PLAYER_BODY, PLAYER_BODY);

        createFixture(body, new float[]{
                150,229,
                175,285,
                166,316,
                156,330,
                150,229,
        },width, height, density, friction, restitution, PLAYER_BODY, PLAYER_BODY);

        //Legs
        createFixture(body, new float[]{
                31,332,
                37,370,
                36,401,
                31,416,
                90,418,
                85,403,
                81,374,
                31,332,
        },width, height, density, friction, restitution, PLAYER_BODY, PLAYER_BODY);

        createFixture(body, new float[]{
                107,359,
                102,395,
                106,418,
                161,417,
                144,397,
                107,359,
                152,327,
        },width, height, density, friction, restitution, PLAYER_BODY, PLAYER_BODY);

        //Belly
        createFixture(body, new float[]{
                75,219,
                17,283,
                41,346,
                90,364,
                143,330,
                151,280,
                138,227,
                75,219,
        },width, height, density, friction, restitution, PLAYER_BODY, PLAYER_BODY);


    }
}
