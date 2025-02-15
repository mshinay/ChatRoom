package com.server;

import com.client.User;
import com.message.Message;
import com.message.MessageType;
import com.utility.Utility;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;




public class Server {
    public static void main(String[] args) throws Exception {

       Server.StartServer(9999);
    }

    public static void StartServer(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);//lisent to port 9999
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
                new Thread(new ThreatOfServerToClient(socket,user)).start();
                System.out.println(user.getId()+"进入服务器");
            }

        }
    }
}
