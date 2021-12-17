/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.control.Client;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import model.Account;
import model.ChatLog;
import model.FileInfo;
import model.Message;
import model.Room;

/**
 *
 * @author Cuong
 */
public class ChatRoomView extends javax.swing.JFrame {

    /**
     * Creates new form ChatRoomView
     */
    private Client controller;
    private Account sender;
    private Room room;
    private ChatLog chatlog = new ChatLog();

    public ChatRoomView() {
        initComponents();
        this.setVisible(true);
    }
    
    public ChatRoomView(Client controller, Account acc, Room r) {
        initComponents();
        this.setVisible(true);
        this.setTitle(r.getName());
        this.controller = controller;
        this.sender = acc;
        this.room = r;
        jLabel1.setText("Phòng chat " + r.getName());
        jLabel2.setText("Tài khoản: " + sender.getUsername());
        setMemberList();
        chatPane.setEditable(false);
        DefaultCaret caret = (DefaultCaret) chatPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    public void loadChatLog(ChatLog chatlog){
        this.chatlog = chatlog;
        for(Message msg : chatlog.getChatlog()){
            appendToPane(msg);
        }    
    }
    
    public void addMess(Message msg){
        appendToPane(msg);
    }
    
    public String getMes(){
        return input.getText();
    }
    
    public void refreshRoom(Room r){
        this.room = r;
        setMemberList();
    }
    
    public void setMemberList(){
        List<Account> list = room.getMember();
        memberList.removeAll();
        DefaultListModel model = new DefaultListModel();
        for(int i=0; i<list.size();i++)
            if(list.get(i).getId() != sender.getId())
                model.addElement(list.get(i).getUsername());
        memberList.setModel(model);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        memberList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        sendFile = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatPane = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        input = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        fileList = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        addMemMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        delMemMenu = new javax.swing.JMenuItem();
        leaveMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jScrollPane2.setViewportView(memberList);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("jLabel1");

        sendFile.setText("Gửi file");
        sendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFileActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(chatPane);

        jLabel2.setText("jLabel2");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/icon/grinning-face_1f600.png"))); // NOI18N
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/icon/smiling-face-with-heart-eyes_1f60d.png"))); // NOI18N
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputKeyPressed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/icon/angry-face_1f620.png"))); // NOI18N
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        fileList.setText("Xem DS File");
        fileList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileListActionPerformed(evt);
            }
        });

        addMemMenu.setText("Cài đặt");
        addMemMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMemMenuActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Thêm mới");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        addMemMenu.add(jMenuItem1);

        delMemMenu.setText("Xóa thành viên");
        delMemMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delMemMenuActionPerformed(evt);
            }
        });
        addMemMenu.add(delMemMenu);

        leaveMenu.setText("Rời khỏi cuộc trò chuyện");
        leaveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveMenuActionPerformed(evt);
            }
        });
        addMemMenu.add(leaveMenu);

        jMenuBar1.add(addMemMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(169, 169, 169)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(sendFile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fileList, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                            .addComponent(input))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(sendFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fileList))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton4))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private Style iconHandler(String iconfile){
        StyledDocument doc = chatPane.getStyledDocument();
        Style icon = doc.addStyle(iconfile, null);
        StyleConstants.setIcon(icon, new ImageIcon(this.getClass().getResource("/client/icon/" + iconfile)));
        return icon;
    }
    
    private Style imgHandler(String imgfile){
        StyledDocument doc = chatPane.getStyledDocument();
        Style icon = doc.addStyle(imgfile, null);
        String path = controller.fileHandler.saveDIR +imgfile;
        ImageIcon img = new ImageIcon(path);
        Image resized = img.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        StyleConstants.setIcon(icon, new ImageIcon(resized));
        return icon;
    }
    
    private void detectEmoji(int start, int end, StyledDocument doc) throws BadLocationException{
        HashMap<String, String> emoji = new HashMap<String, String>();
        emoji.put("=D", "grinning-face_1f600.png");
        emoji.put(":)", "slightly-smiling-face_1f642.png");
        emoji.put("=)", "smiling-face-with-smiling-eyes_1f60a.png");
        emoji.put(":(", "slightly-frowning-face_1f641.png");
        emoji.put(":P", "face-with-tongue_1f61b.png");
        emoji.put(":O", "face-with-open-mouth_1f62e.png");
        String text = doc.getText(start, end - start);
        emoji.forEach((k,v) -> {
            try {
                int i = text.indexOf(k);
                while(i>=0){
                    final SimpleAttributeSet atr = new SimpleAttributeSet(
                            doc.getCharacterElement(start + i).getAttributes());
                    if(StyleConstants.getIcon(atr) == null){
                        StyleConstants.setIcon(atr, new ImageIcon(this.getClass().getResource("/client/icon/" + v)));
                        StyleConstants.setLineSpacing(atr, 50);
                        doc.remove(start + i, 2);
                        doc.insertString(start+i, k, atr);
                        doc.insertString(doc.getLength(), "", null);                        
                    }
                    i = text.indexOf(k, i+2);
                }                
            } catch (BadLocationException ex) {
                Logger.getLogger(ChatRoomView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
    private void appendToPane(Message msg){
        StyledDocument doc = chatPane.getStyledDocument();
        
        SimpleAttributeSet left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(left, Color.BLACK);
        StyleConstants.setFontSize(left, 14);
        
        SimpleAttributeSet right = new SimpleAttributeSet();
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setForeground(right, Color.BLUE);
        StyleConstants.setFontSize(right, 14);

        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        StyleConstants.setForeground(center, Color.ORANGE);
        
        try{
            int offset = doc.getLength();
            if(msg.getSender().getId() == 0){
                String str = "\n" + msg.getContent();
                doc.insertString(doc.getLength(), str, center);
                doc.setParagraphAttributes(doc.getLength(), 1, center, false);
            } else if(msg.getSender().getId() == sender.getId()){
                switch(msg.getType()){
                    case FILE:
                        String str = "\nBạn: Đã gửi file " + msg.getContent();
                        doc.insertString(doc.getLength(), str, right);
                        doc.setParagraphAttributes(doc.getLength(), 1, right, false);
                        break;
                    case MESSAGE:
                        str = "\nBạn: " + msg.getContent();
                        doc.insertString(doc.getLength(), str, right);                        
                        int start = offset - 1;
                        int end = doc.getLength();
                        detectEmoji(start, end, doc);
                        //doc.insertString(doc.getLength(), "\n", null);
                        doc.setParagraphAttributes(doc.getLength(), 1, right, false);
                        break;
                    case EMOJI:
                        str = "\nBạn: ";
                        doc.insertString(doc.getLength(), str, right);
                        doc.setParagraphAttributes(doc.getLength(), 1, right, false);
                        doc.insertString(doc.getLength(), "123", iconHandler(msg.getContent()));
                        break;
                    case IMG: 
                        str = "\nBạn: ";
                        doc.insertString(doc.getLength(), str, right);
                        doc.setParagraphAttributes(doc.getLength(), 1, right, false);
                        doc.insertString(doc.getLength(), "123", imgHandler(msg.getContent()));
                        break;
                    default:
                        break;
                }
            } else {
                switch(msg.getType()){
                    case FILE:
                        String str = "\n" + msg.getSender().getUsername() + ": Đã gửi file " + msg.getContent();
                        doc.insertString(doc.getLength(), str, left);
                        doc.setParagraphAttributes(doc.getLength(),1, left, false);
                        break;
                    case MESSAGE:
                        str = "\n" + msg.getSender().getUsername() + ": " + msg.getContent();
                        doc.insertString(doc.getLength(), str, left);
                        int start = offset - 1;
                        int end = doc.getLength();
                        detectEmoji(start, end, doc);
                        //doc.insertString(doc.getLength(), "\n", null);
                        doc.setParagraphAttributes(doc.getLength(),1, left, false);   
                        break;
                    case EMOJI:
                        str = "\n" + msg.getSender().getUsername() + ": ";
                        doc.insertString(doc.getLength(), str, left);
                        doc.setParagraphAttributes(doc.getLength(), 1, left, false);
                        doc.insertString(doc.getLength(), "123", iconHandler(msg.getContent()));
                        break;
                    case IMG:
                        str = "\n" + msg.getSender().getUsername() + ": ";
                        doc.insertString(doc.getLength(), str, left);
                        doc.setParagraphAttributes(doc.getLength(), 1, left, false);
                        doc.insertString(doc.getLength(), "123", imgHandler(msg.getContent()));
                        break;
                    default:
                        break;
                }
            }
            chatPane.setCaretPosition(doc.getLength());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        String name = showAddMember(controller.getFrList());
        controller.addMember(name, room);
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        controller.closeChatRoom(room);
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void delMemMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delMemMenuActionPerformed
        // TODO add your handling code here:
        String name = showKickMember(room.getMember());
        controller.kickMember(name, room);
    }//GEN-LAST:event_delMemMenuActionPerformed

    private void leaveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveMenuActionPerformed
        // TODO add your handling code here:
        int i = JOptionPane.showConfirmDialog(this, "Bạn muốn rời phòng?", "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if(i == 0)
            controller.leaveRoom(room);
    }//GEN-LAST:event_leaveMenuActionPerformed

    private void addMemMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMemMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addMemMenuActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void inputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String content = getMes();
            if(!content.isEmpty()){
                input.setText("");
                LocalDateTime datetime = LocalDateTime.now();
                Message message = new Message(sender, room, content, datetime, Message.Type.MESSAGE);
                //appendToPane(message);
                controller.sendToRoom(message);
            }
        }
    }//GEN-LAST:event_inputKeyPressed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            String icon = "smiling-face-with-heart-eyes_1f60d.png";
            LocalDateTime datetime = LocalDateTime.now();
            Message message = new Message(sender, room, icon, datetime, Message.Type.EMOJI);
            //appendToPane(message);
            controller.sendToRoom(message);
        }   
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            String icon = "grinning-face_1f600.png";
            LocalDateTime datetime = LocalDateTime.now();
            Message message = new Message(sender, room, icon, datetime, Message.Type.EMOJI);
            //appendToPane(message);
            controller.sendToRoom(message);
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            String icon = "angry-face_1f620.png";
            LocalDateTime datetime = LocalDateTime.now();
            Message message = new Message(sender, room, icon, datetime, Message.Type.EMOJI);
            //appendToPane(message);
            controller.sendToRoom(message);
        }
    }//GEN-LAST:event_jButton4MouseClicked

    private void sendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFileActionPerformed
        // TODO add your handling code here:
        String path = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file");
        int r = fileChooser.showSaveDialog(null);
        if(r == JFileChooser.APPROVE_OPTION){
          
            path = fileChooser.getSelectedFile().getAbsolutePath();
            String filename = fileChooser.getSelectedFile().getName().toLowerCase();
            if(path != null)
                controller.fileHandler.sendFile(path, room);
            LocalDateTime datetime = LocalDateTime.now();
            Message message = new Message();
            if(filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg")){
                message = new Message(sender, room, fileChooser.getSelectedFile().getName(), datetime, Message.Type.IMG);
                //appendToPane(message);
                controller.sendToRoom(message);
            } else{
                message = new Message(sender, room, fileChooser.getSelectedFile().getName(), datetime, Message.Type.FILE);
                //appendToPane(message);
                controller.sendToRoom(message);            
            }  
        }
    }//GEN-LAST:event_sendFileActionPerformed

    private void fileListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileListActionPerformed
        // TODO add your handling code here:
        controller.fileHandler.showFileList(null, room);
        controller.fileHandler.requestFileList(room);
    }//GEN-LAST:event_fileListActionPerformed
    
    public String showAddMember(List<Account> frList){  
        List<String> toAdd = new ArrayList<>();
        for(Iterator<Account> fr = frList.iterator(); fr.hasNext();){
            Account friend = fr.next();
            boolean removed = false;
            for(Iterator<Account> mem = room.getMember().iterator(); mem.hasNext();){                
                Account member = mem.next();
                if(friend.getId() == member.getId()){
                    fr.remove();
                    removed = true;
                }
            }      
            if(!removed)
                toAdd.add(friend.getUsername());
        }
        return (String) JOptionPane.showInputDialog(null, "Hãy chọn một người", "Thêm thành viên",
                JOptionPane.QUESTION_MESSAGE, null, toAdd.toArray(), "");
    }
    
    public String showKickMember(List<Account> list){      
        List<String> toKick = new ArrayList<>();
        for(Account acc:list){
            toKick.add(acc.getUsername());
        }
        return (String) JOptionPane.showInputDialog(null, "Hãy chọn một người", "Xóa thành viên",
                JOptionPane.QUESTION_MESSAGE, null, toKick.toArray(), "");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatRoomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatRoomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatRoomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatRoomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        new ChatRoomView();
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu addMemMenu;
    private javax.swing.JTextPane chatPane;
    private javax.swing.JMenuItem delMemMenu;
    private javax.swing.JButton fileList;
    private javax.swing.JTextField input;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem leaveMenu;
    private javax.swing.JList<String> memberList;
    private javax.swing.JButton sendFile;
    // End of variables declaration//GEN-END:variables
}
