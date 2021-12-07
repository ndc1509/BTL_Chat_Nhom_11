/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Cuong
 */
public class MainMenu extends javax.swing.JFrame {
    
    private RequestView requestView;
    private Client controller;
    /**
     * Creates new form View
     */
    public MainMenu(Client controller) {
        this.controller = controller;
        initComponents();
        this.setVisible(true);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listOnline = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        listGlobal = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        btnAddFrd = new javax.swing.JButton();
        btChat = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        changePwd = new javax.swing.JMenuItem();
        frRequest = new javax.swing.JMenuItem();
        logout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("CHAT APP");

        jLabel1.setText("Danh sách bạn bè onine");

        jScrollPane3.setViewportView(listOnline);

        jScrollPane5.setViewportView(listGlobal);

        jLabel3.setText("GLOBAL");

        btnAddFrd.setText("Kết bạn");
        btnAddFrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFrdActionPerformed(evt);
            }
        });

        btChat.setText("Trò chuyện");
        btChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChatActionPerformed(evt);
            }
        });

        jToggleButton1.setText("Kênh chat chung");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(btChat, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                                .addGap(84, 84, 84)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnAddFrd, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                                    .addComponent(jLabel3)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 45, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btChat, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(btnAddFrd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(225, 225, 225))
        );

        jMenu1.setText("Menu");

        changePwd.setText("Thay đổi mật khẩu");
        jMenu1.add(changePwd);

        frRequest.setText("Yêu cầu kết bạn");
        frRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frRequestActionPerformed(evt);
            }
        });
        jMenu1.add(frRequest);

        logout.setText("Đăng xuất");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jMenu1.add(logout);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        int input = JOptionPane.showConfirmDialog(null, "Bạn muốn đăng xuất?", "Xác nhận", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        controller.logout(input);
    }//GEN-LAST:event_logoutActionPerformed

    private void frRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frRequestActionPerformed
        // TODO add your handling code here:
        controller.viewRequest();
    }//GEN-LAST:event_frRequestActionPerformed

    private void btnAddFrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFrdActionPerformed
        // TODO add your handling code here:
        String friend = listGlobal.getSelectedValue();
        controller.sendFriendRequest(friend);
    }//GEN-LAST:event_btnAddFrdActionPerformed

    public int acceptChat(String friend){
        int res = JOptionPane.showConfirmDialog(null,"Đồng ý ?" , friend + " muốn trò chuyện với bạn", JOptionPane.YES_NO_OPTION);
        return res;
    }
    
    public void showNotAcceptChat(){
        JOptionPane.showMessageDialog(null, "Không đồng ý trò chuyện");
    }
    
    
    private void btChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChatActionPerformed

        String friendName = listOnline.getSelectedValue();
        System.out.println(listOnline.getModel().getSize());
        if(listOnline.getModel().getSize() == 0){
            JOptionPane.showMessageDialog(null, "Bạn bè của bạn chưa online");
        }else if(friendName == null){
            JOptionPane.showMessageDialog(null, "Hãy chọn một người bạn để trò chuyện");
        }else{
            controller.sendRequestChat(friendName);  
        }
        
    }//GEN-LAST:event_btChatActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
//        ChatRoomGlobal chatRoom = new ChatRoomGlobal(controller, controller.getAccount().getUsername());
        controller.createGlobalChat();
//        chatRoom.setVisible(true);
//        this.setVisible(false);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    
    
    public void setGlobalList(List<String> list){
        listGlobal.removeAll();
        DefaultListModel model = new DefaultListModel();
        for(int i=0; i<list.size();i++)
            model.addElement(list.get(i));
        listGlobal.setModel(model);
    }
    
    public void setFrOnlineList(List<String> list){
        listOnline.removeAll();
        DefaultListModel model = new DefaultListModel();
        for(int i=0; i<list.size();i++)
            model.addElement(list.get(i));
        listOnline.setModel(model);
    }
    
    
//    public void setFrOfflineList(List<String> list){
//        listOffline.removeAll();
//        DefaultListModel model = new DefaultListModel();
//        for(int i=0; i<list.size();i++)
//            model.addElement(list.get(i));
//        listOffline.setModel(model);
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btChat;
    private javax.swing.JButton btnAddFrd;
    private javax.swing.JMenuItem changePwd;
    private javax.swing.JMenuItem frRequest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JList<String> listGlobal;
    private javax.swing.JList<String> listOnline;
    private javax.swing.JMenuItem logout;
    // End of variables declaration//GEN-END:variables
}
