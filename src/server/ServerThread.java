/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.AddFriendRequest;
import model.ChatLog;
import model.DataPacket;
import model.FileInfo;
import model.Message;
import model.Room;
import static server.Server.log;

/**
 *
 * @author Cuong
 */
public class ServerThread implements Runnable{
    
        //Ma lenh client nhan
    private static final int LOGIN_SUCCESS = 1;
    private static final int LOGIN_FAIL = 2;
    private static final int REGISTER_SUCCESS = 3;
    private static final int REGISTER_FAIL = 4;   
    private static final int LOGOUT_SUCCESS = 5;
    private static final int SOMEONE_GO_OFFLINE = 6;
    private static final int SOMEONE_GO_ONLINE = 7;
    private static final int LIST_GLOBAL = 8;
    private static final int LIST_FRIEND_REQUESTS = 9;
    private static final int LIST_FRIEND = 10;
    private static final int LIST_ROOM = 11;
    private static final int LIST_FILE = 12;
    private static final int FILE = 13;

    private static final int MESSAGE = 14;
    private static final int MESSAGE_GLOBAL = 15;
    private static final int CHAT_REQUEST = 16;
    private static final int CHAT_REQUEST_ACCEPTED = 17;
    private static final int CHAT_REQUEST_DECLINED = 18;
    private static final int CHAT_CLOSE = 19;
    private static final int UPDATE_ROOM = 20;
    private static final int CHAT_LOG = 21;
    private static final int MESSAGE_ROOM = 22;
    private static final int CHAT_LOG_ROOM = 23; 
    private static final int IMG = 24;
    private static final int LIST_FILE_ROOM = 25;
    //private static final int IMG_ROOM = 26;
    //Ma lenh server nhan
    private static final int LOGIN = 1;
    private static final int REGISTER = 2;
    private static final int LOGOUT = 3;
    private static final int SEND_NEW_ADD_FR_REQUEST = 4;
    private static final int SEND_FR_REQUEST_RESPONSE = 5;
    private static final int SEND_CHAT_REQUEST = 6;
    private static final int SEND_ACCEPT_CHAT_REQUEST = 7;
    private static final int SEND_DECLINE_CHAT_REQUEST = 8;
    private static final int SEND_CHAT_CLOSE = 9;
    private static final int SEND_CHAT_MESSAGE = 10;
    private static final int SEND_CHAT_MESSAGE_GLOBAL = 11;
    public static final int SEND_FILE = 12;
    public static final int REQUEST_FILE_LIST = 13;
    public static final int REQUEST_FILE = 14;
    private static final int REQUEST_CREATE_ROOM = 15;
    private static final int SEND_UPDATE_ROOM = 16;
    private static final int SEND_CHAT_MESSAGE_ROOM = 17;
    private static final int REQUEST_ROOM_CHATLOG = 18;
    public static final int SEND_FILE_TO_ROOM = 19;
        
        private Socket clientSocket;
        private int clientNumber;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private boolean isClosed; 
        private Account account;
        
        private String srcFilePath = "C:\\Users\\Cuong\\Documents\\NetBeansProjects\\Clone chat\\Chat_v1.1\\src\\server\\FileStorage\\";
        public int getClientNumber(){
            return clientNumber;
        }
        
        public int getAccountID(){
            return this.account.getId();
        }
        
        public ServerThread(Socket clientSocket, int clientNumber){
            this.clientSocket = clientSocket;
            this.clientNumber = clientNumber;
            log("K???t n???i m???i v???i client #" + clientNumber + " t???i " + clientSocket.toString());
            isClosed = false;
        }

        @Override
        public void run() {
            DataPacket data;     
            try{
                writeObj(new DataPacket((String)"B???n l?? client #" + clientNumber));
                while(!isClosed){
                    ois = new ObjectInputStream(clientSocket.getInputStream());
                    data = (DataPacket) ois.readObject();
                    log("Client " + clientNumber + ": " + data.getCode());
                    opcode(data);
                }
            } catch(Exception e){
                log("L???i khi x??? l?? client #" + clientNumber);
                e.printStackTrace();
            } finally{
                try {    
                    isClosed = true;
                    Server.getServerThreadBUS().remove(clientNumber);            
                } catch (Exception e) {
                    log("L???i khi kh???i ?????ng k???t n???i");
                }
            }
            log("K???t n???i v???i client #" + clientNumber +" ???? k???t th??c");         
        }
        
        public void writeObj(DataPacket data) throws IOException{
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            log("Server to Client "+ clientNumber + ": " + data.getCode());
            oos.writeObject(data);
            oos.flush();           
        }
        
        //Ma lenh thuc thi 
        public void opcode(DataPacket data) throws Exception{
            int code = data.getCode(); 
            switch(code){
                //login
                case(LOGIN):
                    try{
                        Account acc = (Account) data.getObject();
                        if(authenticate(acc)){
                            setOnline(acc);
                            acc = MySqlDB.getAccount(acc);
                            this.account = acc;
                            writeObj(new DataPacket(LOGIN_SUCCESS, account)); 
                            
                            Server.getServerThreadBUS().broadcast(account, new DataPacket(SOMEONE_GO_ONLINE, account)); 
                            
                            if(getOnlineAccounts()!=null)                               
                                writeObj(new DataPacket(LIST_GLOBAL, getOnlineAccounts()));
                            if(getFriendRequest(account)!=null)
                                writeObj(new DataPacket(LIST_FRIEND_REQUESTS, getFriendRequest(account)));
                            if(getFriends(account) != null)
                                writeObj(new DataPacket(LIST_FRIEND, getFriends(account)));
                            if(getRoomList(account)!=null)
                                writeObj(new DataPacket(LIST_ROOM, getRoomList(account)));                               
                        } else {
                            log("Sai username ho???c password");
                            writeObj(new DataPacket(LOGIN_FAIL, null));                                                   
                        }
                        break;
                    } catch(Exception ex){
                        ex.printStackTrace();
                    }
                    break;
                //Tao tai khoan moi
                case (REGISTER):
                    try{
                        Account acc = (Account) data.getObject();
                        if(addNewAccount(acc))
                            writeObj(new DataPacket(REGISTER_SUCCESS, null));  
                        else                       
                            writeObj(new DataPacket(REGISTER_FAIL, null));  
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
                    break;
                //Logout
                case (LOGOUT):
                    try {                                                                                                                                      
                        setOffline(account);
                        Server.getServerThreadBUS().broadcast(account, new DataPacket(SOMEONE_GO_OFFLINE, account));
                        writeObj(new DataPacket(LOGOUT_SUCCESS, null));   
                        log(account.getUsername() + " ???? ng???t k???t n???i ?????n server");                    
                        isClosed = true;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                    
                //Nhan phan hoi ket ban
                case (SEND_FR_REQUEST_RESPONSE):
                    try {
                        AddFriendRequest req = (AddFriendRequest) data.getObject();
                        if(req.getStatus() == 1){
                            acceptFriendRequest(req);
                            writeObj(new DataPacket(LIST_FRIEND_REQUESTS, getFriendRequest(account)));
                            if(getFriends(account) != null)
                                writeObj(new DataPacket(LIST_FRIEND, getFriends(account)));                        
                            Server.getServerThreadBUS().unicast(req.getSender(), new DataPacket(LIST_FRIEND, getFriends(req.getSender())));
                        } else {
                            declineFriendRequest(req);
                            writeObj(new DataPacket(LIST_FRIEND_REQUESTS, getFriendRequest(account)));
                        }
                        
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Gui loi moi ket ban
                case (SEND_NEW_ADD_FR_REQUEST):
                    try {
                        AddFriendRequest req = (AddFriendRequest) data.getObject();
                        if(sendFriendRequest(req))
                            Server.getServerThreadBUS().unicast(req.getReceiver(), new DataPacket(LIST_FRIEND_REQUESTS, getFriendRequest(req.getReceiver())));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Dong y tro chuyen
                case (SEND_ACCEPT_CHAT_REQUEST):
                    try {                    
                        Account sender = (Account) data.getObject();
                        Account receiver = account;
                        ChatLog chatlog = MySqlDB.getChatLog(sender, receiver);

                        List<Object> list = new ArrayList<>();
                        if(chatlog == null)
                            list.add("empty chatlog");
                        else list.add("chatlog");
                        list.add(chatlog);
                        list.add(sender);
                        writeObj(new DataPacket(CHAT_LOG, list));
                        
                        list.clear();
                        if(chatlog == null)
                            list.add("empty chatlog");
                        else list.add("chatlog");
                        list.add(chatlog);
                        list.add(receiver);
                        Server.getServerThreadBUS().unicast(sender, new DataPacket(CHAT_REQUEST_ACCEPTED, list));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;   
                //Tu choi tro chuyen
                case (SEND_DECLINE_CHAT_REQUEST):
                    try {
                        Account sender = (Account) data.getObject();
                        Server.getServerThreadBUS().unicast(sender, new DataPacket(CHAT_REQUEST_DECLINED, account));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                break;
                //Gui yeu cau tro chuyen
                case (SEND_CHAT_REQUEST): 
                    try{
                        Account receiver = (Account) data.getObject();
                        Account sender = account;   
                        Server.getServerThreadBUS().unicast(receiver, new DataPacket(CHAT_REQUEST, sender));  
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Gui 1 tin nhan cho ban be
                case (SEND_CHAT_MESSAGE): 
                    try{
                        Message msg = (Message) data.getObject();
                        
                        Server.getServerThreadBUS().unicast(msg.getReceiver(), new DataPacket(MESSAGE, msg));
                        MySqlDB.saveMes(msg);
                        writeObj(new DataPacket(MESSAGE, msg));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Nhan yeu cau ket thuc chat
                case (SEND_CHAT_CLOSE): 
                    try{
                        Account receiver = (Account) data.getObject();
                        Account sender = account;
                        Server.getServerThreadBUS().unicast(receiver, new DataPacket(CHAT_CLOSE, sender));      
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Server nhan file
                case(SEND_FILE):
                    try {
                        FileInfo f = (FileInfo) data.getObject();
                        if(f != null){
                            createFile(f);
                            List<Object> list = new ArrayList<>();
                            Account sender = f.getSender();
                            List<FileInfo> files = getFilesFromSender(sender, account);
                            list.add(false);
                            list.add(sender);
                            list.add(files);
                            Server.getServerThreadBUS().unicast(f.getReceiver(), new DataPacket(LIST_FILE, list));
                            log("Nhan file thanh cong");
                        }        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                //Nhan danh sach file duoc share
                case (REQUEST_FILE_LIST):
                    try{
                        List<Object> receivedList = data.getListObject();
                        String type = (String) receivedList.get(0);
                        List<Object> list = new ArrayList<>();
                        boolean isEmpty = false;
                        if(type.equals("FRIEND")){
                            Account friend = (Account) receivedList.get(1);
                            List<FileInfo> files = getFilesFromSender(friend, account);
                            if(files != null){ 
                                list.add(isEmpty);
                                list.add(friend);
                                list.add(files);
                                writeObj(new DataPacket(LIST_FILE, list));
                            } else { //Khong co file
                                isEmpty = true;
                                list.add(isEmpty);
                                list.add(friend);
                                writeObj(new DataPacket(LIST_FILE, list));
                            } 
                        }
                        else if(type.equals("ROOM")) {
                            Room room = (Room) receivedList.get(1);
                            List<FileInfo> files = getFilesFromRoom(room);                               
                            if(files != null){
                                list.add(isEmpty);
                                list.add(room);
                                list.add(files);
                                writeObj(new DataPacket(LIST_FILE_ROOM, list));
                            } else { //Khong co file
                                isEmpty = true;
                                list.add(isEmpty);
                                list.add(room);
                                writeObj(new DataPacket(LIST_FILE_ROOM, list));
                            } 
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Yeu cau tai file
                case (REQUEST_FILE):
                    try {
                        FileInfo file = (FileInfo) data.getObject();
                        FileInfo fileToSend = getFileInfo(srcFilePath + file.getName());
                        fileToSend.setSender(file.getSender());
                        if(file.getReceiver()!= null)
                            fileToSend.setReceiver(file.getReceiver());      
                        else fileToSend.setRoom(file.getRoom());
                        writeObj(new DataPacket(FILE, fileToSend));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                //Gui tin nhan global
                case (SEND_CHAT_MESSAGE_GLOBAL): 
                    try{
                        Message msg = (Message) data.getObject();
                        Server.getServerThreadBUS().broadcast(account, new DataPacket(MESSAGE_GLOBAL, msg));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;    
                //Yeu cau tao phong chat
                case (REQUEST_CREATE_ROOM): 
                    try{
                        Room room = (Room) data.getObject();
                        if(createRoom(room)){
                            writeObj(new DataPacket(LIST_ROOM, getRoomList(account)));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Them thanh vien vao phong
                case (SEND_UPDATE_ROOM): 
                    try{                    
                        List<Object> list = data.getListObject();
                        String type = (String) list.get(0);
                        Account targetMember = (Account) list.get(1);
                        Room room = (Room) list.get(2);
                        if(type.equals("ADD")){
                            if(MySqlDB.insertMember(room, targetMember)){
                                Room newRoom = MySqlDB.getRoomDetails(room);
                                sendMsgToRoom(room, serverLog(targetMember.getUsername() + " ???? ???????c th??m v??o ph??ng b???i " + account.getUsername(), room));                                
                                for(Account acc:newRoom.getMember()){
                                    List<Object> newList = new ArrayList<>();
                                    newList.add("MEMBER_ADDED");
                                    newList.add(targetMember);
                                    newList.add(newRoom);
                                    newList.add(getRoomList(acc));
                                    Server.getServerThreadBUS().unicast(acc, new DataPacket(UPDATE_ROOM, newList));
                                }                   
                            }
                        } else{
                            if(MySqlDB.removeMember(room, targetMember)){
                                Room newRoom = MySqlDB.getRoomDetails(room);
                                List<Object> newList = new ArrayList<>();
                                //G???i th??ng b??o cho m???i th??nh vi??n trong ph??ng
                                if(type.equals("KICK"))
                                    sendMsgToRoom(room, serverLog(targetMember.getUsername() + " ???? b??? ???? kh???i ph??ng b???i " + account.getUsername(), room));
                                else if(type.equals("LEAVE"))
                                    sendMsgToRoom(room, serverLog(targetMember.getUsername() + " ???? r???i ph??ng ", room));    
                                //C???p nh???t danh s??ch ph??ng cho c??c th??nh vi??n trong ph??ng
                                for(Account acc:newRoom.getMember()){    
                                    newList.clear();
                                    if(type.equals("KICK"))
                                        newList.add("MEMBER_KICKED");
                                    else if(type.equals("LEAVE"))
                                        newList.add("MEMBER_LEFT");
                                    newList.add(targetMember);
                                    newList.add(newRoom);
                                    newList.add(getRoomList(acc));
                                    Server.getServerThreadBUS().unicast(acc, new DataPacket(UPDATE_ROOM, newList));                                    
                                }  
                                //G???i th??ng ??i???p cho ng?????i b??? ????/ ng?????i r???i kh???i ph??ng
                                newList.clear();
                                if(type.equals("KICK"))
                                        newList.add("MEMBER_KICKED");
                                else if(type.equals("LEAVE"))
                                    newList.add("MEMBER_LEFT");
                                newList.add(targetMember);
                                    newList.add(newRoom);
                                newList.add(getRoomList(targetMember));
                                Server.getServerThreadBUS().unicast(targetMember, new DataPacket(UPDATE_ROOM, newList));
                            }                       
                        }                        
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case (SEND_CHAT_MESSAGE_ROOM): 
                    try{
                        Message msg = (Message) data.getObject();                            
                        if(msg.getType().equals(Message.Type.IMG))
                            sendImgToRoom(msg);
                        else sendMsgToRoom(msg.getRoom(), msg);    
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Yeu cau chat log room
                case (REQUEST_ROOM_CHATLOG):
                    try{
                        Room r = (Room) data.getObject();
                        ChatLog log = MySqlDB.getChatLog(r);
                        List<Object> list = new ArrayList<>();
                        if(log != null){
                            list.add("chatlog");
                            list.add(log);
                            list.add(r);
                            List<Message> msgList = log.getChatlog();
                            for(Message msg:msgList){
                                if(msg.getType().equals(Message.Type.IMG)){
                                    FileInfo file = getFileInfo(srcFilePath + msg.getContent());
                                    writeObj(new DataPacket(FILE, file));
                                }
                            }
                        } else list.add("empty chatlog");
                        writeObj(new DataPacket(CHAT_LOG_ROOM, list));
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                //Y??u c???u g???i file ?????n ph??ng chat
                case (SEND_FILE_TO_ROOM):
                    try {
                        FileInfo f = (FileInfo) data.getObject();
                        if(f != null){
                            createFile(f);
                            List<Object> list = new ArrayList<>();
                            Account sender = f.getSender();
                            Room room = f.getRoom();
                            List<FileInfo> files = getFilesFromRoom(room);
                            list.add(false);
                            list.add(room);
                            list.add(files);
                            Server.getServerThreadBUS().unicast(sender, new DataPacket(LIST_FILE_ROOM, list));
                            log("Nhan file thanh cong");
                        }  
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
        
        public static Boolean authenticate(Account acc) throws Exception{
            String pass = MySqlDB.getPassword(acc);
            if(pass == null)
                return false;
            if(acc.getPassword().equals(pass))
                return true;
            return false;
        }
        
        
        public static void setOnline(Account acc) throws Exception{
            acc.setOnline(1);
            MySqlDB.setOnline(acc);
        }
        
        public static void setOffline(Account acc) throws Exception{
            acc.setOnline(0);
            MySqlDB.setOnline(acc);
        }
        
        public static Boolean addNewAccount(Account acc) throws Exception{
            return MySqlDB.addAccount(acc);
        }
        
        public List<Account> getOnlineAccounts(){    
            try {                
                return MySqlDB.getOnlineGlobal(account);
            } catch (Exception ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        
        public List<Account> getFriends(Account acc){          
            try {                      
                return MySqlDB.getFriends(acc);
            } catch (Exception ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        
        
        //Ket ban 
        public List<AddFriendRequest> getFriendRequest(Account acc){
            try {
                return MySqlDB.getFriendRequest(acc);
            } catch (Exception ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        
        public Boolean sendFriendRequest(AddFriendRequest request){
            return  MySqlDB.sendFriendRequest(request);
        }
        
        public void acceptFriendRequest(AddFriendRequest request){
            MySqlDB.acceptFriendRequest(request);
        }
        
        public void declineFriendRequest(AddFriendRequest request){
            MySqlDB.declineFriendRequest(request);
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
                    MySqlDB.addFile(fileInfo);
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
        
        public List<FileInfo> getFilesFromSender(Account sender, Account receiver){
            try {                
                return MySqlDB.getFile(sender , receiver);
            } catch (Exception ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
         
        public List<FileInfo> getFilesFromRoom(Room room){
            try {
                return MySqlDB.getFile(room);
            } catch (Exception ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        
        public List<Room> getRoomList(Account acc){
            return MySqlDB.getRoomList(acc);
        }
        
        public Boolean createRoom(Room room){
            return MySqlDB.createRoom(room);
        }
        
        public Message serverLog(String string, Room r){
            LocalDateTime datetime = LocalDateTime.now();
            Account server = new Account(0, "SERVER");
            return new Message(server, r, string, datetime, Message.Type.MESSAGE);
        }
        
        public void sendMsgToRoom(Room room, Message msg){
            try {
                List<Account> members = room.getMember();
                Account sender = msg.getSender();
                writeObj(new DataPacket(MESSAGE_ROOM, msg));
                for(Account acc:members){
                    if(acc.getId() != account.getId())
                        Server.getServerThreadBUS().unicast(acc, new DataPacket(MESSAGE_ROOM, msg));
                }
                MySqlDB.saveMes(msg);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void sendImgToRoom(Message msg){
        try {
            List<Object> list = new ArrayList<>();
            Room room = msg.getRoom();
            FileInfo imgToSend = getFileInfo(srcFilePath + msg.getContent());
            imgToSend.setRoom(room);
            imgToSend.setSender(msg.getSender());
            list.add(imgToSend);
            list.add(msg);
            List<Account> members = room.getMember();
            writeObj(new DataPacket(IMG, list));
            for(Account acc:members){
                if(acc.getId() != account.getId())
                    Server.getServerThreadBUS().unicast(acc, new DataPacket(IMG, list));
            }
            MySqlDB.saveMes(msg);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
}