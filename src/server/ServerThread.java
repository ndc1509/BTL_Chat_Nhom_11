/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Account;
import static server.Server.log;

/**
 *
 * @author Cuong
 */
public class ServerThread implements Runnable{
        
        private Socket clientSocket;
        private int clientNumber;
        private BufferedReader in;
        private BufferedWriter out;
        private boolean isClosed; 
        private Account account;
        
        public int getClientNumber(){
            return clientNumber;
        }
        
        public String getAccountUsername(){
            return this.account.getUsername();
        }
        
        public ServerThread(Socket clientSocket, int clientNumber){
            this.clientSocket = clientSocket;
            this.clientNumber = clientNumber;
            log("Kết nối mới với client #" + clientNumber + " tại " + clientSocket.toString());
            isClosed = false;
        }

        @Override
        public void run() {
            String clientResponse;
            
            try{
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                
                write("Bạn là client #" + clientNumber);
                
                while(!isClosed){
                    clientResponse = in.readLine();
                    log("Client: " + clientResponse);
                    opcode(clientResponse, out);
                }
            } catch(Exception e){
                log("Lỗi khi xử lý client #" + clientNumber);
                e.printStackTrace();
            } finally{
                try {
                    setOffline(account.getUsername());
                    Server.getServerThreadBUS().boardcast(clientNumber, "52" + "," + account.getUsername());
                    clientSocket.close();
                } catch (Exception e) {
                    log("Lỗi khi khởi động kết nối");
                }
            }
            log("Kết nối với client #" + clientNumber +" đã kết thúc");         
        }
        
        //Ma lenh thuc thi 
        public void opcode(String clientResponse, BufferedWriter out) throws Exception{
            Boolean authenticationFlag = false;
            String[] params = clientResponse.split(",");
            int code = Integer.parseInt(params[0]);
            
            switch(code){
                //login
                case(0):
                    try{
                        String username = params[1];
                        String password = params[2];
                        
                        if(authenticate(username, password)){
                            setOnline(username);
                            authenticationFlag = true;
                            account = new Account(username, true);
                            write("0," + authenticationFlag.toString());                           
                            if(getOnlineAccounts()!="")
                                write("50" + getOnlineAccounts());
                            Server.getServerThreadBUS().boardcast(clientNumber,"51" + "," + account.getUsername());
                            if(!getFriendRequest().equals(""))
                                write("40" + getFriendRequest());
                            if(!getFriends().equals(""))
                                write("53" + getFriends());
                        } else {
                            log("Sai username hoặc password");
                            write("0," + authenticationFlag.toString());                                                   
                        }
                        Arrays.fill(params, null);
                        break;
                    } catch(Exception ex){
                        ex.printStackTrace();
                    }
                    break;
                //Tao tai khoan moi
                case (1):
                    try{
                        String username = params[1];
                        String password = params[2];
                        
                        Boolean addFlag = false;

                        if(addNewAccount(username, password))
                            write("1,true");
                        else
                            write("1,false");

                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
                    break;
                //Logout
                case (2):
                    try {
                        
                        String username = params[1];                            
                        setOffline(username);                        
                                             
                        write("2");  
                        
                        log(username + " đã ngắt kết nối đến server");
                        Server.getServerThreadBUS().boardcast(clientNumber, "52" + "," + username);
                        isClosed = true;
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                    
                //Accept friend
                case (45):
                    try {
                        String username = params[1];                                                   
                        acceptFriendRequest(username);
                        write("40" + getFriendRequest());
                        write("53" + getFriends());
                        Server.getServerThreadBUS().unicast(username, "42," + account.getUsername());
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                    
                //Decline friend
                case (46):
                    try {
                        String username = params[1];                                                   
                        declineFriendRequest(username);
                        write("40" + getFriendRequest());
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                
                    
                //Get friend request
                case (47):
                    try {
                        String username = params[1];                                                   
                        if(sendFriendRequest(username)==true)
                            Server.getServerThreadBUS().unicast(username, "41," + account.getUsername());
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                // yeu cau tro chuyen 28,username
                case (28): 
                    try{
                        String friendName = params[1]; 
                        Server.getServerThreadBUS().unicast(friendName, "28," + account.getUsername());
                        Arrays.fill(params, null);
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                  // send mes cho friend
                 case (29): 
                    try{
                        String mes = params[1]; 
                        String friendName = params[2];
                        Server.getServerThreadBUS().unicast(friendName, "29, "+ mes + "," + account.getUsername());
                        Arrays.fill(params, null);
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    Arrays.fill(params, null);
                    break;
            }
        }
        
        public static Boolean authenticate(String username, String password) throws Exception{
            if(password.equals(MySqlDB.getPassword(username)))
                return true;
            return false;
        }
        
        public static void setOnline(String username) throws Exception{
            MySqlDB.setOnline(username, true);
        }
        
        public static void setOffline(String username) throws Exception{
            MySqlDB.setOnline(username, false);
        }
        
        public static Boolean addNewAccount(String username, String password) throws Exception{
            return MySqlDB.addAccount(username, password);
        }
        
        public String getOnlineAccounts(){
            String str = "";
            try{
                ArrayList<String> list = MySqlDB.getOnlineGlobal(account.getUsername());                
                if(list!=null){
                    for(int i=0; i<list.size(); i++){                        
                        str += "," + list.get(i);
                    }
                }
                return str;
            } catch(Exception e){
                e.printStackTrace();
            }
            return str;             
        }
        
        public String getFriends(){
            String str = "";
            try{
                List<String> list = MySqlDB.getFriends(account.getUsername());                
                if(list!=null){
                    for(int i=0; i<list.size(); i++){                        
                        str += "," + list.get(i);
                    }
                }
                return str;
            } catch(Exception e){
                e.printStackTrace();
            }
            return str;
        }
        
        public void write(String message) throws IOException{
            log("Server: " + message);
            out.write(message);
            out.newLine();
            out.flush();
        }
        
        public String getFriendRequest(){
            String str = "";
            try{
                ArrayList<String> list = MySqlDB.getFriendRequest(account.getUsername());                
                if(list!=null){
                    for(int i=0; i<list.size(); i++){                        
                        str += "," + list.get(i);
                    }
                }
                return str;
            } catch(Exception e){
                e.printStackTrace();
            }
            return str;  
        }
        
        public Boolean sendFriendRequest(String username){
            return  MySqlDB.sendFriendRequest(account.getUsername(), username);
        }
        
        public void acceptFriendRequest(String username){
            MySqlDB.acceptFriendRequest(username, account.getUsername());
        }
        
        public void declineFriendRequest(String username){
            MySqlDB.declineFriendRequest(username, account.getUsername());
        }
    }