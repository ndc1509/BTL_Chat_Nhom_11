/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import model.Account;

/**
 *
 * @author Cuong
 */
public class ClientController {
    private LoginView loginView;
    private MainMenu mainMenu;
    private Client client;
    private Account account;
    
    private String clientResponse;
    private String serverResponse;
    
    private Boolean loginFlag;
    private Boolean addFlag;
    private Boolean logoutFlag;

    public ClientController(Client client) {
        this.client = client; 
        LoginView loginView = new LoginView(this);
        loginView.setVisible(true);
        this.loginView = loginView;
    }    
                
    public void Login(String username, String password){
        //Truyen package
        clientResponse = packageString("0", username, password);
        client.sendMsg(clientResponse);
        //Nhan phan hoi
        serverResponse = client.receiveMsg();
        loginFlag = Boolean.valueOf(serverResponse);
        serverResponse = client.receiveMsg();
        //loginUser = 
        log("\nServer phản hồi " + serverResponse);
        if(loginFlag){
            this.account = new Account(username, password, true);
            MainMenu mainMenu = new MainMenu(this);
            mainMenu.setVisible(true);
            this.mainMenu = mainMenu;
            loginView.dispose();
        } else{
            loginView.showErrorMessage("Sai username hoặc password","Lỗi");
        }
        //Dang nhap qua 5 lan
    }
    
    public void Register(String username, String password, String passwordConfirm){
        clientResponse = packageString("1", username, password);
        client.sendMsg(clientResponse);

        serverResponse = client.receiveMsg();
        addFlag = Boolean.valueOf(serverResponse);
        serverResponse = client.receiveMsg();
        log("\nServer phản hồi " + serverResponse);    
        if(addFlag)
            loginView.showMessage("Đăng ký thành công");
        else loginView.showErrorMessage("Đăng ký không thành công", "Lỗi");
    }
    
    public void Logout(int input){
        if(input == 0){
            try {
                clientResponse = packageString("2", account.getUsername());
                client.sendMsg(clientResponse);

                serverResponse = client.receiveMsg();
                logoutFlag = Boolean.valueOf(serverResponse);

                if(logoutFlag){
                    log("Đóng kết nối thành công");
                    client.stop();
                    mainMenu.dispose();
                }
            } catch (Exception ex) {
                log("Đóng kết nối không thành công");
                ex.printStackTrace();
            }
        }
    }

    public static void log(String string) {
        System.out.println(string);
    }
    
    public static String packageString(String...args){
        String response = "";
        for (String arg : args) 
            response += "," + arg;       
        return response.substring(1);
    }    
}
