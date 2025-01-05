package com.client;

import com.message.Message;
import com.message.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreatOfClientToServer implements Runnable{
    Socket socket;

    public ThreatOfClientToServer(Socket socket) {
        this.socket = socket;
    }



    @Override
    public void run() {
        ObjectInputStream ois;

        while (true) {
            try {
                ois = new ObjectInputStream(socket.getInputStream());//receive Message from Server
                Message message=(Message) ois.readObject();//waiting message from server
                System.out.println(message.getSenderID()+":"+message.getContent());
            } catch (Exception e) {
                /**
                 * When closing Client,there'll throw out EOFException,I don't know how to solve it.
                 * So I just common e.printStackTrace(); as following.
                 */
                //e.printStackTrace();


            }









        }
    }
}
