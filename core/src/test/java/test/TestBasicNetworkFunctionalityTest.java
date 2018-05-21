


import com.groundcontrol.game.view.network.Client;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestBasicNetworkFunctionalityTest {

    @Test
    public void testBasicCommunication() throws IOException {
        /*Client client = new Client();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);*/
    }

    @Test
    public void testServerReceiveCommunication() throws IOException, InterruptedException {
        /*Client client = new Client();
        client.startConnection("127.0.0.1", 6666);
        String hS = "hello server";
        String response = client.sendMessage(hS);
        for(int i = 0; i < 100;i++){
            TimeUnit.MILLISECONDS.sleep(100);
            client.sendMessage(Integer.toString(i));

        }
        */
        /*assertEquals("hello client", response);
        assertEquals("hello client", response);*/


    }

    @Test
    public void testServerSendCommunication() throws InterruptedException, IOException {
        /*Client client = new Client();
        client.startConnection("127.0.0.1", 6666);
        while(true)System.out.println(client.receiveMessage());*/
    }

    @Test
    public void testServerReceiveSend() throws InterruptedException, IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 6666);
        TimeUnit.SECONDS.sleep(2);

        for(int i = 0; i< 2;i++)System.out.println(client.receiveMessage());
        TimeUnit.SECONDS.sleep(2);
        for(int i = 0; i< 200;i++){
            client.sendMessage("RIGHT");
            TimeUnit.MILLISECONDS.sleep(100);
        }
        TimeUnit.SECONDS.sleep(2);
        for(int i = 0; i< 2;i++)System.out.println(client.receiveMessage());
        TimeUnit.SECONDS.sleep(2);
        for(int i = 0; i< 2;i++)client.sendMessage("RIGHT");
        TimeUnit.SECONDS.sleep(2);

    }

}