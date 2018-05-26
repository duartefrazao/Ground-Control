package com.groundcontrol.game.view.network;


public class ServerConnector  extends Thread{

    Server server;
    private boolean finished;


    public ServerConnector(Server server) {
        this.server = server;
        this.finished=false;
    }

    @Override
    public void run(){

        System.out.println("Starting server connection...");
        server.start(8500);
        System.out.println("Ending server connection");
    }

    public void stopCom(){
        System.out.println("Stopping communication...");
        this.interrupt();
    }
}