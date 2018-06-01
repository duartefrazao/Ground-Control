package com.groundcontrol.game.view.network;


public class ServerConnector  extends Thread{

    Server server;

    public ServerConnector(Server server) {
        this.server = server;
    }

    @Override
    public void run(){
        server.start(8500);
    }

}