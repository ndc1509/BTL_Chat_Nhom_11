/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

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
            txtLog.append(s.getSender().getUsername() + ": " + s.getContent() + "\n");
        }
    }

    //them tin nhắn mới
    public void addMess(Message mess){
        txtLog.append(mess.getSender().getUsername() + ": " + mess.getContent() + "\n");
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
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(txtMes)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(showFile)
                        .addGap(71, 71, 71)
                        .addComponent(btStopChat)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btStopChat)
                    .addComponent(selectFile)
                    .addComponent(showFile))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMesActionPerformed
        
    }//GEN-LAST:event_txtMesActionPerformed

    private void txtMesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMesKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String content = getMes();
            if(!content.isEmpty()){
                txtLog.append(sender.getUsername() + ": " + content + "\n");
                txtMes.setText("");
                LocalDateTime datetime = LocalDateTime.now();
                Message message = new Message(sender, receiver, content, datetime);
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
            controller.sendFile(path, receiver);
    }//GEN-LAST:event_selectFileActionPerformed

    private void showFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFileActionPerformed
        // TODO add your handling code here:
        controller.requestFileList(receiver);
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
            txtLog.append(sender.getUsername() + ": Đã gửi file " +  fileChooser.getSelectedFile().getName() + '\n');
            LocalDateTime datetime = LocalDateTime.now();
            Message message = new Message(sender, receiver, "Đã gửi file " +  fileChooser.getSelectedFile().getName(), datetime);
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
