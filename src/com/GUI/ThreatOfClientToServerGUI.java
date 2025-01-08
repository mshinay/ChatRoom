package com.GUI;

import com.message.Message;
import com.message.MessageType;
import com.utility.Utility;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ThreatOfClientToServerGUI implements Runnable{
    private Socket socket;
    private JTextArea txtAllMessages;
    private JFrame frame;

    public ThreatOfClientToServerGUI(Socket socket) {
        this.socket = socket;
    }

    public void setTxtAllMessages(JTextArea txtAllMessages) {
        this.txtAllMessages = txtAllMessages;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
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
                        txtAllMessages.append("(私)"+message.getSenderID()+":"+message.getContent()+"\t"+ Utility.TimeFormat(message.getSendtime())+"\n");
                        break;
                    case MessageType.MESSAGE_SEND_GRUPE:
                        if(message.getSenderID().equals(message.getReceiverID())){
                            txtAllMessages.append("(公)你"+":"+message.getContent()+"\t"+ Utility.TimeFormat(message.getSendtime())+"\n");}
                       else { txtAllMessages.append("(公)"+message.getSenderID()+":"+message.getContent()+"\t"+ Utility.TimeFormat(message.getSendtime())+"\n");}
                        break;
                    case MessageType.MESSAGE_VIEW_ONLINEUSER:

                        String[] users = message.getContent().split(" ");
                        for (String user : users) {
                            txtAllMessages.append("用户:"+user+"\n");
                        }
                        break;
                    case MessageType.MESSAGE_SEND_PRIVATE_FAIL:
                        txtAllMessages.append(message.getContent()+"\n");
                        break;

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame,"连接异常","错误警告",JOptionPane.ERROR_MESSAGE);
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
