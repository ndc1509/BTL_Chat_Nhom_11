/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Cuong
 */
public class Room implements Serializable{
    private static final long serialVersionUID = 7L;
    private int id;
    private String name;                                                                                                                                                     
    private Account creator;
    private List<Account> member;

    public Room() {
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public List<Account> getMember() {
        return member;
    }

    public void setMember(List<Account> member) {
        this.member = member;
    }
    
    
}
