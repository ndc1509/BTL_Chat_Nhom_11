/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import model.ChatLog;

/**
 *
 * @author Admin
 */
public class ChatView extends javax.swing.JFrame{

    private Client clientController;
    private String yourName;
    private String friendName;
    private ChatLog chatlog = new ChatLog();
    
    public ChatView(Client clientController, String user1, String user2) {
        initComponents();
        this.setVisible(true);
        
        yourName = user1;
        friendName = user2;
        
        this.setTitle(yourName + "=> " + friendName);
        this.clientController = clientController;
     
    }
    
    public void loadChatLog(ChatLog chatlog){
        this.chatlog = chatlog;
        ArrayList<String> chat = this.chatlog.getChatlog();
        for(String s : chat){
            txtLog.append(s + "\n");
        }
    }

    public void loadMes(){
        
    }
    //them tin nhắn mới
    public void addMess(String mes){
        txtLog.append(friendName + ": " + mes + "\n");
    }
    // lấy tin nhắn đẻ gửi
    public String getMes(){
        return txtMes.getText();
    }

    public String getFriendName() {
        return friendName;
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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMes)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(selectFile)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(showFile)
                            .addGap(71, 71, 71)
                            .addComponent(btStopChat))))
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
            String mes = getMes();
            if(!mes.isEmpty()){
                txtLog.append(yourName + ": " + mes + "\n");
                txtMes.setText("");
                clientController.sendToFriend(mes, friendName);
            }
        }
    }//GEN-LAST:event_txtMesKeyPressed

    private void btStopChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStopChatActionPerformed
       clientController.closeChat(friendName);
       this.dispose();
    }//GEN-LAST:event_btStopChatActionPerformed

    private void selectFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFileActionPerformed
        // TODO add your handling code here:
        String path = getFile();
        if(path != null)
            clientController.sendFile(path, friendName);
    }//GEN-LAST:event_selectFileActionPerformed

    private void showFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFileActionPerformed
        // TODO add your handling code here:
        clientController.requestFileList(friendName);
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
            txtLog.append(yourName + ": Đã gửi file " +  fileChooser.getSelectedFile().getName() + '\n');
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
