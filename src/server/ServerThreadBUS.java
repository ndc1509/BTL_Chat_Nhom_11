/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    
    public int getLength(){
        return listServerThreads.size();
    }
    
    //Gửi cho toàn bộ client
    public void broadcast(String message){
        for(ServerThread serverThread : Server.getServerThreadBUS().getListServerThreads()){
            try {
                serverThread.write(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    //Gửi cho 1 client
    public void unicast(int id, String message){
        for(ServerThread serverThread : Server.getServerThreadBUS().getListServerThreads()){
            if(serverThread.getClientNumber() == id){
                try {
                    serverThread.write(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    //Gửi cho 1 danh sách client (có thể dùng cho group chat)
    public void multicast(List<Integer> ids, String message){
        for(ServerThread serverThread : Server.getServerThreadBUS().getListServerThreads()){
            for(int i=0; i < ids.size(); i++){
                if(serverThread.getClientNumber() == i){
                    try {
                        serverThread.write(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
        }
    }
    //gửi cho toàn bộ client trừ cái id, id có thể là id người gửi ??
//    public void boardCast(int id, String message){  //gửi cho toàn bộ client trừ cái id
//        for(ThreadServer serverThread : Server.serverThreadBus.getListServerThreads()){
//            if (serverThread.getClientNumber() == id) {
//                continue;
//            } else {
//                try {
//                    serverThread.write(message);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }
    
    public void remove(int id){
        for(int i=0; i<Server.getServerThreadBUS().getLength(); i++){
            if(Server.getServerThreadBUS().getListServerThreads().get(i).getClientNumber() == id){
                Server.getServerThreadBUS().getListServerThreads().remove(id);
            }
        }
    } 
}
