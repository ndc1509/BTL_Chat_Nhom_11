/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.Socket;

public class ChatController implements Runnable{
    private String myName;
    private String friendName;
    private ChatView chatView;
    
    public ChatController(ChatView chatView){
        this.chatView = chatView;
    }
    
    public void revMes(String mes){
        
    }
    
    public void sendMes(){
        
    }

    @Override
    public void run() {
        
    } 
}