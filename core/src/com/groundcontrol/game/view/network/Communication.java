package com.groundcontrol.game.view.network;

import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;


public class Communication {

    private Socket clientSocket;
    private Receiver receiver;
    private Sender sender;
    private Server server;

    private ConcurrentLinkedQueue<String> messagesToReceive;
    private LinkedBlockingQueue<String> messagesToSend;

    private boolean alive;

    public Communication(Server s, Socket c){
        System.out.println("Creating communication");
        this.alive=true;
        this.clientSocket=c;
        this.server=s;
        this.messagesToReceive = new ConcurrentLinkedQueue<String>();
        this.messagesToSend = new LinkedBlockingQueue<String>();

        receiver = new Receiver(clientSocket, messagesToReceive);
        receiver.start();

        sender= new Sender(clientSocket, messagesToSend);
        sender.start();
    }

    public void send(String msg){
        messagesToSend.add(msg);
    }

}