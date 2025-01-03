package com.server;

import com.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.net.Socket;

public class ThreatOfServerToClient implements Runnable{
    Socket socket;

    public ThreatOfServerToClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream ois;



        while (true){


            try {
                ois = new ObjectInputStream(socket.getInputStream());
                Message message=(Message) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
