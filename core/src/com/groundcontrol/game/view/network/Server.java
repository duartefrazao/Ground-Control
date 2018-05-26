package com.groundcontrol.game.view.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server extends Observable {

    public ServerSocket serverSocket;
    private Socket clientSocket;
    private Receiver receiver;
    private Sender sender;
    private boolean alive;
    private ConcurrentLinkedQueue<String> receiveQueue =  new ConcurrentLinkedQueue<String>();
    private LinkedBlockingQueue<String> sendQueue= new LinkedBlockingQueue<String>();
    private int portNum = 8500;

    public void start(int port) {
        System.out.println("Setting up server");
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Here111");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Socket closed while waiting for client");
            return;
        }

        System.out.println("Connection established");

        System.out.println("Starting threads");
        receiver = new Receiver(clientSocket, receiveQueue);
        receiver.start();
        sender = new Sender(clientSocket, sendQueue);
        sender.start();


        System.out.println("[Server]Creating communication");
        this.alive = true;
    }

    public void sendMessage(String msg)  {
        sendQueue.add(msg);
    }

    public String receiveMessage()  {

        if(!receiveQueue.isEmpty())
            return receiveQueue.poll();
        return null;
    }


    public void tick()  {
        if(receiver.finished || sender.finished) stop();
    }

    public boolean isAlive() {
        return alive;
    }

    public void stop()  {
        System.out.println("Stopping server");
        while(!sendQueue.isEmpty()){}
        alive=false;
        receiver.stopCom();
        sender.stopCom();
        try {
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





















