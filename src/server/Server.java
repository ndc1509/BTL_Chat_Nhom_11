/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cuong
 */
public class Server {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }
    
    
    //Ghi server log 
    public static void log(String s){
        System.out.println(s);
    }
    
    //Mo server
    public void startServer(int port){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Khong the mo server");
            ex.printStackTrace();
        }
    }
    
    //Dong server
    public void stopServer(){
        try {
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Khong the dong server");
            ex.printStackTrace();
        }
    }
    //Chap nhan ket noi tu cac client su dung ThreadPool
    public void acceptClient(){
        int clientNumber = 0;
        
        ExecutorService threadPool = Executors.newCachedThreadPool();
        
        try{
            while(!Thread.currentThread().isInterrupted()){
                log("Dang lang nghe port" + serverSocket.getLocalPort());
                Socket clientSocket = serverSocket.accept();
                log("IP moi ket noi" + clientSocket.getRemoteSocketAddress());
                
                threadPool.submit(new ClientHandler(clientSocket, clientNumber++));
            }
        } catch(Exception ex){
            ex.printStackTrace();
        } finally{
            threadPool.shutdown();
        }
    }    
    // cai nay le ra static    
    public class ClientHandler implements Runnable{
        private final Socket clientSocket;
        private int clientNumber;

        public ClientHandler(Socket clientSocket, int clientNumber){
            this.clientSocket = clientSocket;
            this.clientNumber = clientNumber;
            log("Ket noi moi voi client #" + clientNumber + " tai " + clientSocket.toString());
        }

        @Override
        public void run() {
            String clientResponse;
            
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //khac
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                out.println("Ban la client #" + clientNumber);
                
                while(true){
                    clientResponse = in.readLine();
                    opcode(clientResponse, out);
                }
            } catch(Exception e){
                log("Loi khi xu ly client #" + clientNumber);
                e.printStackTrace();
            } finally{
                try {
                    clientSocket.close();
                } catch (Exception e) {
                    log("Loi khi dong ket noi");
                }
            }
            log("Ket noi voi client #" + clientNumber +" da ket thuc");         
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
                            out.println(authenticationFlag.toString());
                            out.flush();
                            out.println("Chao mung " + username);
                            out.flush();
                        } else {
                            log("Sai username hoac password");
                            out.println(authenticationFlag.toString());
                            out.flush();
                            out.println("Sai username hoac password");
                            out.flush();                           
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
                            out.println(addFlag.toString());
                            out.flush();
                            out.println("Tao tai khoan thanh cong " + username);
                            out.flush();
                        }
                        else
                        {
                            addFlag = false;
                            log(addFlag.toString());
                            out.println(addFlag.toString());
                            out.flush();
                            out.println("Tao tai khoan khong thanh cong");
                            out.flush();
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
                       
                        out.println(logoutFlag.toString());
                        out.flush();
                        
                        log(username + " da ngat ket noi den server");
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
    }

    public static void main(String[] args) {
        int port = 19750;
        try{
            new Server(port).acceptClient();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
