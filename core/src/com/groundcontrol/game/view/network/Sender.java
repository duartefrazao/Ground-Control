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
        this.clientSocket = clientSocket;
        this.messagesToSend= msgSend;
    }

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

    public void stopCom(){
        this.interrupt();
    }
}