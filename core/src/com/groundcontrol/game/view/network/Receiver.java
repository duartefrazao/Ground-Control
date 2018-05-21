package com.groundcontrol.game.view.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.Buffer;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Receiver  extends Thread{

    private Socket clientSocket;
    private ConcurrentLinkedQueue<String> messagesToReceive;
    public volatile boolean finished =false;


    public Receiver(Socket clientSocket, ConcurrentLinkedQueue<String> messagesToReceive) {
        this.clientSocket= clientSocket;
        this.messagesToReceive=messagesToReceive;
    }

    @Override
    public void run(){
        System.out.println("Starting receive communication...");
        while(!finished){

            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String msg = br.readLine();

                if(msg == null) {
                    System.out.println("Received null message");
                    finished = true;
                }
                else{
                    System.out.println("Received " + msg);
                    messagesToReceive.add(msg);
                }

            } catch (IOException e) {
                System.out.println("Caught exception on run() in Receiver");
                stopCom();
                e.printStackTrace();
            }
        }
        System.out.println("Ending receive communication");
    }

    public void stopCom(){
        System.out.println("Stopping communication...");
        this.finished=true;
    }
}