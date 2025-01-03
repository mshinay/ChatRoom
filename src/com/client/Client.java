package com.client;

import com.message.Message;
import com.message.MessageType;
import com.utility.Utility;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class Client {
     Socket socket;
     User user;


    boolean login() throws Exception {
        boolean flag = false;//use to return
        System.out.print("输入ID:");
        String id=Utility.GetString(10);
        System.out.print("输入密码:");
        String password=Utility.GetString(20);
        user=new User(id,password);

        try {
            socket = new Socket(InetAddress.getLocalHost(), 8888);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());//send Message to Server
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());//receive Message from Server
            //send info of user to server to check
            oos.writeObject(user);
            oos.flush();

            //receive check message from server
            Message mes=(Message) ois.readObject();
            if(mes.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCESS)){//login sucessfully
                ClientSocketManage.addSocket(user.getId(), socket);//put connected used into socket set
                ThreatOfClientToServer threatOfClientToServer = new ThreatOfClientToServer(socket);
                new Thread(threatOfClientToServer).start();
                flag = true;
            }else{//login failly
                socket.close();

            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return flag;
    }


}