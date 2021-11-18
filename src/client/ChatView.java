/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.KeyEvent;

/**
 *
 * @author Admin
 */
public class ChatView extends javax.swing.JFrame{

    private Client clientController;
    private String yourName;
    private String friendName;
    
    public ChatView(Client clientController, String user1, String user2) {
        initComponents();
        this.setVisible(true);
        
        yourName = user1;
        friendName = user2;
        
        this.setTitle(yourName + "=> " + friendName);
        this.clientController = clientController;
     
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btStopChat)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMes, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btStopChat)
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

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btStopChat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtLog;
    private javax.swing.JTextField txtMes;
    // End of variables declaration//GEN-END:variables

}
