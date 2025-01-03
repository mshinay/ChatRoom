package com.client;

import com.utility.Utility;

public class ClientMenu {
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
                    Client client=new Client();
                    if(client.login()){
                    showSecondMenu();
                    }else{
                        System.out.println("登录失败");
                    }
                    break;
                case "9":

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
            System.out.println("3:退出");
            String key = Utility.GetString(1);
            switch (key) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    loop = false;
                    break;
                default:
                    break;
            }
        }


    }
}
