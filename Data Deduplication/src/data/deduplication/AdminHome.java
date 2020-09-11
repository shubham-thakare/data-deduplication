package data.deduplication;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.cloudbus.cloudsim.ParameterException;
import utils.CloudsimUtility;
import utils.FileHandler;
import utils.FileOperations;

public class AdminHome extends javax.swing.JFrame {
    private JFileChooser fileUploader;
    private FileOperations fileOperations;
    private AllFiles allFiles;
    public AdminHome() {
        super("Cloud Data Deduplication - Admin");
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        fileUploader = new JFileChooser();
        fileOperations = new FileOperations();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        uploadFileButton = new javax.swing.JButton();
        AllFilesButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        UploadFileStatus = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(724, 477));
        setMinimumSize(new java.awt.Dimension(724, 477));

        uploadFileButton.setText("Upload File");
        uploadFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadFileButtonActionPerformed(evt);
            }
        });

        AllFilesButton.setText("All Files");
        AllFilesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllFilesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(uploadFileButton, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(AllFilesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uploadFileButton)
                .addGap(18, 18, 18)
                .addComponent(AllFilesButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        UploadFileStatus.setColumns(20);
        UploadFileStatus.setRows(5);
        UploadFileStatus.setText("Click on Upload File for splitting and uploading file on cloud storage...");
        jScrollPane2.setViewportView(UploadFileStatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void uploadFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadFileButtonActionPerformed
        CloudsimUtility cloudsimUtility = new CloudsimUtility();
        int isFileSelected = fileUploader.showOpenDialog(null); 
            if (isFileSelected == JFileChooser.APPROVE_OPTION) 
            { 
                try {
                    UploadFileStatus.setText("Selected file: " + fileUploader.getSelectedFile().getName() + "\n"
                            + "Original file size: " + fileUploader.getSelectedFile().length()/1024 + "Kb\n\n");
                    int sizeOccupied = fileOperations.uploadFile(fileUploader.getSelectedFile().getAbsolutePath());
                    cloudsimUtility.simulateCloudSim(fileUploader.getSelectedFile().getAbsolutePath(), sizeOccupied);
                } catch (ParameterException ex) {}
            } 
            else
                JOptionPane.showMessageDialog(this, "File upload operation aborted!"); 
    }//GEN-LAST:event_uploadFileButtonActionPerformed

    private void AllFilesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AllFilesButtonActionPerformed
        allFiles = new AllFiles();
        allFiles.show();
    }//GEN-LAST:event_AllFilesButtonActionPerformed

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AllFilesButton;
    public static javax.swing.JTextArea UploadFileStatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton uploadFileButton;
    // End of variables declaration//GEN-END:variables
}
