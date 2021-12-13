/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.DataPacket;

/**
 *
 * @author Cuong
 */
public class ServerThreadBUS {
    private List<ServerThread> listServerThreads;
    
    public List<ServerThread> getListServerThreads(){
        return listServerThreads;
    }
    
    public ServerThreadBUS(){
        listServerThreads = new ArrayList<>();
    }
    
    public void add(ServerThread serverThread){
        listServerThreads.add(serverThread);
    }
    
    public void remove(ServerThread serverThread){
        listServerThreads.remove(serverThread);
    }
    
    public int getLength(){
        return listServerThreads.size();
    }
    
    //Gửi cho toàn bộ client
    public void broadcast(DataPacket data){
        for(ServerThread serverThread : Server.getServerThreadBUS().getListServerThreads()){
            try {
                serverThread.writeObj(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    //Gửi cho 1 client 1 Obj
    public void unicast(Account acc, DataPacket data){
        for(ServerThread serverThread : Server.getServerThreadBUS().getListServerThreads()){
            if(serverThread.getAccountID() == acc.getId()){
                try {
                    serverThread.writeObj(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    
    //Gửi cho 1 danh sách client (có thể dùng cho group chat)
    public void multicast(List<Account> list_acc, DataPacket data){
        for(ServerThread serverThread : Server.getServerThreadBUS().getListServerThreads()){
            for(Account acc: list_acc){
                if(serverThread.getAccountID() == acc.getId()){
                    try {
                        serverThread.writeObj(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
        }
    }
    
    public void boardcast(Account acc, DataPacket data){  //gửi cho toàn bộ client trừ cái id
        for(ServerThread serverThread : Server.getServerThreadBUS().getListServerThreads()){
            if (serverThread.getAccountID()== acc.getId()) {
                continue;
            } else {
                try {
                    serverThread.writeObj(data);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void remove(int id){
        for(int i=0; i<Server.getServerThreadBUS().getLength(); i++){
            if(Server.getServerThreadBUS().getListServerThreads().get(i).getClientNumber() == id){
                Server.getServerThreadBUS().getListServerThreads().remove(id);
            }
        }
    } 
}
