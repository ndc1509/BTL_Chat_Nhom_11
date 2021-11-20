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
public class Account implements Serializable{
    private static final long serialVersionUID = 100L;
    
    private int id;
    private String username;
    private String password;
    private int online;

    public Account(int id, String username, String password, int online) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.online = online;
    }
    
    public Account(int id, String username, int online){
        this.id = id;
        this.username = username;
        this.online = online;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int isOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }
    
    
}
