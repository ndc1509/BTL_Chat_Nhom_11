/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.control;

import client.view.FileListView;
import static client.control.Client.log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.DataPacket;
import model.FileInfo;
import model.Room;

/**
 *
 * @author Cuong
 */
public class FileHandler {
    private Account account;
    private Client controller;
   
    public String destDIR = "C:\\Users\\Cuong\\Documents\\NetBeansProjects\\Clone chat\\Chat_v1.1\\src\\server\\FileStorage\\";
    public String saveDIR = "C:\\Users\\Cuong\\Documents\\NetBeansProjects\\Clone chat\\Chat_v1.1\\src\\client\\FileStorage\\";
    
    public HashMap listFileListView = new HashMap();
    public HashMap listFileListViewForRoom = new HashMap();
    
    public FileHandler(Client controller) {
        this.controller = controller;
        this.account = controller.getAccount();
    }
    
    //Tao file de gui cho 1 ban be
    private FileInfo getFileInfo(String srcFilePath, String destDIR, Account receiver){
        FileInfo fileInfo = null;
        BufferedInputStream bis = null;
        try{
            File sourceFile = new File(srcFilePath);
            bis = new BufferedInputStream(new FileInputStream(sourceFile));
            fileInfo = new FileInfo();
            byte[] fileBytes = new byte[(int) sourceFile.length()];
            
            bis.read(fileBytes, 0, fileBytes.length);
            fileInfo.setName(sourceFile.getName());
            fileInfo.setDataBytes(fileBytes);
            fileInfo.setDestDIR(destDIR);
            fileInfo.setSender(account);
            fileInfo.setReceiver(receiver);
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            try {
                if(bis != null){
                    bis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileInfo;
    }
    //Tao file de gui trong nhom
    public FileInfo getFileInfo(String srcFilePath, String destDIR, Room room){
        FileInfo fileInfo = null;
        BufferedInputStream bis = null;
        try{
            File sourceFile = new File(srcFilePath);
            bis = new BufferedInputStream(new FileInputStream(sourceFile));
            fileInfo = new FileInfo();
            byte[] fileBytes = new byte[(int) sourceFile.length()];
            
            bis.read(fileBytes, 0, fileBytes.length);
            fileInfo.setName(sourceFile.getName());
            fileInfo.setDataBytes(fileBytes);
            fileInfo.setDestDIR(destDIR);
            fileInfo.setSender(account);
            fileInfo.setRoom(room);
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            try {
                if(bis != null){
                    bis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileInfo;
    }
    //Tao file
    public boolean createFile(FileInfo fileInfo){
        BufferedOutputStream bos = null;

        try {
            if(fileInfo != null){
                File fileReceive = new File(saveDIR + fileInfo.getName());
                bos = new BufferedOutputStream(new FileOutputStream(fileReceive));                    
                bos.write(fileInfo.getDataBytes());
                bos.flush();
                //System.out.println(fileReceive.getTotalSpace());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally{
            try {
                if(bos != null){
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    //Gui file toi ban be
    public void sendFile(String source, Account receiver){     
        try{           
            FileInfo file = getFileInfo(source, destDIR, receiver);
            controller.sendObj(new DataPacket(controller.SEND_FILE, file));
            log("Gửi file thành công");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    // Gui file toi 1 phong
    public void sendFile(String source, Room room){     
        try{           
            FileInfo file = getFileInfo(source, destDIR, room);
            controller.sendObj(new DataPacket(controller.SEND_FILE_TO_ROOM, file));
            log("Gửi file đến phòng " + room.getName() + " thành công");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
//    //Gui anh den ban be
//    public void sendImg(String source, Account receiver){     
//        try{           
//            FileInfo file = getFileInfo(source, destDIR, receiver);
//            controller.sendObj(new DataPacket(controller.SEND_IMG, file));
//            log("Gửi ảnh thành công");
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    // Gui ảnh den 1 phong
//    public void sendImg(String source, Room room){     
//        try{           
//            FileInfo file = getFileInfo(source, destDIR, room);
//            controller.sendObj(new DataPacket(controller.SEND_IMG_TO_ROOM, file));
//            log("Gửi ảnh đến phòng " + room.getName() + " thành công");
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//    }
    
    //Yeu cau danh sach file ban be gui
    public void requestFileList(Account sender){
        try {
            FileListView view = (FileListView) listFileListView.get(sender.getId());
            if(view == null){
                List<Object> list = new ArrayList<>();
                list.add("FRIEND");
                list.add(sender);
                controller.sendObj(new DataPacket(controller.REQUEST_FILE_LIST, list));
            } else view.toFront();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Danh sach file trong nhom
    public void requestFileList(Room r){
        try {
            FileListView view = (FileListView) listFileListViewForRoom.get(r.getId());
            if(view == null){
                List<Object> list = new ArrayList<>();
                list.add("ROOM");
                list.add(r);
                controller.sendObj(new DataPacket(controller.REQUEST_FILE_LIST, list));
            } else view.toFront();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Show danh sach file ban be gui
    public void showFileList(List<FileInfo> list, Account sender){
        FileListView view = (FileListView) listFileListView.get(sender.getId());
        if(view == null)
            listFileListView.put(sender.getId(), new FileListView(controller, list, sender));
        else 
            view.toFront();
    }
    
    public void closeFileList(Account sender){
        listFileListView.remove(sender.getId());
    }
    
    //Show danh sach file trong nhom
    public void showFileList(List<FileInfo> list, Room r){
        FileListView view = (FileListView) listFileListViewForRoom.get(r.getId());
        if(view == null)
            listFileListViewForRoom.put(r.getId(), new FileListView(controller, list, r));
        else view.toFront();
    }
    
    public void closeFileList(Room r){
        listFileListViewForRoom.remove(r.getId());
    }

    //Tai file
    public void download(FileInfo file){                      
        try {
            controller.sendObj(new DataPacket(controller.REQUEST_FILE, file));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Kiem tra file ton tai khong
    public boolean isFileExisted(String name){
        File file = new File(saveDIR + name);
        //System.out.println(saveDIR + name + " co ton tai khong ?");
        return file.exists();
    }   
}
