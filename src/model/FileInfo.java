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
public class FileInfo implements Serializable{
    private static final long serialVersionUID = 99L;
    
    private String destDIR;
    private String srcDIR;
    private String name;
    private long size;
    private int piecesOfFile;
    private int lastByteLength;
    private byte[] dataBytes;
    private String status;

    public FileInfo(String destDIR, String srcDIR, String name, long size, int piecesOfFile, int lastByteLength, byte[] dataBytes, String status) {
        this.destDIR = destDIR;
        this.srcDIR = srcDIR;
        this.name = name;
        this.size = size;
        this.piecesOfFile = piecesOfFile;
        this.lastByteLength = lastByteLength;
        this.dataBytes = dataBytes;
        this.status = status;
    }

    public FileInfo() {
    }

    public String getDestDIR() {
        return destDIR;
    }

    public void setDestDIR(String destDIR) {
        this.destDIR = destDIR;
    }

    public String getSrcDIR() {
        return srcDIR;
    }

    public void setSrcDIR(String srcDIR) {
        this.srcDIR = srcDIR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getPiecesOfFile() {
        return piecesOfFile;
    }

    public void setPiecesOfFile(int piecesOfFile) {
        this.piecesOfFile = piecesOfFile;
    }

    public int getLastByteLength() {
        return lastByteLength;
    }

    public void setLastByteLength(int lastByteLength) {
        this.lastByteLength = lastByteLength;
    }

    public byte[] getDataBytes() {
        return dataBytes;
    }

    public void setDataBytes(byte[] dataBytes) {
        this.dataBytes = dataBytes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
