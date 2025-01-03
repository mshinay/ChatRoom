package com.server;


import com.client.User;
import com.message.Message;
import com.message.MessageType;
import com.utility.Utility;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;

public class ThreatOfServerToClient implements Runnable{
    Socket socket;
    User user;

    public ThreatOfServerToClient(Socket socket,User user) {
        this.socket = socket;
        this.user=user;
    }

    @Override
    public void run() {
        ObjectInputStream ois;



        while (true){


            try {

                ois = new ObjectInputStream(socket.getInputStream());
                Message message=(Message) ois.readObject();
                switch (message.getMessageType()){//deal with different types of message
                    case MessageType.MESSAGE_SEND_GRUPE:
                        String content=(String) message.getContent();
                        System.out.println(user.getId()+"说:"+content+"\r"+ Utility.TimeFormat(message.getSendtime()));
                        break;
                    case MessageType.MESSAGE_SEND_PRIVATE:


                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
