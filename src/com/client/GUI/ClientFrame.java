package com.client.GUI;

import com.client.Client;
import com.message.Message;
import com.message.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

public class ClientFrame extends JFrame {

    private JTextArea txtAllMessages;
    private JTextField txtSendMessages;
    private JLabel lbReceiverID;
    private JTextField txtReceiverID;
    private JButton btnSend;
    private JButton btnGrupe;
    private JButton btnPrivate;
    private JButton btnGetOnline;

    private JScrollPane scrollPaneAllMessages;
    private JPanel panelSendMessages;
    private JPanel panelReceiverID;
    private JPanel PanelMenu;

    private Box box;
    private boolean displayPrivate=false;

    private Client client;

    public ClientFrame(Client client) {
        this.client = client;
        this.setTitle("聊天室");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        init();
        ThreatOfClientToServerGUI threatOfClientToServerGUI = client.getThreatOfClientToServerGUI();
        threatOfClientToServerGUI.setTxtAllMessages(txtAllMessages);
        threatOfClientToServerGUI.setFrame(this);
        this.setVisible(true);
    }

    public void init() {
        txtAllMessages = new JTextArea(30,20);
        txtAllMessages.setEditable(false);

        txtSendMessages = new JTextField(20);
        txtReceiverID = new JTextField(20);
        lbReceiverID = new JLabel("对方ID:");
        btnSend = new JButton("发送");
        btnGrupe = new JButton("群发");
        btnPrivate= new JButton("私发");
        btnGetOnline=new JButton("在线用户");

        scrollPaneAllMessages = new JScrollPane(txtAllMessages,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelSendMessages = new JPanel(new FlowLayout());
        panelSendMessages.add(txtSendMessages);
        panelSendMessages.add(btnSend);

        panelReceiverID = new JPanel(new FlowLayout());
        panelReceiverID.add(lbReceiverID);
        panelReceiverID.add(txtReceiverID);
        panelReceiverID.setVisible(false);

        PanelMenu = new JPanel(new FlowLayout());
        PanelMenu.add(btnGrupe);
        PanelMenu.add(btnPrivate);
        PanelMenu.add(btnGetOnline);



        box=Box.createVerticalBox();
        box.add(scrollPaneAllMessages);
        box.add(panelReceiverID);
        box.add(panelSendMessages);

        box.add(PanelMenu);

        this.setContentPane(box);
        this.pack();

        btnSend.addActionListener(new BtnSendListener(this,client));
        btnGetOnline.addActionListener(new BtnGetOnlineListener(client));
        btnGrupe.addActionListener(new BtnGrupeListener(this));
        btnPrivate.addActionListener(new BtnPrivateListener(this));

    }

    class BtnSendListener implements ActionListener {
        private ClientFrame frame;
        private Client client;

        public BtnSendListener(ClientFrame frame, Client client) {
            this.frame = frame;
            this.client = client;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String content = txtSendMessages.getText();
            if("".equals(content)){
                JOptionPane.showMessageDialog(frame,"输入内容不能为空","错误警告",JOptionPane.ERROR_MESSAGE);
            }else {
                Message message=new Message(null,client.getUser().getId(),content, LocalDateTime.now());
                if(displayPrivate) {
                    message.setMessageType(MessageType.MESSAGE_SEND_PRIVATE);
                    message.setReceiverID(frame.txtReceiverID.getText());
                    txtAllMessages.append("你对"+frame.txtReceiverID.getText()+":"+content+"\n");
                }
                else {
                    message.setMessageType(MessageType.MESSAGE_SEND_GRUPE);
                }
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(client.getSocket().getOutputStream());//send Message to Server
                    oos.writeObject(message);
                    oos.flush();
                } catch (IOException ec) {
                    ec.printStackTrace();
                }finally {
                    frame.txtSendMessages.setText("");
                    frame.txtReceiverID.setText("");
                }
            }
        }
    }

    class BtnGrupeListener implements ActionListener {
        private ClientFrame frame;

        public BtnGrupeListener(ClientFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            displayPrivate=false;
            frame.panelReceiverID.setVisible(false);
            frame.revalidate();
            frame.repaint();
        }
    }

    class BtnPrivateListener implements ActionListener {
        private ClientFrame frame;

        public BtnPrivateListener(ClientFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            displayPrivate=true;
            frame.panelReceiverID.setVisible(true);
            frame.revalidate();
            frame.repaint();
        }
    }

    class BtnGetOnlineListener implements ActionListener {
        private Client client;
        public BtnGetOnlineListener(Client client) {
            this.client = client;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ObjectOutputStream oos=new ObjectOutputStream(client.getSocket().getOutputStream());
                Message message=new Message(null,client.getUser().getId(),"",LocalDateTime.now());
                message.setMessageType(MessageType.MESSAGE_VIEW_ONLINEUSER);
                oos.writeObject(message);
                oos.flush();
            } catch (IOException ec) {
                ec.printStackTrace();
            }
        }
    }
}
