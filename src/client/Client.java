/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import model.Account;

/**
 *
 * @author Cuong
 */
public class Client {
    private Socket clientSocket;
    private LoginView loginView;
    private MainMenu mainMenu;
    
    private RequestView requestView;
    private Account account;
    
    private HashMap listChatView = new HashMap();
    
    private String clientResponse;
    private String serverResponse;
    private Thread thread;
    
    private ArrayList<String> globalList = new ArrayList<>();
    private ArrayList<String> frRequestList = new ArrayList<>();
    private ArrayList<String> frList = new ArrayList<>();
    
    private BufferedReader in;
    private BufferedWriter out;
    
    public Client(){               
        LoginView loginView = new LoginView(this);
        loginView.setVisible(true);
        this.loginView = loginView;
        setUpSocket();
    }    

    public ArrayList<String> getFrRequestList() {
        return frRequestList;
    }
    
    public String getAccountName(){
        return account.getUsername();
    }
    public void login(String username, String password){
        try {
            clientResponse = packageString("0", username, password);
            sendMsg(clientResponse);
            this.account = new Account(username, password); 
        } catch (Exception e) {
        }        
    }
    
    public void register(String username, String password, String passwordConfirm){
        try {
            clientResponse = packageString("1", username, password);
            sendMsg(clientResponse);        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void logout(int input){
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
                        log("Kết nối đến " + hostname + " port " + port);
                        clientSocket = new Socket(hostname, port);
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                        if(clientSocket.isConnected())
                            log("Kết nối đến " + clientSocket.getRemoteSocketAddress() + " đã được thiết lập"); 
                        String serverResponse = receiveMsg();  
                        
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
                        if(frList!=null)
                            viewFriends();
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
                        if(frList!=null)
                            viewFriends();
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                //FriendList
                case (53):
                    try{
                        frList.clear();
                        for(int i=1; i<params.length; i++)
                            frList.add(params[i]);
                        if(frList!=null)
                            viewFriends();
                        Arrays.fill(params, null);
                        break;
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Login
                case (0):
                    try {
                        if(params[1].equals("true")){                                          
                            MainMenu mainMenu = new MainMenu(this);
                            this.mainMenu = mainMenu;
                            mainMenu.setTitle(account.getUsername());
                            loginView.dispose();
                        } else{
                            loginView.showErrorMessage("Sai username hoặc password","Lỗi");
                        }
                        Arrays.fill(params, null);
                        break;    
                    } catch (Exception e) {
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
                    
                //Lay tat ca yeu cau ket ban
                case(40):
                    try {
                        frRequestList.clear();
                        for(int i=1; i<params.length; i++)
                            frRequestList.add(params[i]);
                        if(this.requestView != null)
                            updateRequest();
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                
                //Lay 1 yeu cau ket ban moi
                case(41):
                    try {
                        frRequestList.add(params[1]);
                        if(this.requestView != null)
                            updateRequest();
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break; 
                //Yeu cau ket ban duoc chap nhan    
                case(42):
                    try {
                        frList.add(params[1]);
                        if(frList!=null)
                            viewFriends();
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break; 
                // Nhan yeu cau chat
                case(28):
                    try {
                       String friend = params[1];
                       ChatView newChatView = new ChatView(this, account.getUsername(), friend);
                       listChatView.put(friend, newChatView);
                       Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break; 
                //nhan tin nhan 1-1
                case(29):
                    try {
                        String mes = params[1];
                        String friendName = params[2];
                        ChatView tmp = (ChatView) listChatView.get(friendName);
                        tmp.addMess(mes);
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break; 
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
            Client controller = new Client();            
        } catch (Exception ex) {
            ex.printStackTrace();
        }            
    }
    
    public void sendAcceptResponse(String username){
        try{
            sendMsg("45," + username);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendDeclineResponse(String username){
        try {
            sendMsg("46," + username);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendFriendRequest(String username){
        try {
            sendMsg("47,"+username);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendRequestChat(String friendName){
        try {
            sendMsg("28," + friendName);
            ChatView newChatView = new ChatView(this, account.getUsername(), friendName);
            listChatView.put(friendName, newChatView);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendToFriend(String mes, String friendName){
        try {
            sendMsg("29," + mes + "," + friendName);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeChat(String friend){
        listChatView.remove(friend);
    }

    public void viewRequest() {
        this.requestView = new RequestView(this);
        requestView.setTitle(account.getUsername());
        requestView.setRequest(frRequestList);
    }
    
    public void updateRequest(){
        requestView.setRequest(frRequestList);
    }

    public void viewFriends(){
        List<String> frOnline = new ArrayList<>(frList);
        List<String> frOffline = new ArrayList<>();
        for(int i=0; i<frList.size(); i++)
            System.out.println(frList.get(i));
        if(globalList.isEmpty()){
            frOffline = frList;
            mainMenu.setFrOfflineList(frOffline);
            return;
        }
        else {
            frOnline.retainAll(globalList);
            
            for(int k = 0; k<frList.size(); k++){
                if(!frOnline.contains(frList.get(k)))
                    frOffline.add(frList.get(k));
            }
                
            mainMenu.setFrOfflineList(frOffline);
            mainMenu.setFrOnlineList(frOnline);
        }
    }
}
