/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Cuong
 */
public class Server {
    private ServerSocket serverSocket;
    private static volatile ServerThreadBUS serverThreadBUS;
    
    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }
    
    public static ServerThreadBUS getServerThreadBUS(){
        return serverThreadBUS;
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
            System.out.println("Không thể mở server");
            ex.printStackTrace();
        }
    }
    
    //Dong server
    public void stopServer(){
        try {
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Không thể đóng server");
            ex.printStackTrace();
        }
    }
    //Chap nhan ket noi tu cac client su dung ThreadPool
    public void acceptClient(){
        int clientNumber = 0;
        serverThreadBUS = new ServerThreadBUS();
        
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                20,
                100,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10)
        );
        
        try{
            while(!Thread.currentThread().isInterrupted()){
                log("Đang lắng nghe port " + serverSocket.getLocalPort());
                Socket clientSocket = serverSocket.accept();
                log("Kết nối mới đến từ " + clientSocket.getRemoteSocketAddress());     
                ServerThread serverThread = new ServerThread(clientSocket, clientNumber++);
                serverThreadBUS.add(serverThread);
                log("Số client đang kết nối: " + serverThreadBUS.getLength());
                executor.execute(serverThread);
            }
        } catch(Exception ex){
            ex.printStackTrace();
        } finally{
            stopServer();
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
