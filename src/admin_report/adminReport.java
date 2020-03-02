/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin_report;

import finance.digana.Helper;
import finance.digana.JDBC;
import main.*;
import java.awt.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Teran Weerasinghe
 */
public class adminReport extends javax.swing.JFrame {

    int xmouse, ymouse;
    JDBC jdbc = new JDBC();
    int userID;
    public final static String UI_NAME = "Admin Reports";

    /**
     * Creates new form Home
     */
    public adminReport() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/home.png")));
    }

    public void AdminReport(String MINDATE, String MAXDATE) {

        try {

            String path = new File(".").getCanonicalPath();
            path += "\\report\\AdminReport.jrxml";

            String shopName = null;
            String shopAddress = null;
            String shopContact = null;
            String shopEmail = null;

            ResultSet rs = jdbc.getData("select * from shopdetails where idshopDetails='1';");
            if (rs.next()) {

                shopName = rs.getString("shopName");
                shopAddress = rs.getString("shopAddress");
                shopContact = rs.getString("shopContact");
                shopEmail = rs.getString("shopEmail");

            }

            JasperReport jr = JasperCompileManager.compileReport(path);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("date", Helper.getDateAndTime("date"));
            parameters.put("time", Helper.getDateAndTime("time"));

            parameters.put("shopName", shopName);
            parameters.put("shopAddress", shopAddress);
            parameters.put("shopContact", shopContact);
            parameters.put("shopEmail", shopEmail);
            parameters.put("minDate", MINDATE);
            parameters.put("maxDate", MAXDATE);

            rs = jdbc.getData("select name,status from user where user_id = '" + UserLogin.userID + "';");
            if (rs.next()) {
                boolean status = rs.getBoolean("status");
                if (status) {
                    parameters.put("user", rs.getString("name"));
                } else {
                    parameters.put("user", rs.getString("name") + "(DELETED)");
                }
            }

            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JDBC().con());
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        date1 = new datechooser.beans.DateChooserCombo();
        date2 = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ADMIN REPORT");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 110, 340, 46));

        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(373, 166, 117, 35));
        jPanel2.add(date1, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 165, 150, 36));
        jPanel2.add(date2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 165, 150, 36));

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

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/adminReportBackground.jpg"))); // NOI18N
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
            Logger.getLogger(adminReport.class.getName()).log(Level.SEVERE, null, ex);
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

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        sleep();
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/img/generateDayReport2.jpg")));
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        sleep();
        jLabel1.setIcon(null);
    }//GEN-LAST:event_jLabel1MouseExited

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered

        sleep();
        jLabel4.setIcon(new ImageIcon(getClass().getResource("/img/reportGenerate2.jpg")));
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        sleep();
        jLabel4.setIcon(null);
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        if (evt.getButton() == 1) {

            String MIN = Helper.getDateAndTime("date");
            String[] M = MIN.split("/");
            String NEW_M_DATE = M[0] + "-" + M[1] + "-" + M[2];
            AdminReport(NEW_M_DATE, NEW_M_DATE);
            
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (evt.getButton() == 1) {

            String MIN = date1.getText();
            String[] M = MIN.split("/");
            String NEW_M_DATE = "20" + M[2] + "-" + M[0] + "-" + M[1];
            String MAX = date2.getText();
            String[] MX = MAX.split("/");
            String NEW_MX_DATE = "20" + MX[2] + "-" + MX[0] + "-" + MX[1];
            
            AdminReport(NEW_M_DATE, NEW_MX_DATE);
        }
    }//GEN-LAST:event_jLabel4MouseClicked

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
            java.util.logging.Logger.getLogger(adminReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminReport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo date1;
    private datechooser.beans.DateChooserCombo date2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
