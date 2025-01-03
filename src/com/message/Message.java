package com.message;
import com.client.User;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Message<MesType> implements Serializable {
    private User Sender;
    private User Receiver;
    private LocalDateTime Sendtime;
    private MesType content; //use generics to expand type of message like text,picture,video,ect
    private String MessageType;

    public Message() {

    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;

    }

    public Message(User receiver, User sender, MesType content, LocalDateTime sendtime) {
        Receiver = receiver;
        Sender = sender;
        this.content = content;
        Sendtime = sendtime;
    }

    public User getReceiver() {
        return Receiver;
    }

    public void setReceiver(User receiver) {
        Receiver = receiver;
    }

    public User getSender() {
        return Sender;
    }

    public void setSender(User sender) {
        Sender = sender;
    }

    public MesType getContent() {
        return content;
    }

    public void setContent(MesType content) {
        this.content = content;
    }

    public LocalDateTime getSendtime() {
        return Sendtime;
    }

    public void setSendtime(LocalDateTime sendtime) {
        Sendtime = sendtime;
    }
}
