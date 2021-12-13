/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Cuong
 */
public class AddFriendRequest implements Serializable{
    private static final long serialVersionUID = 2L;
    private Account sender;
    private Account receiver;
    private int status;
    private String message;

    public AddFriendRequest() {
    }

    public AddFriendRequest(Account sender, Account receiver, int status, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.message = message;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
