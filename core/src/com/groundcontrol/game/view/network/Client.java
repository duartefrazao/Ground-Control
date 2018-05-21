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


    public int startConnection(String ip, int port)  {
        System.out.println("Setting up Client");
        try {
            this.clientSocket = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println("Error connecting client to server");
            e.printStackTrace();
            return 1;
        }

        System.out.println("Connection established");

        System.out.println("Starting threads");
        receiver = new Receiver(clientSocket, receiveQueue);
        receiver.start();
        sender = new Sender(clientSocket, sendQueue);
        sender.start();

        this.alive = true;

        return 0;


    }

    public void sendMessage(String msg) throws IOException {
        sendQueue.add(msg);
    }

    public String receiveMessage() throws IOException {
        if(!receiveQueue.isEmpty()) return receiveQueue.poll();
        else return null;
    }

    public void stopConnection() throws IOException {
        receiver.stopCom();
        sender.stopCom();
        clientSocket.close();
    }
}
