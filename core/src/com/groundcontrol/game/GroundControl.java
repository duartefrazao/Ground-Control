package com.groundcontrol.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.network.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

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


		/*Socket s = null;
		try {
			s = new Socket("google.pt", 80);

			String serverIP = s.getLocalAddress().getHostAddress();

			System.out.println("IP->" +serverIP);
			s.close();
		} catch (IOException e) {
			//System.exit(1);
			e.printStackTrace();
		}
*/

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

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

}
