/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ChatLog implements Serializable{
    private static final long serialVersionUID = 3L;
    private List<Message> chatlog = new ArrayList<>();
    
    public void addMes(Message mes){
        this.chatlog.add(mes);
    }

    public List<Message> getChatlog() {
        return chatlog;
    }

    public void setChatlog(List<Message> chatlog) {
        this.chatlog = chatlog;
    }

}
