/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Messager implements Serializable{
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime datetime;

    public Messager(String sender, String receiver, String content, LocalDateTime datetime) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.datetime = datetime;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
    
    public String getDateTimeToString(){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.datetime.format(myFormatObj);
    }
    
}
