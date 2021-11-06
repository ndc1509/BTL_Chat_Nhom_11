/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Cuong
 */
public class Account {
    private int id;
    private String username;
    private String password;
    private boolean online;

    public Account(String username, String password, boolean online) {
        this.username = username;
        this.password = password;
        this.online = online;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.online = false;
    }
    
    public Account(String username, boolean online){
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

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
    
    
}
