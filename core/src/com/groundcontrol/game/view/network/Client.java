package com.groundcontrol.game.view.network;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Networking client
 */
public class Client {
    private Socket clientSocket;
    private Receiver receiver;
    private Sender sender;
    private boolean alive;
    private ConcurrentLinkedQueue<String> receiveQueue =  new ConcurrentLinkedQueue<String>();
    private LinkedBlockingQueue<String> sendQueue= new LinkedBlockingQueue<String>();

    /**
     * Starts a connection with a server
     * @param ip of the server
     * @param port of the server
     * @return if the connection was sucessfull
     */
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

    /**
     * Sends message to sendQueue to be sent to server
     * @param msg message to send to server
     */
    public void sendMessage(String msg) {
        sendQueue.add(msg);
    }

    /**
     * Receives message from server from receiveQueue
     * @return null if receiveQueue empty, or message if not
     */
    public String receiveMessage() {
        if(!receiveQueue.isEmpty())
            return receiveQueue.poll();
        else return null;
    }

    /**
     * Check to see if both communication threads are working, if not stops client
     */
    public void tick()  {
        if(receiver.finished || sender.finished) stop();
    }

    /**
     * Gets client status
     * @return true if both threads are up
     */
    public boolean isAlive(){
        return alive;
    }

    /**
     * Stops the client, terminating threads and finishing communication
     */
    public void stop() {
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

}
