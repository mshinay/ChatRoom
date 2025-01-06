package com.client.GUI;





import com.client.Client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginFrame extends JFrame {
    private JLabel lbUserID;
    private JLabel lbPassword;
    private JTextField tfUserID;
    private JPasswordField tfPassword;
    private JButton btnLogin;
    private JButton btnEixt;

    private JPanel plUserID;
    private JPanel plPassword;
    private JPanel plbtn;

    private Box box;

    private Client client;

    public static void main(String[] args) {
        LoginFrame frame = new LoginFrame();
    }

  public LoginFrame() {
        //this.setSize(1000, 500);
        client = new Client();
        this.setTitle("Login");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        this.setVisible(true);
    }

    public void init() {
        lbUserID = new JLabel("用户名:");
        lbPassword =new JLabel("密 码:");
        tfUserID = new JTextField(20);
        tfPassword = new JPasswordField(20);
        btnLogin = new JButton("登录");
        btnEixt=new JButton("退出");
        plUserID=new JPanel(new FlowLayout());
        plPassword=new JPanel(new FlowLayout());
        plbtn=new JPanel(new FlowLayout());

        box=Box.createVerticalBox();

        plUserID.add(lbUserID);
        plUserID.add(tfUserID);

        plPassword.add(lbPassword);
        plPassword.add(tfPassword);

        plbtn.add(btnLogin);
        plbtn.add(btnEixt);

        box.add(plUserID);
        box.add(plPassword);
        box.add(plbtn);

        this.setContentPane(box);
        this.pack();

        btnEixt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnLogin.addActionListener(new ButtonActionListener(this) );

    }

    class ButtonActionListener implements ActionListener {

        private JFrame frame;
        public ButtonActionListener(JFrame frame) {
            this.frame = frame;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String userID = tfUserID.getText();
            String password = tfPassword.getText();
            if("".equals(userID) || "".equals(password)){
                JOptionPane.showMessageDialog(frame,"ID或密码不能为空","错误警告",JOptionPane.ERROR_MESSAGE);
            }else {
                try {
                    if(client.loginGUI(userID,password)){
                        new ClientFrame(client);
                        frame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(frame,"登录失败","错误警告",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
