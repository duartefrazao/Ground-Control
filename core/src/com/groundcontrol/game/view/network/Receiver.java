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

    public void stopCom(){
        this.interrupt();
    }
}