/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultListModel;
import model.Account;
import model.Client;
import model.Friend;

/**
 *
 * @author Cuong
 */
public class ClientController {
    private Socket clientSocket;
    private LoginView loginView;
    private MainMenu mainMenu;

    private Account account;
    private Client client;
    
    private String clientResponse;
    private String serverResponse;
    private Thread thread;
    
    private ArrayList<String> globalList = new ArrayList<>();
    private ArrayList<Friend> friends;
    private HashMap<String, Friend> friendMap;
    
    private BufferedReader in;
    private BufferedWriter out;
    
    public ClientController(){
               
        LoginView loginView = new LoginView(this);
        loginView.setVisible(true);
        this.loginView = loginView;
        setUpSocket();
    }    
                
    public void Login(String username, String password){
        try {
            clientResponse = packageString("0", username, password);
            sendMsg(clientResponse);
            this.account = new Account(username, password); 
        } catch (Exception e) {
        }        
    }
    
    public void Register(String nickname, String username, String password, String passwordConfirm){
        try {
            clientResponse = packageString("1", nickname, username, password);
            sendMsg(clientResponse);        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Logout(int input){
        if(input == 0){
            try {
                clientResponse = packageString("2", account.getUsername());
                log(clientResponse);
                sendMsg(clientResponse);
            } catch (Exception ex) {
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
    
    private void setUpSocket(){
        try {
            thread = new Thread(){
                @Override
                public void run() {

                    try{
                        String hostname = "127.0.0.1";
                        int port = 19750;
                        log("Ket noi den " + hostname + " port " + port);
                        clientSocket = new Socket(hostname, port);
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                        if(clientSocket.isConnected())
                            log("Ket noi den" + clientSocket.getRemoteSocketAddress() + " da duoc thiet lap"); 
                        String serverResponse = receiveMsg();
                        log("\nServer phan hoi " + serverResponse);  
                        //luồng nhận dữ liệu của client
                        while(!clientSocket.isClosed()){
                            serverResponse = receiveMsg();
                            opcode(serverResponse);
                        }                
                    } catch(Exception e){
                        e.printStackTrace();
                    } finally{
                        try{
                            clientSocket.close();
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }                
            };
            thread.run();
        } catch (Exception e) {
        }
    }
        
        public void opcode(String response) throws Exception{
            String[] params = response.split(",");
            int code = Integer.parseInt(params[0]);
            switch(code){
                //Online users
                case (50):
                    try{
                        for(int i=1; i<params.length; i++)

                            globalList.add(params[i]);
                        if(globalList!=null)
                            mainMenu.setGlobalList(globalList);
                        Arrays.fill(params, null);
                        break;
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                //1 user goOnline
                case (51):
                    try{
                        
                            globalList.add(params[1]);
                            mainMenu.setGlobalList(globalList);
                        
                        Arrays.fill(params, null);
                        break;
                    }catch(Exception e){
                        e.printStackTrace();
           
                    
                    
                    }
                    break;
                //1 user goOffline
                case (52):
                    try {
                        if(!params[1].equals(""))
                            for(int i=0; i<globalList.size(); i++)
                                if(globalList.get(i).equals(params[1])){
                                    globalList.remove(i);
                                    mainMenu.setGlobalList(globalList);
                                }
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                //Login
                case (0):
                    try {
                        if(params[1].equals("true")){   
                            System.out.println(params[2] + " " + params[3] + " " + params[4]);
                            client = new Client(Integer.parseInt(params[2]), params[3], params[4]);
                            MainMenu mainMenu = new MainMenu(this, client, null);
                            this.mainMenu = mainMenu;
                            loginView.dispose();
                        } else{
                            loginView.showErrorMessage("Sai username hoặc password","Lỗi");
                        }
                        Arrays.fill(params, null);
                        break;    
                    } catch (Exception e) {
                    }
                    break;
                // load danh sach ban be
                    case (49):
                    try {
                        if(params[1].equals("true")){   
                            ArrayList<Friend> list = new ArrayList<>();
                            if(!params[2].isEmpty()){
                                for(int i = 2; i < params.length ;i += 3){
                                    Friend f = new Friend(Integer.parseInt(params[i]), params[i+1], Integer.parseInt(params[i+2]));
                                    list.add(f);
                                }
                                
                            }
                            this.friends = list;
                            this.mainMenu.setFriends(list);
                        } else{
                            loginView.showErrorMessage("Sai username hoặc password","Lỗi");
                        }
                        Arrays.fill(params, null);
                        break;    
                    } catch (Exception e) {
                    }
                    break;
                // nhận thông tin broadcast một user mới online, nếu có trong ds bạn bè thì hiển thị thành online
                    case (29):
                        try {
                            int id = Integer.parseInt(params[1]);
                            int isOnline = Integer.parseInt(params[2]);
                        for(Friend f : friends){
                            if(f.getId() == id){
                                f.setIsOnline(isOnline);
                                this.mainMenu.setFriends(friends);
                                break;
                            }
                        }
                        Arrays.fill(params, null);
                        break;    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                //Tao tai khoan
                case (1):
                    try {                         
                        if(params[1].equals("true"))
                            loginView.showMessage("Đăng ký thành công");
                        else 
                            loginView.showErrorMessage("Đăng ký không thành công", "Lỗi");
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                //Xac nhan logout
                case (2):
                    try {                        
                        log("Đóng kết nối thành công");
                        clientSocket.close();
                        mainMenu.dispose();
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        log("Đóng kết nối không thành công");
                    }
                default:
                    Arrays.fill(params, null);                       
                    break;
            }
        }
    
    public String receiveMsg(){
        String serverResponse = null;
        try{
            serverResponse = in.readLine();
            log("Server: " + serverResponse);
        } catch(Exception e){
            e.printStackTrace();
        }
        return serverResponse;
    }
    
    public void sendMsg(String clientResponse) throws IOException{
        log("Client: " + clientResponse);
        out.write(clientResponse);
        out.newLine();
        out.flush();
    }
    
    public static void main(String[] args) {                

        try{
            ClientController controller = new ClientController();
        } catch (Exception ex) {
            ex.printStackTrace();
        }            
    }
}
