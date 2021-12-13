/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable{
    private static final long serialVersionUID = 6L;
    private Account sender;
    private Account receiver;
    private Room room;
    private String content;
    private LocalDateTime datetime;

    public Message() {
    }

    
    
    public Message(Account sender, Account receiver, String content, LocalDateTime datetime) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.datetime = datetime;
    }

    public Message(Account sender, Room r, String content, LocalDateTime datetime) {
        this.sender = sender;
        this.room = r;
        this.content = content;
        this.datetime = datetime;
    }
    
    public Message(Account sender, String content, LocalDateTime datetime) {
        this.sender = sender;
        this.content = content;
        this.datetime = datetime;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
    
    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    
    
    
    public String getDateTimeToString(){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.datetime.format(myFormatObj);
    }
    
}
