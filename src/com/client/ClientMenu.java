package com.client;

import com.utility.Utility;

public class ClientMenu {
    Client client=new Client();
    public static void main(String[] args) throws Exception {
        new ClientMenu().showMenu();

    }
    boolean loop = true;
    public void showMenu() throws Exception {

        while (loop){
            System.out.println("输入1 登录" );
            System.out.println("输入9 退出");
            String key= Utility.GetString(1);
            switch (key){
                case "1":

                    if(client.login()) {
                      showSecondMenu();
                    }
                    break;
                case "9":
                    loop = false;
                    break;
                default:

                    break;
            }
        }

    }

    void showSecondMenu() {
        while(loop) {
            System.out.println("1:私聊");
            System.out.println("2:群发");
            System.out.println("3:查询在线用户");
            System.out.println("4:退出");
            String key = Utility.GetString(1);
            switch (key) {
                case "1":
                    System.out.print("私聊对象：");
                    String receiverID = Utility.GetString(10);
                    client.chatPrivate(receiverID);
                    break;
                case "2":
                    client.grupeChat();
                    break;
                case "3":
                    client.viewOnlineAccounts();
                    break;
                case "4":
                    loop = false;
                    client.Logout();
                    break;
                default:
                    break;
            }
        }


    }
}
