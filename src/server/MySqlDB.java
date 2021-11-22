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
import model.ChatLog;

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
    public static void setOnline(String username, Boolean isOnline) throws SQLException, ClassNotFoundException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "UPDATE account SET isOnline = 0 WHERE username = ?";
        if(isOnline)
            query = "UPDATE account SET isOnline = 1 WHERE username = ?";
        
        PreparedStatement ps = con.prepareStatement(query);    
        ps.setString(1, username);
        ps.executeUpdate();
        
        ps.close();
        con.close();
    }
    //Lay password de check
    public static String getPassword(String username) throws SQLException, ClassNotFoundException{
        Connection con = null;
        String pass="";
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "SELECT password FROM account where username = ?;";
        
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, username);
        ResultSet resultSet = ps.executeQuery();
        Account acc = null;
        while(resultSet.next()){
            pass = resultSet.getString(1);            
        }
        resultSet.close();
        ps.close();
        con.close();
        
        return pass;
    }
    //Lay account
    public static Account getAccount(String username) throws SQLException, ClassNotFoundException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "SELECT * FROM account where username = ?;";
        
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, username);
        ResultSet resultSet = ps.executeQuery();
        Account acc = null;
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            int isOnline = resultSet.getInt(4);
            acc = new Account(id, name, password, isOnline);
        }
        resultSet.close();
        ps.close();
        con.close();
        
        return acc;
    }
    //Dang ki
    public static Boolean addAccount(String username, String password) throws SQLException, ClassNotFoundException{
        Connection con = null;
        
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            String query = "INSERT INTO account (username, password, isOnline) VALUES (?, ?, 0)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
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
//    //K hieu
//    public static String[] getAccount(String username) throws SQLException, ClassNotFoundException{
//        Connection con = null;
//        Class.forName(jdbcDriver);
//        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
//        
//        String query = "SELECT username FROM account , friendship"
//                + "WHERE username != 'admin' "
//                + "AND username != ? "
//                + "AND isOnline = 1 "
//                + "AND username = user1 "
//                + "AND user2 = ?";
//        PreparedStatement ps = con.prepareStatement(query);
//        ps.setString(1, username);
//        ps.setString(2, username);  
//        
//        ResultSet result =  ps.executeQuery();
//        
//        int count = 0;
//        result.last();
//        count = result.getRow();
//        result.beforeFirst();
//
//        String[] usersArr = new String[count];
//        int i = 0;
//        while(result.next()) {			
//            usersArr[i] = result.getString(1);
//            i++;
//        }
//
//        ps.close();
//        con.close();
//
//        return usersArr;
//    }
    //Lay danh sach ban be
    public static List<String> getFriends(int id) throws SQLException, ClassNotFoundException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "select username from account where id in (select user2 from friendship where user1 = ?)";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, id);
        
        ResultSet result =  statement.executeQuery();

        List<String> usersArr = new ArrayList<>();
 
        while(result.next()) {                
            usersArr.add(result.getString(1));
        }

        statement.close();
        con.close();
        return usersArr;
    }
    
    //Them ban 
    public static Boolean addFriend(int id1, String user2) {
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            Account friendAcc = getAccount(user2);
            int id2 = friendAcc.getId();
            
            String query = "INSERT INTO frendship (user1, user2) VALUES (?,?), (?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id1);
            statement.setInt(2, id2);
            statement.setInt(3, id2);
            statement.setInt(4, id1);

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
    
    public static Boolean sendFriendRequest(int id1, String user2){
        Connection con = null;
        boolean ok = false;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            Account acc = getAccount(user2);
            int id2 = acc.getId();
            String query = "INSERT INTO friendrequest (fromuser, touser, status) VALUES (?,?,0)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id1);
            statement.setInt(2, id2);
            int i = statement.executeUpdate();
            if(i == 0)
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
    
     public static ArrayList<String> getFriendRequest(int id2) throws Exception{
        Connection con = null;

        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        String query = "select username from account where id in (select fromuser from friendrequest where touser = ? and status = 0)";
        PreparedStatement statement = con.prepareStatement(query);

        statement.setInt(1, id2);
        ResultSet rs = statement.executeQuery();
        ArrayList<String> arr = new ArrayList<>();
        while(rs.next()){
            arr.add(rs.getString(1));
        }
        return arr;

    }
    
    public static Boolean acceptFriendRequest(String user1, int id2){
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            Account acc = getAccount(user1);
            int id1 = acc.getId();
            String query = "Update friendrequest set status = 1 "
                    + "where fromuser = ? and touser = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id1);
            statement.setInt(2, id2);
            statement.executeUpdate();
            
            String query2 = "Insert into friendship (user1, user2) values (?,?) , (?,?)";
            statement = con.prepareStatement(query2);
            statement.setInt(1, id1);
            statement.setInt(2, id2);
            statement.setInt(3, id2);
            statement.setInt(4, id1);
            statement.executeUpdate();
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
    
    public static Boolean declineFriendRequest(String user1, int id2){
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
            Account acc = getAccount(user1);
            int id1 = acc.getId();
            
            String query = "DELETE FROM friendrequest "
                    + "where fromuser = ? and touser = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id1);
            statement.setInt(2, id2);
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
    
    //Lay danh sach online Global
    public static ArrayList<String> getOnlineGlobal(String username) throws ClassNotFoundException, SQLException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        ArrayList<String> list = new ArrayList<>();
        String query = "Select username from account "
                + "where username != ? "
                + "and isOnline = 1";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, username);
        
        ResultSet rs = statement.executeQuery();
        while(rs.next()){            
            list.add(rs.getString(1));
        }
        return list;
    }
    
    //Them file moi
    public static void addFile(String filename, int id1, String user2){
        Connection con = null;
        try {   
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            Account acc = getAccount(user2);
            int id2 = acc.getId();
            String query = "INSERT INTO filesharing (file_name, sender, receiver) VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, filename);
            statement.setInt(2, id1);
            statement.setInt(3, id2);
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
    public static List<String> getFile(String user1, int id2) throws Exception{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        int id1 = getAccount(user1).getId();
        ArrayList<String> list = new ArrayList<>();
        String query = "Select file_name from filesharing where sender = ? and receiver = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, id1);
        statement.setInt(2, id2);
        
        ResultSet rs = statement.executeQuery();
        while(rs.next()){            
            list.add(rs.getString(1));
        }
        return list;
    }
    
    // lay chat log 1-1 giua 2 client
    public static ChatLog getChatLog(String user1, String user2) throws ClassNotFoundException, SQLException{
        Connection con = null;
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        
        String query = "Select * from chatlog" +
                        " where (sender = ? AND receiver= ?) OR (sender = ? AND receiver= ?)" +
                        " Order by id_chat_log;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, user1);
        statement.setString(2, user2);
        statement.setString(3, user2);
        statement.setString(4, user1);
        
        ChatLog chatlog = new ChatLog();
        
        ResultSet rs = statement.executeQuery();
        while(rs.next()){            
            chatlog.addMes(rs.getString(2) + " : " + rs.getString(4));
        }
        return chatlog;
    }
}




































