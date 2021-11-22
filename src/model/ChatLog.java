/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ChatLog implements Serializable{
    private ArrayList<String> chatlog = new ArrayList<>();
    
    public void addMes(String mes){
        this.chatlog.add(mes);
    }

    public ArrayList<String> getChatlog() {
        return chatlog;
    }
    
    
}
