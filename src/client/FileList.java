/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Cuong
 */
public class FileList extends javax.swing.JFrame {

    private String saveDIR = "C:\\Users\\Cuong\\Documents\\NetBeansProjects\\Clone chat\\Chat_v1.1\\src\\client\\FileStorage\\";
    private Client clientController;

    /**
     * Creates new form FileList
     */
    public FileList(Client clientController, String sender) {
        initComponents();
        this.clientController = clientController;
    }

    public void setFiles(List<String> list){
        if(list != null){
            jList1.removeAll();
            DefaultListModel model = new DefaultListModel();
        
            for(int i=0; i<list.size(); i++){
                model.addElement(list.get(i));
            }
            jList1.setModel(model);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        download = new javax.swing.JButton();
        openFolder = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        download.setText("Tải");
        download.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadActionPerformed(evt);
            }
        });

        openFolder.setText("Mở folder chứa file");
        openFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFolderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(openFolder)
                    .addComponent(download, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(download)
                        .addGap(18, 18, 18)
                        .addComponent(openFolder)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void downloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadActionPerformed
        // TODO add your handling code here:
        String filename = jList1.getSelectedValue();
        if(filename != null)
            clientController.download(filename);
    }//GEN-LAST:event_downloadActionPerformed

    private void openFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFolderActionPerformed
        try {
            // TODO add your handling code here:
            Desktop.getDesktop().open(new File(saveDIR));
        } catch (IOException ex) {
            Logger.getLogger(FileList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_openFolderActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public void showMessage(String s){
        JOptionPane.showMessageDialog(this, s);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton download;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openFolder;
    // End of variables declaration//GEN-END:variables
}
