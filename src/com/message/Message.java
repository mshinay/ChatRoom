package com.message;
import com.client.User;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Message implements Serializable {
    private String SenderID;
    private String ReceiverID;
    private LocalDateTime Sendtime;
    private String content; //use generics to expand type of message like text,picture,video,ect
    private String MessageType;

    public Message() {

    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;

    }

    public Message(String receiverID, String senderID, String content, LocalDateTime sendtime) {
        ReceiverID = receiverID;
        SenderID = senderID;
        this.content = content;
        Sendtime = sendtime;
    }

    public String getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(String receiverID) {
        ReceiverID = receiverID;
    }

    public String getSenderID() {
        return SenderID;
    }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSendtime() {
        return Sendtime;
    }

    public void setSendtime(LocalDateTime sendtime) {
        Sendtime = sendtime;
    }
}
