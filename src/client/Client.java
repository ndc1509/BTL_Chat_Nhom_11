/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Cuong
 */
public class Client {
    private ClientController controller;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    
    
    public Client(String hostname, int port) throws IOException{
        log("Ket noi den " + hostname + " port " + port);
        clientSocket = new Socket(hostname, port);
        if(clientSocket.isConnected())
            log("Ket noi den" + clientSocket.getRemoteSocketAddress() + " da duoc thiet lap");
        
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }
    
    public void stop() throws Exception{
        clientSocket.close();
    }
    
    private static void log(String message) {
        System.out.println(message);
    }
    
    public String receiveMsg(){
        String serverResponse = null;
        try{
            serverResponse = in.readLine();
        } catch(Exception e){
            e.printStackTrace();
        }
        return serverResponse;
    }
    
    public void sendMsg(String clientResponse){
        out.println(clientResponse);
        out.flush();
    }
    
    public static void main(String[] args) {                
        String hostname = "127.0.0.1";
        int port = 19750;
        try{
            Client client = new Client(hostname, port);
            
            String serverResponse = client.receiveMsg();
            log("\nServer phan hoi " + serverResponse);       
            ClientController controller = new ClientController(client);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }            
    }
}
