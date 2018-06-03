package com.groundcontrol.game.view.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private Socket clientSocket;
    private Receiver receiver;
    private Sender sender;
    private boolean alive;
    private ConcurrentLinkedQueue<String> receiveQueue =  new ConcurrentLinkedQueue<String>();
    private LinkedBlockingQueue<String> sendQueue= new LinkedBlockingQueue<String>();


    public boolean startConnection(String ip, int port)  {
        try {
            this.clientSocket = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println("Error connecting client to server");
            return false;
        }


        receiver = new Receiver(clientSocket, receiveQueue);
        receiver.start();
        sender = new Sender(clientSocket, sendQueue);
        sender.start();

        this.alive = true;

        return true;


    }

    public void sendMessage(String msg) {
        sendQueue.add(msg);
    }

    public String receiveMessage() {
        if(!receiveQueue.isEmpty())
            return receiveQueue.poll();
        else return null;
    }

    public void tick()  {
        if(receiver.finished || sender.finished) stop();
    }

    public boolean isAlive(){
        return alive;
    }

    public void stop() {
        while(!sendQueue.isEmpty()){}
        this.alive=false;
        receiveQueue.clear();
        sendQueue.clear();
        receiver.stopCom();
        sender.stopCom();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessages() {
        while(!sendQueue.isEmpty()){}

    }
}
