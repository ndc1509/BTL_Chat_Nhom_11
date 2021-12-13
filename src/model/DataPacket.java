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
public class DataPacket implements Serializable{
    private static final long serialVersionUID = 4L;
    private int code;
    private Object object;
    private List<Object> listObject;
    public DataPacket() {
    }

    public DataPacket(int code, Object object) {
        this.code = code;
        this.object = object;
    }

    public DataPacket(Object object) {
        this.object = object;
    }

    public DataPacket(int code, List<Object> listObject) {
        this.code = code;
        this.listObject = listObject;
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<Object> getListObject() {
        return listObject;
    }

    public void setListObject(List<Object> listObject) {
        this.listObject = listObject;
    }
    
    
}
