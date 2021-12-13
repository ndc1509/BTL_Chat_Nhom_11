/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    private static final int SEND_FILE = 12;
    private static final int REQUEST_FILE_LIST = 13;
    private static final int REQUEST_FILE = 14;
    private static final int REQUEST_CREATE_ROOM = 15;
    private static final int SEND_UPDATE_ROOM = 16;
    private static final int SEND_CHAT_MESSAGE_ROOM = 17;
    private static final int REQUEST_ROOM_CHATLOG = 18;
    private Socket clientSocket;
    private LoginView loginView;
    private MainMenu mainMenu;
    private ChatRoomGlobal chatGlobal;
    
    private RequestView requestView;
    private Account account;
    
    private HashMap listChatView = new HashMap();
    private HashMap listChatRoomView = new HashMap();
    private HashMap listFileListView = new HashMap();
    
    private Thread thread;
    
    private List<Account>  globalList = new ArrayList<>();
    private List<AddFriendRequest> frRequestList = new ArrayList<>();
    private List<Account> frList = new ArrayList<>();
    private List<Room> myRoom = new ArrayList<>();
    private List<FileInfo> fileList = new ArrayList<>();
    
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    private String destDIR = "C:\\Users\\Cuong\\Documents\\NetBeansProjects\\Clone chat\\Chat_v1.1\\src\\server\\FileStorage\\";
    private String saveDIR = "C:\\Users\\Cuong\\Documents\\NetBeansProjects\\Clone chat\\Chat_v1.1\\src\\client\\FileStorage\\";
    
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
                log("Đăng xuất");
                sendObj(new DataPacket(LOGOUT, null));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
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
                        log("Kết nối đến " + hostname + " port " + port);
                        clientSocket = new Socket(hostname, port);
                        if(clientSocket.isConnected())
                            log("Kết nối đến " + clientSocket.getRemoteSocketAddress() + " đã được thiết lập"); 
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Login khon thanh cong
            case (LOGIN_FAIL):
                try {
                    loginView.showErrorMessage("Sai username hoặc password","Lỗi");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;   

            //Tao tai khoan thanh cong
            case (REGISTER_SUCCESS):
                try {                         
                    loginView.showMessage("Đăng ký thành công");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Tao tai khoan that bai
            case (REGISTER_FAIL):
                try {                         
                    loginView.showErrorMessage("Đăng ký không thành công", "Lỗi");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Xac nhan logout thanh cong
            case (LOGOUT_SUCCESS):
                try {                        
                    log("Đóng kết nối thành công");
                    clientSocket.close();
                    mainMenu.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                    log("Đóng kết nối không thành công");
                }

            //Danh sach online toan server
            case (LIST_GLOBAL):
                try{
                    globalList = (List<Account>) data.getObject();
                    if(globalList != null)
                        mainMenu.setGlobalList(globalList);
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;
            //1 user chuyen sang trang thai online
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
            //1 user chuyen sang trang thai offline
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
            //Danh sach ban be
            case (LIST_FRIEND):
                try{
                    frList = (List<Account>) data.getObject();
                    if(frList!=null)
                        viewFriends();
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;

            //Lay tat ca yeu cau ket ban
            case(LIST_FRIEND_REQUESTS):
                try {
                    frRequestList = (List<AddFriendRequest>) data.getObject();
                    if(requestView != null)
                        requestView.setRequest(frRequestList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            // Chap nhan tro chuyen
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
            // Tu choi tro chuyen
            case(CHAT_REQUEST_DECLINED):
                try {
                    Account receiver = (Account) data.getObject();
                    mainMenu.alert(receiver.getUsername() + " đã từ chối trò chuyện!", "Thông báo");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            // Nhan yeu cau chat
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
            //Chat log 1-1
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
            //Nhan tin nhan 1-1
            case(MESSAGE):
                try {
                    Message msg = (Message) data.getObject();
                    Account sender = msg.getSender();
                    ChatView tmp = (ChatView) listChatView.get(sender.getId());
                    tmp.addMess(msg);                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break; 
            //Dong chat 1-1
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
            //nhan danh sach file dc gui boi 1 nguoi ban
            case(LIST_FILE):
                try {
                    List<Object> list = data.getListObject();
                    Boolean ok = (Boolean) list.get(0);
                    if(ok == true){
                        Account sender = (Account) list.get(1);
                        List<FileInfo> files = (List<FileInfo>) list.get(2);
                        if(listFileListView.get(sender.getId()) != null){
                            FileListView view = (FileListView) listFileListView.get(sender.getId());
                            view.setFiles(files);
                        } else {
                            showFileList(files, sender);
                        }                        
                    } else {
                        Account sender = (Account) list.get(1);
                        showFileList(null, sender);
                    }   
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break; 
            //Tai file tu server
            case(FILE):
                try {
                    FileInfo file = (FileInfo) data.getObject();
                    FileListView fileListView = (FileListView) listFileListView.get(file.getSender().getId());
                    if(fileListView != null){
                        if(file != null && createFile(file)){                        
                            fileListView.showMessage("Tai file thanh cong");
                        } else{
                            fileListView.showMessage("Tai file that bai");
                        }
                    }              
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;     
            //nhan tin nhan global
            case(MESSAGE_GLOBAL):
                try {
                    Message msg = (Message) data.getObject();
                    if(chatGlobal != null)
                        chatGlobal.addMess(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Lay danh sach phong
            case(LIST_ROOM):
                try {
                    myRoom =(List<Room>) data.getObject();
                    viewRoomList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Refresh room list
            case(UPDATE_ROOM):
                try {       
                    List<Object> list = data.getListObject();
                    String type = (String) list.get(0);
                    Account member = (Account) list.get(1);
                    Room room = (Room) list.get(2);
                    myRoom =(List<Room>) list.get(3);
                    if(type.equals("MEMBER_ADDED")){
                        if(member.getId() == account.getId()){
                            mainMenu.alert("Bạn đã được thêm vào phòng " + room.getName(), "Thông báo");
                        }

                        ChatRoomView view = (ChatRoomView) listChatRoomView.get(room.getId());
                        viewRoomList();
                        if(view != null){
                            view.refreshRoom(room);                    
                        }
                    } else {
                        if(member.getId() == account.getId() && type.equals("MEMBER_KICKED")){
                            mainMenu.alert("Bạn đã bị đá khỏi phòng chat " + room.getName(), "Thông báo");
                        }
                        if(member.getId() == account.getId() && type.equals("MEMBER_LEFT")){
                            mainMenu.alert("Bạn đã rời khỏi phòng chat " + room.getName(), "Thông báo");
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
            //nhan tin nhan phong chat
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
            //Chat log cua phong
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
            default:                  
                break;
        }
    }
    
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
    
    public void showGlobalChat(){
        if(chatGlobal == null)
            chatGlobal = new ChatRoomGlobal(this, account);
        else chatGlobal.toFront();
    }
       
    public void closeChatGlobal(){
        chatGlobal = null;
    }
    
    public Boolean checkIsFriend(String username){
        for(Account fr: frList){
            if(fr.getUsername().equals(username)){
                return false;
            }
        }
        return true;
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
    
    public void sendRequestChat(String receiver){
        try {
            for(Account fr:frList){
                if(fr.getUsername().equals(receiver)){
                    sendObj(new DataPacket(SEND_CHAT_REQUEST, fr));
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
    
    public void sendToGlobal(Message mess){
        try {
            sendObj(new DataPacket(SEND_CHAT_MESSAGE_GLOBAL, mess));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeChat(Account friend) throws IOException{
        listChatView.remove(friend.getId());
        sendObj(new DataPacket(SEND_CHAT_CLOSE, friend));
    }

    public void showRequestView() {
        if(requestView == null)
            this.requestView = new RequestView(this, frRequestList);
        else requestView.toFront();
    }
    
    public void closeRequestView(){
        requestView = null;
    }

    public void viewFriends(){   
        for(Account acc:globalList){
            System.out.println(acc);
        }
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
    
     //Lam viec voi file
    public void sendFile(String source, Account receiver){     
        try{           
            FileInfo file = getFileInfo(source, destDIR, receiver);
            sendObj(new DataPacket(SEND_FILE, file));
            log("Gửi file thành công");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
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
    
   
    private boolean createFile(FileInfo fileInfo){
        BufferedOutputStream bos = null;

        try {
            if(fileInfo != null){
                File fileReceive = new File(saveDIR + fileInfo.getName());
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
    
    //Yeu cau danh sach file ban be gui
    public void requestFileList(Account sender){
        try {
            FileListView view = (FileListView) listFileListView.get(sender.getId());
            if(view == null)
                sendObj(new DataPacket(REQUEST_FILE_LIST, sender));
            else 
                view.toFront();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Show danh sach file
    public void showFileList(List<FileInfo> list, Account sender){
        FileListView view = (FileListView) listFileListView.get(sender.getId());
        if(view == null)
            listFileListView.put(sender.getId(), new FileListView(this, list, sender));
        else 
            view.toFront();
    }
    
    public void closeFileList(Account sender){
        listFileListView.remove(sender.getId());
    }
    
    //Tai file
    public void download(FileInfo file){                      
        try {
            sendObj(new DataPacket(REQUEST_FILE, file));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Tao phong chat
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
        if(myRoom!=null){     
            mainMenu.setRoomList(myRoom);
        }
    }
    
    public void showChatRoom(String room_name){
        for(Room r: myRoom){
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
        for(Room r: myRoom){
            if(r.getId()== room.getId()){
                listChatRoomView.remove(r.getId());
            }
        }   
    }
}
