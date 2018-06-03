package com.groundcontrol.game.view.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.Buffer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Thread responsible for receiving messages
 */
public class Receiver  extends Thread{

    private Socket clientSocket;
    private ConcurrentLinkedQueue<String> messagesToReceive;
    public volatile boolean finished =false;


    /**
     * Constructor
     * @param clientSocket Client connected
     * @param messagesToReceive Queue to send messages received
     */
    public Receiver(Socket clientSocket, ConcurrentLinkedQueue<String> messagesToReceive) {
        this.clientSocket= clientSocket;
        this.messagesToReceive=messagesToReceive;
    }

    /**
     * Thread cycle to receive messages while connection is up
     */
    @Override
    public void run(){

        while(!finished){

            try {


                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String msg = br.readLine();
                if(messagesToReceive==null || msg==null){
                    this.finished=true;
                    return ;
                }
                messagesToReceive.add(msg);

            } catch (IOException e) {
                e.printStackTrace();
                this.finished=true;
            }
        }
    }

    /**
     * Forces end of the thread
     */
    public void stopCom(){
        this.interrupt();
    }
}