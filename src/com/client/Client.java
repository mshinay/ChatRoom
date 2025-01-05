package com.client;

import com.message.Message;
import com.message.MessageType;
import com.utility.Utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;


public class Client {
     private Socket socket;
     private User user;
     private ThreatOfClientToServer threatOfClientToServer;
     private ObjectOutputStream oos;
     private ObjectInputStream ois;


    public ThreatOfClientToServer getThreatOfClientToServer() {
        return threatOfClientToServer;
    }

    boolean login() throws Exception {
        boolean flag = false;//use to return
        System.out.print("输入ID:");
        String id=Utility.GetString(10);
        System.out.print("输入密码:");
        String password=Utility.GetString(20);
        user=new User(id,password);

        try {
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            oos = new ObjectOutputStream(socket.getOutputStream());//send Message to Server
            ois = new ObjectInputStream(socket.getInputStream());//receive Message from Server
            //send info of user to server to check
            oos.writeObject(user);
            oos.flush();

            //receive check message from server
            Message mes=(Message) ois.readObject();
            if(mes.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCESS)){//login sucessfully
                ClientSocketManage.addSocket(user.getId(), socket);//put connected used into socket set
                threatOfClientToServer = new ThreatOfClientToServer(socket);
                Thread thread = new Thread(threatOfClientToServer);
                thread.start();
                flag = true;
            }else{//login failly
                socket.close();

            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return flag;
    }

    public void grupeChat() {
        String content=Utility.GetString(100);//sent chat content in ChatRoom
        Message<String> message=new Message<>(null,user.getId(),content, LocalDateTime.now());
        message.setMessageType(MessageType.MESSAGE_SEND_GRUPE);
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());//send Message to Server
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chatPrivate(String receiverId) {//sent chat content to other account
        String content=Utility.GetString(100);
        Message<String> message=new Message<>(receiverId,user.getId(),content, LocalDateTime.now());
        message.setMessageType(MessageType.MESSAGE_SEND_PRIVATE);
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());//send Message to Server
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() {//close socket
        try {
            oos=new ObjectOutputStream(socket.getOutputStream());
            Message message=new Message();
            message.setMessageType(MessageType.MESSAGE_LOGOUT);
            oos.writeObject(message);
            oos.flush();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
