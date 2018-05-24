package com.groundcontrol.game.view.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server extends Observable {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Receiver receiver;
    private Sender sender;
    private boolean alive;
    private ConcurrentLinkedQueue<String> receiveQueue =  new ConcurrentLinkedQueue<String>();
    private LinkedBlockingQueue<String> sendQueue= new LinkedBlockingQueue<String>();

    public void start(int port) {
        System.out.println("Setting up server");
        try {
            this.serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000);
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
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
    public void stop() throws IOException {
        alive=false;
        receiver.finished=true;
        sender.finished=true;
        receiver.stopCom();
        sender.stopCom();
        clientSocket.close();
        serverSocket.close();
    }

    public void sendMessage(String msg) throws IOException {
        sendQueue.add(msg);
    }

    public String receiveMessage() throws IOException {
        if(!receiveQueue.isEmpty()){
            String mes =receiveQueue.poll();
            return mes;
        }
         return null;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(8888);

    }

    public void tick() throws IOException {
        if(receiver.finished || sender.finished) stop();
    }

    public boolean isAlive() {
        return alive;
    }
}





















