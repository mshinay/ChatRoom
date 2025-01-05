package com.server;

import com.client.User;
import com.message.Message;
import com.message.MessageType;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;




public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(9999);//lisent to port 9999
        while (true) {
            Socket socket = serverSocket.accept();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());//send Message to Client
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());//receive Message from Client
            User user=(User) ois.readObject();
            Message message = new Message();
            if(user.getId()==null){//id is invaild
                message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                oos.writeObject(message);
                socket.close();
            }
            if(!(user.getPassword().equals("123456"))){//password is wrong
                message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                oos.writeObject(message);
                socket.close();
            }else {
                message.setMessageType(MessageType.MESSAGE_LOGIN_SUCESS);//LOGIN_SUCESS
                oos.writeObject(message);
                ServerSocketManage.addSocket(user.getId(), socket);//add into Server's socket set
                new Thread(new ThreatOfServerToClient(socket,user)).start();
                System.out.println(user.getId()+"进入服务器");
            }



        }
    }
}
