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

    public void start(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Socket closed while waiting for client");
            return;
        }

        receiver = new Receiver(clientSocket, receiveQueue);
        receiver.start();
        sender = new Sender(clientSocket, sendQueue);
        sender.start();


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
        alive=false;
        receiveQueue.clear();
        sendQueue.clear();
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





















