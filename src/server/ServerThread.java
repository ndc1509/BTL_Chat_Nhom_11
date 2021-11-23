/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Account;
import model.ChatLog;
import model.FileInfo;
import model.Messager;
import static server.Server.log;

/**
 *
 * @author Cuong
 */
public class ServerThread implements Runnable{
        
        private Socket clientSocket;
        private int clientNumber;
        private DataInputStream in;
        private DataOutputStream out;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private boolean isClosed; 
        private Account account;
        private FileInfo fileInfo;
        
        private String srcFilePath = "C:\\Users\\Cuong\\Documents\\NetBeansProjects\\Clone chat\\Chat_v1.1\\src\\server\\FileStorage\\";
        public int getClientNumber(){
            return clientNumber;
        }
        
        public String getAccountUsername(){
            return this.account.getUsername();
        }
        
        public ServerThread(Socket clientSocket, int clientNumber){
            this.clientSocket = clientSocket;
            this.clientNumber = clientNumber;
            log("Kết nối mới với client #" + clientNumber + " tại " + clientSocket.toString());
            isClosed = false;
        }

        @Override
        public void run() {
            String clientResponse;
            
            try{
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());
                
                write("Bạn là client #" + clientNumber);
                
                while(!isClosed){
                    clientResponse = in.readUTF();
                    log("Client: " + clientResponse);
                    opcode(clientResponse, out);
                }
            } catch(Exception e){
                log("Lỗi khi xử lý client #" + clientNumber);
                e.printStackTrace();
            } finally{
                try {
                    setOffline(account.getUsername());
                    Server.getServerThreadBUS().boardcast(clientNumber, "52" + "," + account.getUsername());
                    clientSocket.close();
                } catch (Exception e) {
                    log("Lỗi khi khởi động kết nối");
                }
            }
            log("Kết nối với client #" + clientNumber +" đã kết thúc");         
        }
        
        //Ma lenh thuc thi 
        public void opcode(String clientResponse, DataOutputStream out) throws Exception{
            Boolean authenticationFlag = false;
            String[] params = clientResponse.split(",");
            int code = Integer.parseInt(params[0]);
            
            switch(code){
                //login
                case(0):
                    try{
                        String username = params[1];
                        String password = params[2];
                        
                        if(authenticate(username, password)){
                            setOnline(username);
                            authenticationFlag = true;
                            this.account = getAccount(username);
                            write("0," + authenticationFlag.toString()); 
                            oos = new ObjectOutputStream(clientSocket.getOutputStream());
                            writeObj(account);
                            if(getOnlineAccounts()!="")
                                write("50" + getOnlineAccounts());
                            Server.getServerThreadBUS().boardcast(clientNumber,"51" + "," + account.getUsername());
                            if(!getFriendRequest().equals(""))
                                write("40" + getFriendRequest());
                            if(!getFriends().equals(""))
                                write("53" + getFriends());
                        } else {
                            log("Sai username hoặc password");
                            write("0," + authenticationFlag.toString());                                                   
                        }
                        Arrays.fill(params, null);
                        break;
                    } catch(Exception ex){
                        ex.printStackTrace();
                    }
                    break;
                //Tao tai khoan moi
                case (1):
                    try{
                        String username = params[1];
                        String password = params[2];
                        
                        Boolean addFlag = false;

                        if(addNewAccount(username, password))
                            write("1,true");
                        else
                            write("1,false");
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
                    break;
                //Logout
                case (2):
                    try {
                        
                        String username = params[1];                            
                        setOffline(username);                        
                                             
                        write("2");  
                        
                        log(username + " đã ngắt kết nối đến server");
                        Server.getServerThreadBUS().boardcast(clientNumber, "52" + "," + username);
                        isClosed = true;
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                    
                //Accept friend
                case (45):
                    try {
                        String username = params[1];                                                   
                        acceptFriendRequest(username);
                        write("40" + getFriendRequest());
                        write("53" + getFriends());
                        Server.getServerThreadBUS().unicast(username, "42," + account.getUsername());
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                    
                //Decline friend
                case (46):
                    try {
                        String username = params[1];                                                   
                        declineFriendRequest(username);
                        write("40" + getFriendRequest());
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                
                    
                //Get friend request
                case (47):
                    try {
                        String username = params[1];                                                   
                        if(sendFriendRequest(username)==true)
                            Server.getServerThreadBUS().unicast(username, "41," + account.getUsername());
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                // yeu cau tro chuyen 28,username
                case (28): 
                    try{
                        String receiver = params[1].trim();
                        String myName = params[2].trim();
                        System.out.println(receiver + "/" + myName);
                        write("27," + receiver);
                        ChatLog chatlog = MySqlDB.getChatLog(myName, receiver);
                        writeObj(chatlog);
                        
                        Server.getServerThreadBUS().unicast(receiver, "28," + account.getUsername());
                        Server.getServerThreadBUS().unicast(receiver, "27," + account.getUsername());
                        Server.getServerThreadBUS().unicast(receiver, chatlog);
                        for(String s : chatlog.getChatlog()){
                            System.out.println(s);
                        }
                        Arrays.fill(params, null);
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                // send mes cho friend
                case (29): 
                    try{
                        String receiver = params[1].trim();
                        ois = new ObjectInputStream(clientSocket.getInputStream());
                        Messager mess = (Messager) ois.readObject();
                        Server.getServerThreadBUS().unicast(receiver, "29,"+ account.getUsername());
                        Server.getServerThreadBUS().unicast(receiver, mess);
                        MySqlDB.saveMes(mess);
                        Arrays.fill(params, null);
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Nhan file
                case(30):
                    try {
                        String user = params[1];
                        getFile();
                        MySqlDB.addFile(fileInfo.getName(), account.getId(), user);
                        fileInfo = null;
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                //Lay danh sach file duoc share
                case (31):
                    try{
                        String user = params[1];
                        List<FileInfo> files = getFilesFromSender(user);
                        write("31,"+ user + "," + files.size());
                        for(int i=0; i<files.size(); i++)
                            writeObj(files.get(i));
                        Arrays.fill(params, null);
                        break;
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Yeu cau tai file
                case (32):
                    try {
                        String filename = params[1];
                        write("32");
                        sendFile(srcFilePath + filename);
                        Arrays.fill(params, null);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    Arrays.fill(params, null);
                    break;
            }
        }
        
        public static Boolean authenticate(String username, String password) throws Exception{
            String pass = MySqlDB.getPassword(username);
            if(password.equals(pass))
                return true;
            return false;
        }
        
        public static Account getAccount(String username) throws Exception{
            Account acc = MySqlDB.getAccount(username);
            return acc;
        }
        
        public static void setOnline(String username) throws Exception{
            MySqlDB.setOnline(username, true);
        }
        
        public static void setOffline(String username) throws Exception{
            MySqlDB.setOnline(username, false);
        }
        
        public static Boolean addNewAccount(String username, String password) throws Exception{
            return MySqlDB.addAccount(username, password);
        }
        
        public String getOnlineAccounts(){
            String str = "";
            try{
                ArrayList<String> list = MySqlDB.getOnlineGlobal(account.getUsername());                
                if(list!=null){
                    for(int i=0; i<list.size(); i++){                        
                        str += "," + list.get(i);
                    }
                }
                return str;
            } catch(Exception e){
                e.printStackTrace();
            }
            return str;             
        }
        
        public String getFriends(){
            String str = "";
            try{
                List<String> list = MySqlDB.getFriends(account.getId());                
                if(list!=null){
                    for(int i=0; i<list.size(); i++){                        
                        str += "," + list.get(i);
                    }
                }
                return str;
            } catch(Exception e){
                e.printStackTrace();
            }
            return str;
        }
        
        public void write(String message) throws IOException{
            log("Server: " + message);
            out.writeUTF(message);
            out.flush();
        }
        
        public void writeObj(Object object) throws IOException{
            log("Server gửi object");
            
            oos.writeObject(object);
            oos.flush();
        }
        //Ket ban 
        public String getFriendRequest(){
            String str = "";
            try{
                ArrayList<String> list = MySqlDB.getFriendRequest(account.getId());                
                if(list!=null){
                    for(int i=0; i<list.size(); i++){                        
                        str += "," + list.get(i);
                    }
                }
                return str;
            } catch(Exception e){
                e.printStackTrace();
            }
            return str;  
        }
        
        public Boolean sendFriendRequest(String username){
            return  MySqlDB.sendFriendRequest(account.getId(), username);
        }
        
        public void acceptFriendRequest(String username){
            MySqlDB.acceptFriendRequest(username, account.getId());
        }
        
        public void declineFriendRequest(String username){
            MySqlDB.declineFriendRequest(username, account.getId());
        }
        
        //Lam viec voi file
        private boolean createFile(FileInfo fileInfo){
            BufferedOutputStream bos = null;
            
            try {
                if(fileInfo != null){
                    log(fileInfo.getDestDIR() + fileInfo.getName());
                    File fileReceive = new File(fileInfo.getDestDIR() + fileInfo.getName());
                    bos = new BufferedOutputStream(new FileOutputStream(fileReceive));                    
                    bos.write(fileInfo.getDataBytes());
                    bos.flush();
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
        
        public void getFile(){            
            try{
                ois = new ObjectInputStream(clientSocket.getInputStream());
                this.fileInfo = (FileInfo) ois.readObject();
                if(fileInfo != null){
                    createFile(fileInfo);
                    log("Nhan file thanh cong");
                }                
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        
        private FileInfo getFileInfo(String srcFilePath){
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
        
        public List<FileInfo> getFilesFromSender(String sender){
            List<FileInfo> fileInfos = new ArrayList<>();
            try{
                List<String> list = MySqlDB.getFile(sender , account.getId());                
                if(list!=null){
                    for(int i=0; i<list.size(); i++){                        
                        FileInfo file = new FileInfo();
                        file.setName(list.get(i));
                        file.setSender(sender);
                        fileInfos.add(file);
                    }
                }
                return fileInfos;
            } catch(Exception e){
                e.printStackTrace();
            }
            return fileInfos;
        }
        
        public void sendFile(String source){     
        try{           
            FileInfo fileInfo = getFileInfo(source);
            writeObj(fileInfo);                 
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    }