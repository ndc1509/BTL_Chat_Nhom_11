/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.control;

import client.view.ChatRoomGlobal;
import client.view.ChatRoomView;
import client.view.ChatView;
import client.view.FileListView;
import client.view.LoginView;
import client.view.MainMenu;
import client.view.RequestView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

/**
 *
 * @author Cuong
 */
public class Client {
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
    private LoginView loginView;
    private MainMenu mainMenu;
    private ChatRoomGlobal chatGlobal;
    
    private RequestView requestView;
    private Account account;
    
    public HashMap listChatView = new HashMap();
    public HashMap listChatRoomView = new HashMap();
    
    private Thread thread;
    
    public FileHandler fileHandler;
    
    private List<Account>  globalList = new ArrayList<>();
    private List<AddFriendRequest> frRequestList = new ArrayList<>();
    private List<Account> frList = new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();
        
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
        
    public Client(){               
        LoginView loginView = new LoginView(this);
        loginView.setVisible(true);
        this.loginView = loginView;
        setUpSocket();
    }    
    
    public Account getAccount() {
        return account;
    }
    
    public List<AddFriendRequest> getFrRequestList() {
        return frRequestList;
    }
    
    public List<Account> getFrList(){
        return frList;
    }
    
    public static void log(String string) {
        System.out.println(string);
    }
    
    private void setUpSocket(){
        try {
            thread = new Thread(){
                @Override
                public void run() {
                    try{
                        String hostname = "127.0.0.1";
                        int port = 19750;
                        log("K???t n???i ?????n " + hostname + " port " + port);
                        clientSocket = new Socket(hostname, port);
                        if(clientSocket.isConnected())
                            log("K???t n???i ?????n " + clientSocket.getRemoteSocketAddress() + " ???? ???????c thi???t l???p"); 
                        DataPacket data = receiveObj();
                        log(data.getObject().toString());
                        while(!clientSocket.isClosed()){
                            data = receiveObj();
                            opcode(data);
                        }                
                    } catch(Exception e){
                        e.printStackTrace();
                    } finally{
                        try{
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }                
            };
            thread.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    public void opcode(DataPacket data) throws Exception{            
        int code = data.getCode();
        switch(code){         
            //Login thanh cong
            case (LOGIN_SUCCESS):
                try {                        
                    this.account = (Account) data.getObject();
                    MainMenu mainMenu = new MainMenu(this);
                    this.mainMenu = mainMenu;
                    mainMenu.setTitle(account.getUsername());
                    loginView.dispose();     
                    fileHandler = new FileHandler(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Login khong thanh cong
            case (LOGIN_FAIL):
                try {
                    loginView.showErrorMessage("????ng nh???p kh??ng th??nh c??ng","L???i");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;   

            //Tao tai khoan thanh cong
            case (REGISTER_SUCCESS):
                try {                         
                    loginView.showMessage("????ng k?? th??nh c??ng");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Tao tai khoan that bai
            case (REGISTER_FAIL):
                try {                         
                    loginView.showErrorMessage("????ng k?? kh??ng th??nh c??ng", "L???i");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //X??c nh???n logout th??nh c??ng
            case (LOGOUT_SUCCESS):
                try {                        
                    log("????ng k???t n???i th??nh c??ng");
                    clientSocket.close();
                    mainMenu.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                    log("????ng k???t n???i kh??ng th??nh c??ng");
                }

            //Nh???n danh s??ch c??c client tr??n to??n server
            case (LIST_GLOBAL):
                try{
                    globalList = (List<Account>) data.getObject();
                    if(globalList != null)
                        mainMenu.setGlobalList(globalList);
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;
            //Khi c?? 1 client kh??c chuy???n sang ONLINE
            case (SOMEONE_GO_ONLINE):
                try{               
                    Account acc = (Account) data.getObject();
                    globalList.add(acc);
                    mainMenu.setGlobalList(globalList);
                    if(frList!=null)
                        viewFriends();
                }catch(Exception e){
                    e.printStackTrace();                
                }
                break;
            //Khi c?? 1 client kh??c chuy???n sang OFFLINE
            case (SOMEONE_GO_OFFLINE):
                try {
                    Account offAccount = (Account) data.getObject();
                    if(!globalList.isEmpty()){
                        for(Iterator<Account> it = globalList.iterator(); it.hasNext();){
                            Account acc = it.next();
                            if(acc.getId() == offAccount.getId()){
                                it.remove();
                                mainMenu.setGlobalList(globalList);
                                break;
                            }
                        }
                    }
                    if(frList!=null)
                        viewFriends();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Nh???n danh s??ch b???n b??
            case (LIST_FRIEND):
                try{
                    frList = (List<Account>) data.getObject();
                    if(frList!=null)
                        viewFriends();
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;

                
            //C??C M?? L???NH X??? L?? L???I M???I K???T B???N       
            //L???y t???t c??? y??u c???u k???t b???n
            case(LIST_FRIEND_REQUESTS):
                try {
                    frRequestList = (List<AddFriendRequest>) data.getObject();
                    if(requestView != null)
                        requestView.setRequest(frRequestList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
                
            //C??C M?? L???NH CHAT 1-1
            //Nh???n y??u c???u chat 1-1
            case(CHAT_REQUEST):
                try {
                    Account sender = (Account) data.getObject();
                    int accept = mainMenu.acceptChat(sender);
                    if(accept == 0)
                        sendObj(new DataPacket(SEND_ACCEPT_CHAT_REQUEST, sender));
                    else 
                        sendObj(new DataPacket(SEND_DECLINE_CHAT_REQUEST, sender));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Ch???p nh???n chat 1-1                
            case(CHAT_REQUEST_ACCEPTED):
                try {
                    List<Object> list = data.getListObject();
                    String str = (String) list.get(0);
                    
                    Account receiver = (Account) list.get(2);
                    ChatView newChatView = new ChatView(this, account, receiver);
                    listChatView.put(receiver.getId(), newChatView);
                    if(str.equals("chatlog")){
                        ChatLog log = (ChatLog) list.get(1);
                        newChatView.loadChatLog(log);
                    }                      
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;    
            //T??? ch???i chat 1-1
            case(CHAT_REQUEST_DECLINED):
                try {
                    Account receiver = (Account) data.getObject();
                    mainMenu.alert(receiver.getUsername() + " ???? t??? ch???i tr?? chuy???n!", "Th??ng b??o");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;            
            //Nh???n chatlog 1-1
            case(CHAT_LOG):
                try {
                    List<Object> list = data.getListObject();
                    String str = (String) list.get(0);
                    ChatLog log = (ChatLog) list.get(1);
                    Account receiver = (Account) list.get(2);
                    
                    ChatView newChatView = new ChatView(this, account, receiver);
                    listChatView.put(receiver.getId(), newChatView);
                    if(str.equals("chatlog")){
                        newChatView.loadChatLog(log);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break; 
            //Nh???n tin nh???n 1-1
            case(MESSAGE):
                try {
                    Message msg = (Message) data.getObject();
                    Account sender = msg.getSender();
                    if(sender.getId() == account.getId()){
                        Account receiver = msg.getReceiver();
                        ChatView tmp = (ChatView) listChatView.get(receiver.getId());
                        tmp.addMess(msg);  
                    } else{
                        ChatView tmp = (ChatView) listChatView.get(sender.getId());
                        tmp.addMess(msg);  
                    }              
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break; 
            //????ng chat 1-1
            case(CHAT_CLOSE):
                try {
                    Message msg = (Message) data.getObject();
                    Account sender = msg.getSender();
                    ChatView tmp = (ChatView) listChatView.get(sender.getId());
                    tmp.showOff();
                    tmp.dispose();
                    listChatView.remove(sender.getId());     
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
                
            //C??C M?? L???NH X??? L?? FILE
            //Nh???n danh s??ch file
            case(LIST_FILE):
                try {      
                    List<Object> list = data.getListObject();
                    boolean isEmpty = (boolean) list.get(0);
                    Account sender = (Account) list.get(1);
                    List<FileInfo> files = new ArrayList<>();
                    if(!isEmpty)      
                        files = (List<FileInfo>) list.get(2);                                                       
                    FileListView view = (FileListView) fileHandler.listFileListView.get(sender.getId());
                    if(view != null){                            
                        view.setFiles(files);
                    }          
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break; 
            //Nh???n danh s??ch file t??? 1 ph??ng
            case(LIST_FILE_ROOM):
                try {
                    List<Object> list = data.getListObject();
                    boolean isEmpty = (boolean) list.get(0);
                    Room room = (Room) list.get(1);
                    List<FileInfo> files = new ArrayList<>();
                    if(!isEmpty)      
                        files = (List<FileInfo>) list.get(2);                                                       
                    FileListView view = (FileListView) fileHandler.listFileListViewForRoom.get(room.getId());
                    if(view != null){                            
                        view.setFiles(files);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break; 
            //T???i 1 file t??? server
            case(FILE):
                try {
                    FileInfo file = (FileInfo) data.getObject();
                    if(file != null && fileHandler.createFile(file)){  
                        if(file.getReceiver() != null){
                            Account sender = file.getSender();
                            FileListView fileListView = (FileListView) fileHandler.listFileListView.get(sender.getId());
                            if(fileListView != null){
                                fileListView.showMessage("Tai file thanh cong");
                            }
                        } else if(file.getRoom() != null){
                            Room room = file.getRoom();
                            FileListView fileListView = (FileListView) fileHandler.listFileListViewForRoom.get(room.getId());
                            if(fileListView != null){                      
                                fileListView.showMessage("Tai file thanh cong");
                            } 
                        }  
                    }       
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break; 
                
            //C??C M?? L???NH X??? L?? CHAT NH??M
            //Nh???n tin nh???n t??? ph??ng chat chung c???a server
            case(MESSAGE_GLOBAL):
                try {
                    Message msg = (Message) data.getObject();
                    if(chatGlobal != null)
                        chatGlobal.addMess(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Nh???n danh s??ch ph??ng chat m?? client c?? m???t trong ????
            case(LIST_ROOM):
                try {
                    roomList =(List<Room>) data.getObject();
                    viewRoomList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //C???p nh???t danh s??ch ph??ng khi c??c s??? ki???n th??m, ???? th??nh vi??n ho???c c?? th??nh vi??n r???i kh???i ph??ng chat
            case(UPDATE_ROOM):
                try {       
                    List<Object> list = data.getListObject();
                    String type = (String) list.get(0);
                    Account member = (Account) list.get(1);
                    Room room = (Room) list.get(2);
                    roomList =(List<Room>) list.get(3);
                    if(type.equals("MEMBER_ADDED")){ //S??? ki??n th??nh vi??n ???????c th??m v??o ph??ng
                        if(member.getId() == account.getId()){
                            mainMenu.alert("B???n ???? ???????c th??m v??o ph??ng " + room.getName(), "Th??ng b??o");
                        }

                        ChatRoomView view = (ChatRoomView) listChatRoomView.get(room.getId());
                        viewRoomList();
                        if(view != null){
                            view.refreshRoom(room);                    
                        }
                    } else { //S??? ki???n c?? th??nh vi??n r???i kh???i ph??ng ho???c b??? ???? kh???i ph??ng
                        if(member.getId() == account.getId() && type.equals("MEMBER_KICKED")){ //N???u client l?? th??nh vi??n b??? ????
                            mainMenu.alert("B???n ???? b??? ???? kh???i ph??ng chat " + room.getName(), "Th??ng b??o");
                        }
                        if(member.getId() == account.getId() && type.equals("MEMBER_LEFT")){ //N???u client l?? th??nh vi??n r???i kh???i ph??ng
                            mainMenu.alert("B???n ???? r???i kh???i ph??ng chat " + room.getName(), "Th??ng b??o");
                        }
                        ChatRoomView view = (ChatRoomView) listChatRoomView.get(room.getId());
                        viewRoomList();
                        if(view != null && member.getId() == account.getId()){
                            view.dispose();
                            listChatView.remove(room.getId());
                        } else if(view != null){
                            view.refreshRoom(room);
                        }
                    }  
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Nh???n tin nh???n trong ph??ng chat
            case(MESSAGE_ROOM):
                try {
                    Message msg = (Message) data.getObject();
                    Room room = msg.getRoom();                   
                    ChatRoomView view = (ChatRoomView) listChatRoomView.get(room.getId());
                    if(view != null)
                        view.addMess(msg);                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break; 
            //Nh???n chatlog c???a ph??ng
            case(CHAT_LOG_ROOM):
                try {
                    List<Object> list = data.getListObject();
                    String str = (String) list.get(0);       
                    if(str.equals("chatlog")){
                        ChatLog log = (ChatLog) list.get(1);
                        Room r = (Room) list.get(2);                        
                        ChatRoomView view = (ChatRoomView) listChatRoomView.get(r.getId());
                        if(view != null)
                            view.loadChatLog(log);
                    }                  
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break; 
            //Nh???n ???nh trong nh??m
            case (IMG):
                try {
                    List<Object> list = data.getListObject();
                    FileInfo img = (FileInfo) list.get(0);
                    if(img != null){
                        if(fileHandler.createFile(img)){
                            Message msg = (Message) list.get(1);
                            Room room = msg.getRoom();     
                            //Thread.sleep(2000);
                            ChatRoomView view = (ChatRoomView) listChatRoomView.get(room.getId());
                            if(view != null)
                                view.addMess(msg);  
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:                  
                break;
        }
    }
    
    //Hai ph????ng th???c ch??nh g???i nh???n c??c object
    public DataPacket receiveObj(){
        DataPacket data = null;
        try{
            ois = new ObjectInputStream(clientSocket.getInputStream());
            data = (DataPacket) ois.readObject();
            log("Server: " + data.getCode());
        } catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
    
    public void sendObj(DataPacket data) throws IOException{
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        log("Client: " + data.getCode());
        oos.writeObject(data);
        oos.flush();
    }
    
    public static void main(String[] args) {                
        try{
            Client controller = new Client();            
        } catch (Exception ex) {
            ex.printStackTrace();
        }            
    }
    //????ng nh???p, k??, xu???t
    public void login(String username, String password){
        try {
            Account acc = new Account();
            acc.setUsername(username);
            acc.setPassword(password);
            sendObj(new DataPacket(LOGIN, acc));
        } catch (Exception e) {
        }        
    }
    
    public void register(String username, String password){
        try {
            Account acc = new Account();
            acc.setUsername(username);
            acc.setPassword(password);
            sendObj(new DataPacket(REGISTER, acc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void logout(int input){
        if(input == 0){
            try {
                log("????ng xu???t");
                sendObj(new DataPacket(LOGOUT, null));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else{
            return;
        }
    }
    
    //X??? l?? y??u c???u k???t b???n
    public void friendRequestHandler(int a, AddFriendRequest req){
        try{
            if(a == 0){
                req.setStatus(1);
                sendObj(new DataPacket(SEND_FR_REQUEST_RESPONSE, req));   
            }
            else if(a == 1){
                req.setStatus(2);
                sendObj(new DataPacket(SEND_FR_REQUEST_RESPONSE, req));
            } else {
                return;
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendFriendRequest(String receiver, String message){
        try {
            for(Account acc:globalList){
                if(acc.getUsername().equals(receiver)){
                    AddFriendRequest req = new AddFriendRequest(account, acc, 0, message);
                    sendObj(new DataPacket(SEND_NEW_ADD_FR_REQUEST, req));
                    return;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Boolean checkIsFriend(String username){
        for(Account fr: frList){
            if(fr.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    
    public void showRequestView() {
        if(requestView == null)
            this.requestView = new RequestView(this, frRequestList);
        else requestView.toFront();
    }
    
    public void closeRequestView(){
        requestView = null;
    }
    
    //Ph??ng chat chung to??n server
    public void showGlobalChat(){
        if(chatGlobal == null)
            chatGlobal = new ChatRoomGlobal(this, account);
        else chatGlobal.toFront();
    }
       
    public void sendToGlobal(Message mess){
        try {
            sendObj(new DataPacket(SEND_CHAT_MESSAGE_GLOBAL, mess));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeChatGlobal(){
        chatGlobal = null;
    }
    
    //X??? l?? chat 1-1
    public void sendRequestChat(String receiver){
        try {
            for(Account fr:frList){
                if(fr.getUsername().equals(receiver)){
                    sendObj(new DataPacket(SEND_CHAT_REQUEST, fr));
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendToFriend(Message mess){
        try {
            sendObj(new DataPacket(SEND_CHAT_MESSAGE, mess));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeChat(Account friend) throws IOException{
        listChatView.remove(friend.getId());
        sendObj(new DataPacket(SEND_CHAT_CLOSE, friend));
    }
    
    //X??? l?? danh s??ch b???n b??
    public void viewFriends(){   
//        for(Account acc:globalList){
//            System.out.println(acc);
//        }
        if(globalList.isEmpty()){
            mainMenu.setFrOnlineList(null);
            return;
        }
        else {
            List<Account> frOnline = new ArrayList<>();
            for(Account global:globalList){
                for(Account fr:frList){
                    if(fr.getUsername().equals(global.getUsername())){
                        frOnline.add(fr);
                    }
                }
            }                
            //mainMenu.setFrOfflineList(frOffline);
            mainMenu.setFrOnlineList(frOnline);
        }
    }
    
    
    //X??? l?? ph??ng chat
    public void createRoom(String name){
        try {
            Room r = new Room();
            List<Account> listMem = new ArrayList<>();
            listMem.add(account);
            r.setName(name);
            r.setCreator(account);
            r.setMember(listMem);
            sendObj(new DataPacket(REQUEST_CREATE_ROOM, r));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void viewRoomList(){
        if(roomList!=null){     
            mainMenu.setRoomList(roomList);
        }
    }
    
    public void showChatRoom(String room_name){
        for(Room r: roomList){
            if(r.getName() == room_name){
                ChatRoomView tmp = (ChatRoomView) listChatRoomView.get(r.getId());
                if(tmp == null){
                    try {
                        ChatRoomView view = new ChatRoomView(this, account, r);
                        listChatRoomView.put(r.getId(), view);
                        sendObj(new DataPacket(REQUEST_ROOM_CHATLOG, r));
                        return;
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    tmp.toFront();
                }
                break;
            }
        }   
    }
    
    public void addMember(String name, Room room){
        try {
            for(Account fr:frList){
                if(fr.getUsername().equals(name)){
                    List<Object> list = new ArrayList<>();
                    list.add("ADD");
                    list.add(fr);
                    list.add(room);
                    sendObj(new DataPacket(SEND_UPDATE_ROOM, list));
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void kickMember(String name, Room room){
        try{
           for(Account acc:room.getMember()){
                if(acc.getUsername().equals(name)){
                    List<Object> list = new ArrayList<>();
                    list.add("KICK");
                    list.add(acc);
                    list.add(room);
                    sendObj(new DataPacket(SEND_UPDATE_ROOM, list));
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void leaveRoom(Room room){
        try{
            List<Object> list = new ArrayList<>();
            list.add("LEAVE");
            list.add(account);
            list.add(room);
            sendObj(new DataPacket(SEND_UPDATE_ROOM, list));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void sendToRoom(Message message){
        try {
            sendObj(new DataPacket(SEND_CHAT_MESSAGE_ROOM, message));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeChatRoom(Room room){
        for(Room r: roomList){
            if(r.getId()== room.getId()){
                listChatRoomView.remove(r.getId());
                break;
            }
        }   
    }
}
