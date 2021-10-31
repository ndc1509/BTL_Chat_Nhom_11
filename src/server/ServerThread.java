/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import static server.Server.log;

/**
 *
 * @author Cuong
 */
public class ServerThread implements Runnable{
        private Socket clientSocket;
        private int clientNumber;
        private BufferedReader in;
        private PrintWriter out;
        private boolean isClosed; 
        
        public int getClientNumber(){
            return clientNumber;
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
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                write("Bạn là client #" + clientNumber);
                
                while(!isClosed){
                    clientResponse = in.readLine();
                    opcode(clientResponse, out);
                }
            } catch(Exception e){
                log("Lỗi khi xử lý client #" + clientNumber);
                e.printStackTrace();
            } finally{
                try {
                    clientSocket.close();
                } catch (Exception e) {
                    log("Lỗi khi khởi động kết nối");
                }
            }
            log("Kết nối với client #" + clientNumber +" đã kết thúc");         
        }
        
        //Ma lenh thuc thi 
        public void opcode(String clientResponse, PrintWriter out) throws Exception{
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
                            write(authenticationFlag.toString());
                            write("Chào mừng " + username + "!");                            
                        } else {
                            log("Sai username hoặc password");
                            write(authenticationFlag.toString());
                            write("Sai username hoặc password");                                                     
                        }
                        Arrays.fill(params, null);
                        break;
                    } catch(Exception ex){
                        ex.printStackTrace();
                    }
                //Tao tai khoan moi
                case (1):
                    try{
                        String username = params[1];
                        String password = params[2];
                        
                        Boolean addFlag = false;

                        if(addNewAccount(username, password))
                        {
                            addFlag = true;
                            write(addFlag.toString());
                            write("Tạo tài khoản " + username + " thành công!");                           
                        }
                        else
                        {
                            addFlag = false;
                            write(addFlag.toString());
                            write("Tạo tài khoản không thành công");
                        }
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
                    
                //Logout
                case (2):
                    try {
                        String username = params[1];                            
                        setOffline(username);                        
                        Boolean logoutFlag = true;                       
                        write(logoutFlag.toString());                        
                        log(username + " đã ngắt kết nối đến server");
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
            }
        }
        
        public static Boolean authenticate(String username, String password) throws Exception{
            log(MySqlDB.getPassword(username));
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
        
        public void write(String message){
            out.println(message);
            out.flush();
        }
    }