/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.control.Client;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Account;
import model.ChatLog;
import model.Message;

/**
 *
 * @author Admin
 */
public class ChatView extends javax.swing.JFrame{

    private Client controller;
    private Account sender;
    private Account receiver;
    private ChatLog chatlog = new ChatLog();
    
    public ChatView(Client controller, Account user1, Account user2) {
        initComponents();
        this.setVisible(true);
        
        this.sender = user1;
        this.receiver = user2;  
        
        this.setTitle(sender.getUsername() + " nhắn tin cho " + receiver.getUsername());
        this.controller = controller;    
    }
    
    public void loadChatLog(ChatLog chatlog){
        this.chatlog = chatlog;
        for(Message s : chatlog.getChatlog()){
            addMess(s);
        }
    }

    //them tin nhắn mới
    public void addMess(Message s){
        if(s.getSender().getId() == sender.getId()){
            if(s.getType().equals(Message.Type.MESSAGE))
                txtLog.append("Bạn: " + s.getContent() + "\n");
            else if(s.getType().equals(Message.Type.FILE))
                txtLog.append("Bạn: Đã gửi file " + s.getContent() + "\n");
        } else{
            if(s.getType().equals(Message.Type.MESSAGE))
                txtLog.append(s.getSender().getUsername() + ": " + s.getContent() + "\n");
        else if(s.getType().equals(Message.Type.FILE))
                txtLog.append(s.getSender().getUsername() + ": Đã gửi file " + s.getContent() + "\n");
        }
    }
    // lấy tin nhắn đẻ gửi
    public String getMes(){
        return txtMes.getText();
    }

    public String getFriendName() {
        return receiver.getUsername();
    }
    public void showOff(){
        JOptionPane.showMessageDialog(null, "Bạn của bạn đã kết thúc cuộc trò chuyện");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        txtMes = new javax.swing.JTextField();
        btStopChat = new javax.swing.JButton();
        selectFile = new javax.swing.JButton();
        showFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        txtLog.setEditable(false);
        txtLog.setColumns(20);
        txtLog.setRows(5);
        jScrollPane1.setViewportView(txtLog);

        txtMes.setToolTipText("");
        txtMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMesActionPerformed(evt);
            }
        });
        txtMes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMesKeyPressed(evt);
            }
        });

        btStopChat.setText("Kết thúc trò chuyên");
        btStopChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStopChatActionPerformed(evt);
            }
        });

        selectFile.setText("Chọn file");
        selectFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFileActionPerformed(evt);
            }
        });

        showFile.setText("File được chia sẻ");
        showFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMes)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(selectFile)
                            .addGap(18, 18, 18)
                            .addComponent(showFile)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                            .addComponent(btStopChat))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMes, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showFile)
                    .addComponent(selectFile)
                    .addComponent(btStopChat))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMesActionPerformed
        
    }//GEN-LAST:event_txtMesActionPerformed

    private void txtMesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMesKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String content = getMes();
            if(!content.isEmpty()){
                //txtLog.append(sender.getUsername() + ": " + content + "\n");
                txtMes.setText("");
                LocalDateTime datetime = LocalDateTime.now();
                Message message = new Message(sender, receiver, content, datetime, Message.Type.MESSAGE);
                controller.sendToFriend(message);
            }
        }
    }//GEN-LAST:event_txtMesKeyPressed

    private void btStopChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStopChatActionPerformed
        try {
            controller.closeChat(receiver);
        } catch (IOException ex) {
            Logger.getLogger(ChatView.class.getName()).log(Level.SEVERE, null, ex);
        }
       this.dispose();
    }//GEN-LAST:event_btStopChatActionPerformed

    private void selectFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFileActionPerformed
        // TODO add your handling code here:
        String path = getFile();
        if(path != null)
            controller.fileHandler.sendFile(path, receiver);
    }//GEN-LAST:event_selectFileActionPerformed

    private void showFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFileActionPerformed
        // TODO add your handling code here:
        controller.fileHandler.showFileList(null, receiver);
        controller.fileHandler.requestFileList(receiver);
    }//GEN-LAST:event_showFileActionPerformed

    /**
     * @param args the command line arguments
     */

    public String getFile(){
        String path = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file");
        int r = fileChooser.showSaveDialog(null);
        if(r == JFileChooser.APPROVE_OPTION){
            path = fileChooser.getSelectedFile().getAbsolutePath();
            if(path != null)
                controller.fileHandler.sendFile(path, receiver);
            //txtLog.append(sender.getUsername() + ": Đã gửi file " +  fileChooser.getSelectedFile().getName() + '\n');
            LocalDateTime datetime = LocalDateTime.now();
            Message message = new Message(sender, receiver, fileChooser.getSelectedFile().getName(), datetime, Message.Type.FILE);
            controller.sendToFriend(message);
        }
        return path;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btStopChat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton selectFile;
    private javax.swing.JButton showFile;
    private javax.swing.JTextArea txtLog;
    private javax.swing.JTextField txtMes;
    // End of variables declaration//GEN-END:variables

}
