package com.groundcontrol.game.view.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Networking server
 */
public class Server extends Observable {

    public ServerSocket serverSocket;
    private Socket clientSocket;
    private Receiver receiver;
    private Sender sender;
    private boolean alive;
    private ConcurrentLinkedQueue<String> receiveQueue =  new ConcurrentLinkedQueue<String>();
    private LinkedBlockingQueue<String> sendQueue= new LinkedBlockingQueue<String>();

    /**
     * Starts a connection with a client
     * @param port of the server
     * @return if the connection was made sucessfully
     */
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

    /**
     * Sends message to sendQueue to be sent to client
     * @param msg message to send to client
     */
    public void sendMessage(String msg)  {
        sendQueue.add(msg);
    }

    /**
     * Receives message from client from receiveQueue
     * @return null if receiveQueue empty, or message if not
     */
    public String receiveMessage()  {

        if(!receiveQueue.isEmpty())
            return receiveQueue.poll();
        return null;
    }


    /**
     * Check to see if both communication threads are working, if not stops server
     */
    public void tick()  {
        if(receiver.finished || sender.finished) stop();
    }

    /**
     * Gets server status
     * @return true if both threads are up
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Stops the server, terminating threads and finishing communication
     */
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





















