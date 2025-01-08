package com.GUI;


import com.client.User;
import com.message.Message;
import com.message.MessageType;
import com.server.ServerSocketManager;
import com.utility.Utility;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class ThreatOfServerToClientGUI implements Runnable{
    Socket socket;
    User user;
    private JTextArea txtAllMessages;

    public void setTxtAllMessages(JTextArea txtAllMessages) {
        this.txtAllMessages = txtAllMessages;
    }

    public ThreatOfServerToClientGUI(Socket socket, User user) {
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

                        txtAllMessages.append(user.getId()+"对所有人:"+content+"\t"+ Utility.TimeFormat(message.getSendtime())+"\n");
                        System.out.println(user.getId()+"对所有人:"+content+"\t"+ Utility.TimeFormat(message.getSendtime()));
                        Map.Entry[] entries = ServerSocketManager.getAllIfo();
                        for (Map.Entry entry : entries) {
                            ObjectOutputStream Receiveroos = new ObjectOutputStream(((Socket)entry.getValue()).getOutputStream());
                            message.setReceiverID((String) entry.getKey());
                            Receiveroos.writeObject(message);
                            Receiveroos.flush();
                        }
                        break;
                    case MessageType.MESSAGE_SEND_PRIVATE:
                        if(ServerSocketManager.getSocket(message.getReceiverID())==null){
                            message.setContent("当前用户不在线");
                            message.setMessageType(MessageType.MESSAGE_SEND_PRIVATE_FAIL);
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(message);
                            oos.flush();
                        }
                        else {
                            Socket Receiversocket = ServerSocketManager.getSocket(message.getReceiverID());
                            ObjectOutputStream Receiveroos = new ObjectOutputStream(Receiversocket.getOutputStream());
                            Receiveroos.writeObject(message);
                            Receiveroos.flush();
                            txtAllMessages.append(message.getSenderID()+"对"+message.getReceiverID()+":"+message.getContent()+"\t"+ Utility.TimeFormat(message.getSendtime())+"\n");
                            System.out.println(message.getSenderID()+"对"+message.getReceiverID()+":"+message.getContent()+"\t"+ Utility.TimeFormat(message.getSendtime()));
                        }
                        break;
                    case MessageType.MESSAGE_LOGOUT:
                        loop = false;
                        ServerSocketManager.removeSocket(user.getId());
                        txtAllMessages.append(user.getId()+"下线\n");
                        System.out.println(user.getId()+"下线\n");
                        break;
                    case MessageType.MESSAGE_VIEW_ONLINEUSER:
                        message.setContent(ServerSocketManager.viewAllSocket());
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        System.out.println(ServerSocketManager.viewAllSocket());
                        oos.writeObject(message);
                        oos.flush();
                        txtAllMessages.append(message.getSenderID()+"查询了在线用户\n");
                        System.out.println(message.getSenderID()+"查询了在线用户\n");
                        break;
                }

            } catch (Exception e) {
                try {
                    ServerSocketManager.removeSocket(user.getId());
                } catch (IOException ex) {

                }

                txtAllMessages.append(user.getId()+"下线\n");
                System.out.println(user.getId()+"下线\n");
                    break;
                }

            }


        }
    }


