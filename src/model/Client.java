/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class Client {
    private String nickName;
    private int id;
    private String URLImage;
    private String username;

    public Client(int id, String nickName, String username) {
        this.nickName = nickName;
        this.id = id;
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    public String toString(){
        return this.id+","+this.nickName+","+this.username;
    }
}
