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
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFrame extends JFrame {
    private JTextArea txtAllMessages;
    private JScrollPane scrollPaneAllMessages;

    public ServerFrame() {

        this.setTitle("Server");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        this.setVisible(true);
    }
    private void init() {
        txtAllMessages = new JTextArea(30, 20);
        txtAllMessages.setEditable(false);
        scrollPaneAllMessages = new JScrollPane(txtAllMessages,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPaneAllMessages);
        this.pack();

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerFrame serverFrame = new ServerFrame();

        ServerSocket serverSocket = new ServerSocket(9999);//lisent to port 9999
        while (true) {
            ServerSocketManager.viewAllSocket();
            Socket socket = serverSocket.accept();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());//send Message to Client
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());//receive Message from Client
            User user=(User) ois.readObject();
            Message message = new Message();
            if(!Utility.CheckUser(user.getId(),user.getPassword())) {
                message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                oos.writeObject(message);
                socket.close();
            }
            if(ServerSocketManager.getSocket(user.getId())!=null) {//avoid Repeated logins
                message.setMessageType(MessageType.MESSAGE_REPEATED_LOGIN);
                oos.writeObject(message);
                socket.close();
            }
            else {
                message.setMessageType(MessageType.MESSAGE_LOGIN_SUCESS);//LOGIN_SUCESS
                oos.writeObject(message);
                ServerSocketManager.addSocket(user.getId(), socket);//add into Server's socket set
                ThreatOfServerToClientGUI threatOfServerToClientGUI = new ThreatOfServerToClientGUI(socket, user);
                threatOfServerToClientGUI.setTxtAllMessages(serverFrame.txtAllMessages);
                new Thread(threatOfServerToClientGUI).start();
                serverFrame.txtAllMessages.append(user.getId()+"进入服务器\n");

            }

        }

    }

}
