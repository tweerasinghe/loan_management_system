/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import finance.digana.JDBC;
import main.*;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Teran Weerasinghe
 */
public class setting extends javax.swing.JFrame {
public final static String UI_NAME = "Settings";
    int xmouse, ymouse;
    JDBC jdbc;
    int userID;

    /**
     * Creates new form Home
     */
    public setting() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/home.png")));

        jdbc = JDBC.getInstance();
        loadDetails();
    }
    String shopName = null;
    String shopAddress = null;
    String shopContact = null;
    String shopEmail = null;

    private void loadDetails() {
        try {

            ResultSet rs = jdbc.getData("select * from shopdetails where idshopDetails='1';");
            if (rs.next()) {

                shopName = rs.getString("shopName");
                shopAddress = rs.getString("shopAddress");
                shopContact = rs.getString("shopContact");
                shopEmail = rs.getString("shopEmail");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        sName.setText(shopName);
        sAddress.setText(shopAddress);
        sContact.setText(shopContact);
        sEmail.setText(shopEmail);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        sName = new javax.swing.JTextField();
        sAddress = new javax.swing.JTextField();
        sContact = new javax.swing.JTextField();
        sEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SETTINGS");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sName.setBackground(new java.awt.Color(57, 61, 75));
        sName.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        sName.setForeground(new java.awt.Color(255, 255, 255));
        sName.setBorder(null);
        sName.setCaretColor(new java.awt.Color(255, 255, 255));
        sName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sNameKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sNameKeyTyped(evt);
            }
        });
        jPanel2.add(sName, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 122, 342, 20));

        sAddress.setBackground(new java.awt.Color(57, 61, 75));
        sAddress.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        sAddress.setForeground(new java.awt.Color(255, 255, 255));
        sAddress.setBorder(null);
        sAddress.setCaretColor(new java.awt.Color(255, 255, 255));
        sAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sAddressKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sAddressKeyTyped(evt);
            }
        });
        jPanel2.add(sAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 172, 342, 20));

        sContact.setBackground(new java.awt.Color(57, 61, 75));
        sContact.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        sContact.setForeground(new java.awt.Color(255, 255, 255));
        sContact.setBorder(null);
        sContact.setCaretColor(new java.awt.Color(255, 255, 255));
        sContact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sContactKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sContactKeyTyped(evt);
            }
        });
        jPanel2.add(sContact, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 222, 342, 20));

        sEmail.setBackground(new java.awt.Color(57, 61, 75));
        sEmail.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        sEmail.setForeground(new java.awt.Color(255, 255, 255));
        sEmail.setBorder(null);
        sEmail.setCaretColor(new java.awt.Color(255, 255, 255));
        sEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sEmailKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sEmailKeyTyped(evt);
            }
        });
        jPanel2.add(sEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 272, 342, 20));

        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 258, 117, 35));

        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(679, 495, 104, 45));

        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 208, 117, 35));

        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 158, 117, 35));

        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 108, 117, 35));

        jLabel5.setText("     ");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
        });
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 11, 20, 20));

        jLabel6.setText("     ");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
        });
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 11, 20, 20));

        jLabel7.setText("        ");
        jLabel7.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel7MouseDragged(evt);
            }
        });
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel7MousePressed(evt);
            }
        });
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 812, 70));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/settingsBackground.jpg"))); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 812, 552));

        setSize(new java.awt.Dimension(812, 552));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    int ml = 20;

    public void sleep() {
        try {
            Thread.sleep(ml);
        } catch (InterruptedException ex) {
            Logger.getLogger(setting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        xmouse = evt.getX();
        ymouse = evt.getY();
    }//GEN-LAST:event_jLabel7MousePressed

    private void jLabel7MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseDragged
        setLocation(evt.getXOnScreen() - xmouse, evt.getYOnScreen() - ymouse);
    }//GEN-LAST:event_jLabel7MouseDragged

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/img/close2.png")));
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        if (evt.getButton() == 1) {
            this.dispose();
            Home.h.setState(NORMAL);
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        sleep();
        jLabel6.setIcon(null);
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        sleep();
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/img/minimize2.png")));
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        if (evt.getButton() == 1) {

            this.setState(ICONIFIED);
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        sleep();
        jLabel5.setIcon(null);
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered

        sleep();
        jLabel4.setIcon(new ImageIcon(getClass().getResource("/img/save2222.jpg")));
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        sleep();
        jLabel4.setIcon(null);
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        sleep();
        jLabel9.setIcon(new ImageIcon(getClass().getResource("/img/save2222.jpg")));
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        sleep();
        jLabel9.setIcon(null);
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        sleep();
        jLabel10.setIcon(new ImageIcon(getClass().getResource("/img/save2222.jpg")));
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
        sleep();
        jLabel10.setIcon(null);
    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        sleep();
        jLabel11.setIcon(new ImageIcon(getClass().getResource("/img/save2222.jpg")));
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        sleep();
        jLabel11.setIcon(null);
    }//GEN-LAST:event_jLabel11MouseExited

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        sleep();
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/img/backup2.jpg")));
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        sleep();
        jLabel1.setIcon(null);
    }//GEN-LAST:event_jLabel1MouseExited

    private void sNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sNameKeyPressed

    }//GEN-LAST:event_sNameKeyPressed

    private void sNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sNameKeyTyped

    }//GEN-LAST:event_sNameKeyTyped

    private void sAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sAddressKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sAddressKeyPressed

    private void sAddressKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sAddressKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_sAddressKeyTyped

    private void sContactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sContactKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sContactKeyPressed

    private void sContactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sContactKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_sContactKeyTyped

    private void sEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sEmailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sEmailKeyPressed

    private void sEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sEmailKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_sEmailKeyTyped

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        if (evt.getButton() == 1) {

            try {
                String path = new File(".").getCanonicalPath();

                path += "\\backup\\MySqlBF.exe";
                //Runtime rt = Runtime.getRuntime();
                //Process p = rt.exec(path);

                Process p = Runtime.getRuntime().exec(path);
                p.waitFor();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        String value = sName.getText();
        updateShopDetails(evt, value, "Enter Shop Name", "update shopdetails set shopName = '"+value+"'  where idshopDetails='1';");
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        String value = sAddress.getText();
        updateShopDetails(evt, value, "Enter Shop Address", "update shopdetails set shopAddress = '"+value+"'  where idshopDetails='1';");
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
       String value = sContact.getText();
        updateShopDetails(evt, value, "Enter Shop Contact", "update shopdetails set shopContact = '"+value+"'  where idshopDetails='1';");
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
         String value = sEmail.getText();
        updateShopDetails(evt, value, "Enter Shop Email", "update shopdetails set shopEmail = '"+value+"'  where idshopDetails='1';");
    }//GEN-LAST:event_jLabel11MouseClicked
    private void updateShopDetails(MouseEvent evt,String value,String type,String sql){
        if(evt.getButton() == 1){
            
            if(value.isEmpty()){
                JOptionPane.showMessageDialog(this, type, "Info", JOptionPane.OK_OPTION);
            }
            else {
                
                
                
             
                try {
                    int status = jdbc.putData(sql);
                    if(status == 1){
                        JOptionPane.showMessageDialog(this, "Data Saved", "Info", JOptionPane.INFORMATION_MESSAGE);
                        loadDetails();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Try Again!", "Info", JOptionPane.OK_OPTION);
                    }
                } catch (Exception e) {
                }
            }
        }
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
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new setting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField sAddress;
    private javax.swing.JTextField sContact;
    private javax.swing.JTextField sEmail;
    private javax.swing.JTextField sName;
    // End of variables declaration//GEN-END:variables
}
