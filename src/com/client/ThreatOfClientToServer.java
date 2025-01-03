package com.client;

import com.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
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
                System.out.println("start");
                Message message=(Message)ois.readObject();//waiting message from server
            } catch (Exception e) {
                e.printStackTrace();
            }




        }
    }
}
