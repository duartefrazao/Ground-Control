package com.groundcontrol.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.view.GameView;

public class GroundControl extends Game {

	private SpriteBatch batch;

	private AssetManager assetManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		startGame();
	}

	private void startGame()  {
		GameModel gameModel = new GameModel();

		GameController gameController = new GameController(gameModel);

		GameView gv= new GameView(this,gameModel,gameController);

		setScreen(gv);


	}

	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

	/**
	 * Accessor to the game batch
	 * @return batch
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	/**
	 * Accessor to the game asset manager
	 * @return assetManager
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

}
