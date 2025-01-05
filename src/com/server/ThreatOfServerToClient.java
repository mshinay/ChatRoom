package com.server;


import com.client.User;
import com.message.Message;
import com.message.MessageType;
import com.utility.Utility;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;
import java.net.Socket;

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
        boolean loop = true;


        while (loop){


            try {


                    ois = new ObjectInputStream(socket.getInputStream());
                    Message message=(Message) ois.readObject();
                    String content="";
                switch (message.getMessageType()){//deal with different types of message
                    case MessageType.MESSAGE_SEND_GRUPE:
                        content=(String) message.getContent();
                        System.out.println(user.getId()+":"+content+"\t"+ Utility.TimeFormat(message.getSendtime()));
                        break;
                    case MessageType.MESSAGE_SEND_PRIVATE:
                        Socket Receiversocket = ServerSocketManager.getSocket(message.getReceiverID());
                        ObjectOutputStream Receiveroos = new ObjectOutputStream(Receiversocket.getOutputStream());
                        Receiveroos.writeObject(message);
                        Receiveroos.flush();
                        break;
                    case MessageType.MESSAGE_LOGOUT:
                        loop = false;
                        ServerSocketManager.removeSocket(user.getId());
                        System.out.println(user.getId()+"下线");
                        break;
                    case MessageType.MESSAGE_VIEW_ONLINEUSER:
                        message.setContent(ServerSocketManager.viewAllSocket());
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        System.out.println(ServerSocketManager.viewAllSocket());
                        oos.writeObject(message);
                        oos.flush();
                        break;
                }

            } catch (Exception e) {
                try {
                    ServerSocketManager.removeSocket(user.getId());
                } catch (IOException ex) {

                }
                System.out.println(user.getId()+"下线");
                    break;
                }

            }


        }
    }


