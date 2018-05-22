package com.groundcontrol.game.view.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender extends Thread {

    private Socket clientSocket;
    private LinkedBlockingQueue<String> messagesToSend;
    public volatile boolean finished=false;

    public Sender(Socket clientSocket, LinkedBlockingQueue<String> msgSend) {
        System.out.println("Creating sender for communication with socket : " + clientSocket.getLocalPort());
        this.clientSocket = clientSocket;
        this.messagesToSend= msgSend;
    }

    @Override
    public void run(){
        System.out.println("Starting sending messages");
        while(!finished){
            try {
                String msg = messagesToSend.take();

                //System.out.println("Sending " + msg);

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())),true);

               // System.out.println("Sending " + msg);
                out.println(msg);

            } catch (InterruptedException e) {
                //stopCom();
                e.printStackTrace();
            } catch (IOException e) {
                stopCom();
                e.printStackTrace();
            }


        }
        System.out.println("Ending sending communication");
    }

    public void stopCom(){
        System.out.println("Stopping communication...");
        this.interrupt();
        this.finished=true;
    }
}