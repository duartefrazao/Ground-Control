package com.groundcontrol.game.view.network;

/**
 * Thread responsible for initiating server connection with a client, and preventing busy waiting
 */
public class ServerConnector  extends Thread{

    Server server;

    /**
     * Constructor
     * @param server to initiate
     */
    public ServerConnector(Server server) {
        this.server = server;
    }

    /**
     * Starts connection to server
     */
    @Override
    public void run(){
        server.start(8500);
    }

}