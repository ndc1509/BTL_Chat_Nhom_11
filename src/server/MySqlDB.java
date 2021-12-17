/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.AddFriendRequest;
import model.ChatLog;
import model.FileInfo;
import model.Message;
import model.Room;

/**
 *
 * @author Cuong
 */
public class MySqlDB{
    static final String dbUrl = "jdbc:mysql://localhost:3306/chat";
    static final String dbUsername = "root";
    static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    static final String dbPassword = "";
    private Connection con = null;
    
    public MySqlDB(){
        
    }
        
    //Thiet lap trang thai sau khi dang nhap    
    public static void setOnline(Account acc) throws SQLException, ClassNotFoundException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "UPDATE account SET isOnline = 0 WHERE username = ?";
        if(acc.isOnline() == 1)
            query = "UPDATE account SET isOnline = 1 WHERE username = ?";
        
        PreparedStatement ps = con.prepareStatement(query);    
        ps.setString(1, acc.getUsername());
        ps.executeUpdate();
        
        ps.close();
        con.close();
    }
    //Lay password de check
    public static String getPassword(Account acc) throws SQLException, ClassNotFoundException{
        Connection con = null;
        String pass="";
        boolean isOnline = false;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "SELECT password, isOnline FROM account where username = ?;";
        
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, acc.getUsername());
        ResultSet resultSet = ps.executeQuery();
        
        while(resultSet.next()){
            pass = resultSet.getString(1);   
            if(resultSet.getInt(2) == 1)
                isOnline = true;
        }
        resultSet.close();
        ps.close();
        con.close();
        if(!isOnline)
            return pass;
        else return null;
    }
    //Lay account
    public static Account getAccount(Account a) throws SQLException, ClassNotFoundException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "SELECT * FROM account where username = ?;";
        
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, a.getUsername());
        ResultSet resultSet = ps.executeQuery();
        Account acc = null;
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            int isOnline = resultSet.getInt(4);
            acc = new Account();
            acc.setId(id);
            acc.setUsername(name);
        }
        resultSet.close();
        ps.close();
        con.close();
        
        return acc;
    }
    //Dang ki
    public static Boolean addAccount(Account acc) throws SQLException, ClassNotFoundException{
        Connection con = null;
        
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            String query = "INSERT INTO account (username, password, isOnline) VALUES (?, ?, 0)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            int res = ps.executeUpdate();
            ps.close();
            con.close();

            if(res == 0) 
                return false; 
            else 
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    //Lay danh sach online Global
    public static List<Account> getOnlineGlobal(Account account) throws ClassNotFoundException, SQLException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        List<Account> list = new ArrayList<>();
        String query = "Select id,username from account "
                + "where id != ? "
                + "and isOnline = 1";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, account.getId());
        
        ResultSet rs = statement.executeQuery();
        while(rs.next()){   
            Account acc = new Account();
            acc.setId(rs.getInt("id"));
            acc.setUsername(rs.getString("username"));
            list.add(acc);
        }
        return list;
    }
    
    
    //Lay danh sach ban be
    public static List<Account> getFriends(Account account) throws SQLException, ClassNotFoundException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "select id,username from account where id in (select user2 from friendship where user1 = ?)";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, account.getId());
        
        ResultSet result =  statement.executeQuery();

        List<Account> frArr = new ArrayList<>();
 
        while(result.next()) {  
            Account acc = new Account();
            acc.setId(result.getInt("id"));
            acc.setUsername(result.getString("username"));
            frArr.add(acc);
        }

        statement.close();
        con.close();
        return frArr;
    }
    
    //Them ban 
    public static Boolean addFriend(AddFriendRequest request) {
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);  
            
            String query = "INSERT INTO frendship (user1, user2) VALUES (?,?), (?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, request.getSender().getId());
            statement.setInt(2, request.getReceiver().getId());
            statement.setInt(3, request.getReceiver().getId());
            statement.setInt(4, request.getSender().getId());
            statement.executeUpdate();

            statement.close();
            con.close();

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }		
    }
    
    public static Boolean sendFriendRequest(AddFriendRequest request){
        Connection con = null;
        boolean ok = false;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            String query = "INSERT INTO friendrequest (fromuser, touser, status, message) VALUES (?,?,0,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, request.getSender().getId());
            statement.setInt(2, request.getReceiver().getId());
            if(request.getMessage() != null)
                statement.setString(3, request.getMessage());
            else statement.setString(3, null);
            if(statement.executeUpdate() == 0)
                ok = false;
            else ok = true;
        } catch(Exception e) {
            e.printStackTrace();
            ok = false;
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return ok;
    }
    
    public static List<AddFriendRequest> getFriendRequest(Account account) throws Exception{
        Connection con = null;

        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        String query = "select sender.id as senderid, sender.username as sendername, receiver.id as receiveid, receiver.username as receivername, message from friendrequest req " +
                            "join account sender on sender.id = req.fromuser " +
                            "join account receiver on receiver.id = req.touser where req.status = 0 and req.touser = ?";
        PreparedStatement statement = con.prepareStatement(query);

        statement.setInt(1, account.getId());
        ResultSet rs = statement.executeQuery();
        List<AddFriendRequest> list = new ArrayList<>();
        while(rs.next()){
            AddFriendRequest req = new AddFriendRequest();
            req.setSender(new Account(rs.getInt("senderid"), rs.getString("sendername")));
            req.setReceiver(new Account(rs.getInt("receiverid"), rs.getString("receivername")));
            req.setStatus(0);
            req.setMessage(rs.getString("message"));
            list.add(req);
        }
        return list;
    }
    
    public static Boolean acceptFriendRequest(AddFriendRequest request){
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            String query = "Update friendrequest set status = 1 "
                    + "where fromuser = ? and touser = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, request.getSender().getId());
            statement.setInt(2, request.getReceiver().getId());
            statement.executeUpdate();
            
            if(addFriend(request))
                return true;
            else 
                return false;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }		
    }
    
    public static Boolean declineFriendRequest(AddFriendRequest request){
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            String query = "Update friendrequest set status = 2 "
                    + "where fromuser = ? and touser = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, request.getSender().getId());
            statement.setInt(2, request.getReceiver().getId());
            statement.executeUpdate();
            statement.close();
            con.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }		
    }
    
    //Them file moi
    public static void addFile(FileInfo file){
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            String query = "INSERT INTO filesharing (file_name, sender, receiver, room) VALUES (?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, file.getName());
            statement.setInt(2, file.getSender().getId());
            if(file.getReceiver() != null)
                statement.setInt(3, file.getReceiver().getId());
            else statement.setString(3, null);
            
            if(file.getRoom() != null)
                statement.setInt(4, file.getRoom().getId());
            else statement.setString(4, null);
            
            statement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    //Lay file do user1 gui cho id2
    public static List<FileInfo> getFile(Account acc1, Account acc2) throws Exception{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        List<FileInfo> list = new ArrayList<>();
        String query = "Select file_name from filesharing where sender = ? and receiver = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, acc1.getId());
        statement.setInt(2, acc2.getId());
        
        ResultSet rs = statement.executeQuery();
        while(rs.next()){     
            FileInfo file = new FileInfo();
            file.setName(rs.getString(1));
            file.setSender(acc1);
            file.setReceiver(acc2);
            list.add(file);
        }
        return list;
    }
    
    public static List<FileInfo> getFile(Room room) throws Exception{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        List<FileInfo> list = new ArrayList<>();
        String query = "Select filesharing.file_name, acc.id as senderid, acc.username as sendername from filesharing "
                + "join account acc on acc.id = filesharing.sender "
                + "where room = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, room.getId());
        
        ResultSet rs = statement.executeQuery();
        while(rs.next()){     
            FileInfo file = new FileInfo();
            file.setName(rs.getString(1));
//            Account sender = new Account(rs.getInt(2), rs.getString(3));
//            file.setSender(sender);
            file.setRoom(room);
            list.add(file);
        }
        return list;
    }
    
    // lay chat log 1-1 giua 2 client
    public static ChatLog getChatLog(Account acc1, Account acc2) throws ClassNotFoundException, SQLException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "Select chatlog.id_chat_log ,sender.id as senderid, sender.username as sender,receiver.id as receiverid, receiver.username as receiver, chatlog.mess, chatlog.date_time, chatlog.type from chatlog " +
                        "join account sender on sender.id = chatlog.sender " +
                        "join account receiver on receiver.id = chatlog.receiver " +
                        "where (sender = ? AND receiver= ?) OR (sender = ? AND receiver= ?) " +
                        "Order by chatlog.id_chat_log;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, acc1.getId());
        statement.setInt(2, acc2.getId());
        statement.setInt(3, acc2.getId());
        statement.setInt(4, acc1.getId());
        
        ChatLog chatlog = new ChatLog();
        
        ResultSet rs = statement.executeQuery();
        while(rs.next()){       
            Account sender = new Account(rs.getInt("senderid"), rs.getString("sender"));
            Account receiver = new Account(rs.getInt("receiverid"), rs.getString("receiver"));
            Message msg = new Message();
            msg.setSender(sender);
            msg.setReceiver(receiver);
            msg.setContent(rs.getString("mess"));
            if(rs.getString("type").equals("message"))
                msg.setType(Message.Type.MESSAGE);
            else if(rs.getString("type").equals("file"))
                msg.setType(Message.Type.FILE);
            else if(rs.getString("type").equals("emoji"))
                msg.setType(Message.Type.EMOJI);
            else if(rs.getString("type").equals("img"))
                msg.setType(Message.Type.IMG);
            chatlog.addMes(msg);
        }
        return chatlog;
    }
    
    public static ChatLog getChatLog(Room r) throws ClassNotFoundException, SQLException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "Select chatlog.id_chat_log ,sender.id as senderid, sender.username as sender, chatlog.mess, chatlog.date_time, chatlog.room, chatlog.type from chatlog " +
                        "join account sender on sender.id = chatlog.sender " +
                        "where chatlog.room = ? " +
                        "Order by chatlog.id_chat_log;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, r.getId());
        
        ChatLog chatlog = new ChatLog();
        
        ResultSet rs = statement.executeQuery();
        while(rs.next()){       
            Account sender = new Account(rs.getInt("senderid"), rs.getString("sender"));
            Message msg = new Message();
            msg.setSender(sender);
            msg.setRoom(r);
            msg.setContent(rs.getString("mess"));
            if(rs.getString("type").equals("message"))
                msg.setType(Message.Type.MESSAGE);
            else if(rs.getString("type").equals("file"))
                msg.setType(Message.Type.FILE);
            else if(rs.getString("type").equals("emoji"))
                msg.setType(Message.Type.EMOJI);
            else if(rs.getString("type").equals("img"))
                msg.setType(Message.Type.IMG);
            chatlog.addMes(msg);
        }
        return chatlog;
    }
    
    // lưu tin nhắn
    public static void saveMes(Message mess) throws ClassNotFoundException, SQLException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "INSERT INTO chatlog(sender, receiver, mess, date_time, room, type) values (?, ?, ?, ?, ?, ?);";
         PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, mess.getSender().getId());
        if(mess.getReceiver()!= null)
            statement.setInt(2, mess.getReceiver().getId());
        else statement.setString(2, null);
        statement.setString(3, mess.getContent());
        statement.setString(4, mess.getDateTimeToString());
        if(mess.getRoom() != null)
            statement.setInt(5, mess.getRoom().getId());
        else statement.setString(5, null);
        
        if(mess.getType().equals(Message.Type.MESSAGE))
            statement.setString(6, "message");
        else if(mess.getType().equals(Message.Type.FILE))
            statement.setString(6, "file");
        else if(mess.getType().equals(Message.Type.EMOJI))
            statement.setString(6, "emoji");
        else if(mess.getType().equals(Message.Type.IMG))
            statement.setString(6, "img");
        statement.executeUpdate();
        statement.close();
    }
    
    //Tao phong chat
    public static Boolean createRoom(Room r){
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            String query = "INSERT INTO room (name, creator) values (?,?)";
            PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, r.getName());
            statement.setInt(2, r.getCreator().getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            int room_id = 0;
            while(rs.next()){              
                room_id = rs.getInt(1);
            }
            statement.close();
            
            query = "INSERT INTO room_member (r_id, member) values (?,?)";
            statement = con.prepareStatement(query);
            statement.setInt(1, room_id);
            statement.setInt(2, r.getCreator().getId());
            statement.execute();
            statement.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    //Them nguoi vao phong chat
    public static Boolean insertMember(Room r, Account acc){
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            String query = "INSERT INTO room_member (r_id, member) values (?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, r.getId());
            statement.setInt(2, acc.getId());
            statement.execute();
            statement.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    //Xóa người khỏi phòng chat
    public static Boolean removeMember(Room r, Account acc){
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            String query = "DELETE FROM room_member WHERE r_id = ? and member = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, r.getId());
            statement.setInt(2, acc.getId());
            statement.execute();
            statement.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public static List<Room> getRoomList(Account account){
        Connection con = null;
        try {   
            List<Room> list = new ArrayList<>(); 
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            String query = "select room_id, name from room_member "
                    + "left join room on room_member.r_id = room.room_id  where `member` = ? "
                    + "union "
                    + "select room_id, name from room_member "
                    + "right join room on room_member.r_id = room.room_id where `member` = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, account.getId());
            statement.setInt(2, account.getId());
            ResultSet rs = statement.executeQuery();
            while(rs.next()){   
                Room room = new Room();
                int room_id = rs.getInt("room_id");
                String name = rs.getString("name");
            
                room.setId(room_id);
                room.setName(name);
                
                String memberQuery = "select id, username from room_member "
                    + "left join account on room_member.member = account.id  where r_id = ? and `member` != ? "
                    + "union "
                    + "select id, username from room_member "
                    + "right join account on room_member.member = account.id where r_id = ? and `member` != ? ";
                PreparedStatement ps2 = con.prepareStatement(memberQuery);
                ps2.setInt(1, room_id);
                ps2.setInt(2, account.getId());
                ps2.setInt(3, room_id);
                ps2.setInt(4, account.getId());
                ResultSet rs2 = ps2.executeQuery();
                List<Account> memList = new ArrayList<>();
                while(rs2.next()){
                    Account acc = new Account();
                    acc.setId(rs2.getInt("id"));
                    acc.setUsername(rs2.getString("username"));
                    memList.add(acc);
                }
                ps2.close();
                room.setMember(memList);
                list.add(room);
            }
            return list;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public static Room getRoomDetails(Room room){
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    
            String memberQuery = "select id, username from room_member "
                + "left join account on room_member.member = account.id  where r_id = ? "
                + "union "
                + "select id, username from room_member "
                + "right join account on room_member.member = account.id where r_id = ? ";
            PreparedStatement ps = con.prepareStatement(memberQuery);
            ps.setInt(1, room.getId());
            ps.setInt(2, room.getId());
            ResultSet rs = ps.executeQuery();
            List<Account> memList = new ArrayList<>();
            while(rs.next()){
                Account acc = new Account();
                acc.setId(rs.getInt("id"));
                acc.setUsername(rs.getString("username"));
                memList.add(acc);
            }
            ps.close();
            room.setMember(memList);
            return room;       
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
