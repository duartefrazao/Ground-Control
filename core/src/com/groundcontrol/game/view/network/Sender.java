package com.groundcontrol.game.view.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Thread responsible for sending messages
 */
public class Sender extends Thread {

    private Socket clientSocket;
    private LinkedBlockingQueue<String> messagesToSend;
    public volatile boolean finished=false;

    /**
     * Constructor
     * @param clientSocket Client connected
     * @param msgSend Queue to take messages to send
     */
    public Sender(Socket clientSocket, LinkedBlockingQueue<String> msgSend) {
        this.clientSocket = clientSocket;
        this.messagesToSend= msgSend;
    }


    /**
     * Thread cycle to send messages while connection is up
     */
    @Override
    public void run(){
        while(!finished){
            try {

                if(messagesToSend==null){
                    this.finished=true;
                    return;
                }

                String msg = messagesToSend.take();
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())),true);
                out.println(msg);

            }catch (InterruptedException e) {
                e.printStackTrace();
                this.finished=true;
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