package com.client;

import com.message.Message;
import com.message.MessageType;
import com.utility.Utility;

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
                switch (message.getMessageType()){
                    case MessageType.MESSAGE_SEND_PRIVATE:
                        System.out.println("(私)"+message.getSenderID()+":"+message.getContent()+"\t"+ Utility.TimeFormat(message.getSendtime()));
                        break;
                    case MessageType.MESSAGE_SEND_GRUPE:
                        System.out.println("(公)"+message.getSenderID()+":"+message.getContent()+"\t"+ Utility.TimeFormat(message.getSendtime()));
                        break;
                    case MessageType.MESSAGE_VIEW_ONLINEUSER:

                        String[] users = message.getContent().split(" ");
                        for (String user : users) {
                            System.out.println("用户:"+user);
                        }

                }
            } catch (Exception e) {
                System.out.println("与服务器断开连接，请重新启动");
                break;
                /**
                 * When closing Client,there'll throw out EOFException,I don't know how to solve it.
                 * So I just common e.printStackTrace(); as following.
                 */
                //e.printStackTrace();


            }


        }
    }
}
