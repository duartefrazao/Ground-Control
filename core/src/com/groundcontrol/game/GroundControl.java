package com.groundcontrol.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.view.GameView;
import com.groundcontrol.game.view.MenuScreen;
import com.groundcontrol.game.view.SecondPlayerScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Formatter;

public class GroundControl extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;


	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		startGame();
	}

	private String getIP()
	{
		// This try will give the Public IP Address of the Host.
		try
		{
			URL url = new URL("http://automation.whatismyip.com/n09230945.asp");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String ipAddress = new String();
			ipAddress = (in.readLine()).trim();
			/* IF not connected to internet, then
			 * the above code will return one empty
			 * String, we can check it's length and
			 * if length is not greater than zero,
			 * then we can go for LAN IP or Local IP
			 * or PRIVATE IP
			 */
			if (!(ipAddress.length() > 0))
			{
				try
				{
					InetAddress ip = InetAddress.getLocalHost();
					System.out.println((ip.getHostAddress()).trim());
					return ((ip.getHostAddress()).trim());
				}
				catch(Exception ex)
				{
					return "ERROR";
				}
			}
			System.out.println("IP Address is : " + ipAddress);

			return (ipAddress);
		}
		catch(Exception e)
		{
			// This try will give the Private IP of the Host.
			try
			{
				InetAddress ip = InetAddress.getLocalHost();
				System.out.println((ip.getHostAddress()).trim());
				return ((ip.getHostAddress()).trim());
			}
			catch(Exception ex)
			{
				return "ERROR";
			}
		}
	}

	private void startGame()  {

		/*Enumeration en = null;
		try {
			en = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		while(en.hasMoreElements())
		{
			NetworkInterface n = (NetworkInterface) en.nextElement();
			Enumeration ee = n.getInetAddresses();
			while (ee.hasMoreElements())
			{
				InetAddress i = (InetAddress) ee.nextElement();
				System.out.println(i.getHostAddress());
			}
		}
		InetAddress IP= null;
		try {
			IP = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		InetAddress Ipe= null;
		try {
			Ipe = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("IP of my system is := "+Ipe.getHostAddress());

		System.out.println(IP.toString());
		System.out.println(getIP());

		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress("google.com", 80));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(socket.getLocalAddress());
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} */

		Socket s = null;
		try {
			s = new Socket("google.pt", 80);

			String serverIP = s.getLocalAddress().getHostAddress();

			System.out.println("IP->" +serverIP);
			s.close();
		} catch (IOException e) {
			System.exit(1);
			e.printStackTrace();
		}


		GameModel gameModel = new GameModel();

		GameController gameController = new GameController(gameModel);


		setScreen(new MenuScreen(this));


	}

	public void startNewGame(){
		GameModel gameModel = new GameModel();

		GameController gameController = new GameController(gameModel);

		setScreen(new GameView(this, gameModel,gameController));
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

	public void startMP() {
		setScreen(new SecondPlayerScreen(this));
	}

    public void startMainMenu() {
		setScreen(new MenuScreen(this));
    }
}
