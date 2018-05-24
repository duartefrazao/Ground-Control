package com.groundcontrol.game.view.network;

import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;


public class Communication {

    private Server server;
    private Client client;
    private boolean alive;
    private int portNum = 8500;

    public Communication(){
       server= new Server();
       server.start(portNum);
       client= new Client();
       //startCommunicationServer();
    }

    public void startCommunication(){

    }


}