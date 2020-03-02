/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import finance.digana.Helper;
import finance.digana.JDBC;
import main.*;
import java.awt.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import loan.NewPayment;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Teran Weerasinghe
 */
public class searchCustomer extends javax.swing.JFrame {

    int xmouse, ymouse;
    int ROW;
    public final static String UI_NAME = "View Loan";
    public final static String UI_NAME1 = "Search Loan";
    public final static String UI_NAME2 = "Print Payment Details";

    /**
     * Creates new form Home
     */
    public searchCustomer() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/customer.jpg")));
        nic.grabFocus();

    }

    JDBC jdbc = new JDBC();

    private void clear() {
        nic.setText("");
        c_contact1.setText("");
        c_folder.setText("");
        c_name.setText("");
        c_nic.setText("");
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        nic.grabFocus();

    }

    private int getCustomerIdUsingNIC(String NIC) {

        int I = 0;
        if (!(NIC.equals(""))) {
            int LENGTH = NIC.length();
            if (LENGTH == 9 || LENGTH == 12) {
                try {
                    String SQL = "SELECT * FROM customer WHERE custoemer_nic='" + NIC + "' AND status='1'";
                    ResultSet RS = jdbc.getData(SQL);
                    if (RS.next()) {
                        int CID = RS.getInt("customer_id");
                        c_name.setText(RS.getString("name_full"));
                        String C_NIC = RS.getString("custoemer_nic");

                        switch (C_NIC.length()) {
                            case 9:
                                C_NIC = C_NIC + "V";
                                break;
                            case 12:
                                break;
                            default:
                                C_NIC = "invalid nic please update";
                                break;
                        }
                        c_nic.setText(C_NIC);
                        c_contact1.setText(RS.getString("contact1"));
                        String SQL2 = "SELECT * FROM loan WHERE customer_id='" + CID + "'";
                        ResultSet RS2 = jdbc.getData(SQL2);

                        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                        dtm.setRowCount(0);
                        while (RS2.next()) {
                            Vector v = new Vector();
                            v.add(RS2.getString("genarated_id"));
                            v.add("RS " + RS2.getString("amount"));
                            String status = RS2.getString("status");
                            if (status.equals("1")) {
                                v.add("NOT ISSUED");
                            } else if (status.equals("2")) {
                                v.add("PAY OFF");
                            } else if (status.equals("3")) {
                                v.add("SETTLED");
                            }
                            
                            v.add(RS2.getString("interest_rate") + "%");
                            String CIDD = RS2.getString("customer_id");
                            
                            String sql = "SELECT custoemer_nic FROM customer WHERE customer_id='" + CIDD + "' AND status='1'";
                            ResultSet rs = jdbc.getData(sql);
                            if (rs.next()) {
                                String NICC = rs.getString("custoemer_nic");
                                int len = NICC.length();
                                if (len == 9) {
                                    v.add(NICC + "V");
                                } else {
                                    v.add(NICC);
                                }
                            }
                            v.add(RS2.getString("date"));
                            dtm.addRow(v);
                        }

                        c_folder.setText(RS.getString("folderid"));
                        I = CID;

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "No Customer Data Found !", "ERROR", 3);
                        nic.grabFocus();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "No Customer Data Found !", "ERROR", 3);
                    nic.grabFocus();
                    e.printStackTrace();

                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please Enter Valid NIC !", "ERROR", 3);
                nic.grabFocus();
            }

        } else {
            JOptionPane.showMessageDialog(rootPane, "Please Enter NIC Without 'V'  And Hit Enter ! ", "ERROR", 3);
            nic.grabFocus();
        }

        return I;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        HiddenTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        c_name = new javax.swing.JLabel();
        c_nic = new javax.swing.JLabel();
        c_contact1 = new javax.swing.JLabel();
        c_folder = new javax.swing.JLabel();
        nic = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        jMenuItem1.setText("Show");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Print Payment Details");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        HiddenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PAYMENTID", "AMOUNT", "DATE", "MONTH", "USER"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(HiddenTable);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SEARCH CUSTOMER");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 606, 106, 49));

        c_name.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        c_name.setForeground(new java.awt.Color(255, 255, 255));
        c_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(c_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 190, 279, 30));

        c_nic.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        c_nic.setForeground(new java.awt.Color(255, 255, 255));
        c_nic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(c_nic, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 190, 279, 30));

        c_contact1.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        c_contact1.setForeground(new java.awt.Color(255, 255, 255));
        c_contact1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(c_contact1, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 190, 279, 30));

        c_folder.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        c_folder.setForeground(new java.awt.Color(255, 255, 255));
        c_folder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(c_folder, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 250, 279, 30));

        nic.setBackground(new java.awt.Color(57, 61, 75));
        nic.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        nic.setForeground(new java.awt.Color(255, 255, 255));
        nic.setBorder(null);
        nic.setCaretColor(new java.awt.Color(255, 255, 255));
        nic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nicKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nicKeyTyped(evt);
            }
        });
        getContentPane().add(nic, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 121, 359, 24));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/minimize2.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
        });
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1021, 10, 20, 20));

        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
        });
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1051, 10, 20, 20));

        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel2MouseDragged(evt);
            }
        });
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1083, 70));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "LOAN ID", "AMOUNT", "STATUS", "INTEREST RATE", "CUSTOMER NIC", "ISSUED DATE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 1020, 240));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/searchCustomerBackground.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setSize(new java.awt.Dimension(1083, 665));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseDragged
        setLocation(evt.getXOnScreen() - xmouse, evt.getYOnScreen() - ymouse);
    }//GEN-LAST:event_jLabel2MouseDragged

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        xmouse = evt.getX();
        ymouse = evt.getY();
    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/img/close2.png")));
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        jLabel3.setIcon(null);
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        jLabel4.setIcon(null);
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        jLabel4.setIcon(new ImageIcon(getClass().getResource("/img/minimize2.png")));
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        if (evt.getButton() == 1) {
            this.dispose();
            Home.h.setState(NORMAL);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (evt.getButton() == 1) {

            this.setState(ICONIFIED);

        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        sleep();
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/img/refresh2.jpg")));
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        sleep();
        jLabel5.setIcon(null);
    }//GEN-LAST:event_jLabel5MouseExited

    private void nicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nicKeyPressed
        if (evt.getKeyCode() == 10) {
            if (!UserLogin.userPrivilage.contains(UI_NAME1)) {
                JOptionPane.showMessageDialog(rootPane, "Access Denied");
            } else if (!(nic.getText().equals(""))) {
                getCustomerIdUsingNIC(nic.getText());
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please Enter NIC !");
            }
        }
    }//GEN-LAST:event_nicKeyPressed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        if (evt.getButton() == 1) {
            clear();
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void nicKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nicKeyTyped
        if (!(Character.isDigit(evt.getKeyChar()))) {
            evt.consume();
        }
    }//GEN-LAST:event_nicKeyTyped

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        if (evt.getButton() == 3) {
            ROW = jTable1.getSelectedRow();
        }
    }//GEN-LAST:event_jTable1MousePressed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        if (!(ROW < 0)) {
            if (evt.isPopupTrigger()) {

                jPopupMenu1.show(jTable1, evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_jTable1MouseReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (!UserLogin.userPrivilage.contains(UI_NAME)) {
            JOptionPane.showMessageDialog(this, "Access Denied", "Info", JOptionPane.OK_OPTION);
        } else {
            int row = jTable1.getSelectedRow();

            String S = jTable1.getValueAt(row, 0).toString();
            new NewPayment(S).setVisible(true);
        }


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (!UserLogin.userPrivilage.contains(UI_NAME2)) {
            JOptionPane.showMessageDialog(this, "Access Denied", "Info", JOptionPane.OK_OPTION);
        } else {
            int[] rows = jTable1.getSelectedRows();
            if (rows.length <= 0) {
                JOptionPane.showMessageDialog(this, "Please Select Row/s", "Info", JOptionPane.OK_OPTION);
            } else {
                try {
                    Map<String, Object> parameters = new HashMap<String, Object>();
                    DefaultTableModel dtm = (DefaultTableModel) HiddenTable.getModel();

                    if (rows.length == 1) {
                        String VAL = jTable1.getValueAt(rows[0], 0).toString();
                        ResultSet rs1 = jdbc.getData("select * from loan where genarated_id='" + VAL + "'");
                        if (rs1.next()) {

                            String id = rs1.getString("loan_id");
                            String CID = rs1.getString("customer_id");
                            ResultSet rs5 = jdbc.getData("select * from customer where customer_id='" + CID + "'");
                            if (rs5.next()) {
                                parameters.put("Cname", rs5.getString("name_full"));
                                parameters.put("Cnic", rs5.getString("custoemer_nic") + "v");
                            }

                            parameters.put("Amount", "RS" + " " + rs1.getString("amount"));
                            parameters.put("Rate", rs1.getString("interest_rate") + "%");

                            ResultSet rs2 = jdbc.getData("select * from loan_payment where loan_id='" + id + "'");
                            while (rs2.next()) {
                                Vector v = new Vector();

                                v.add(rs2.getString("genarated_id"));
                                v.add(rs2.getString("amount"));
                                v.add(rs2.getString("payed_date"));
                                v.add(rs2.getString("month"));
                                ResultSet rs4 = jdbc.getData("select name from user where user_id='" + rs2.getString("user_id") + "'");
                                if (rs4.next()) {
                                    v.add(rs4.getString("name"));
                                }

                                dtm.addRow(v);
                            }

                        }

                        String path = new File(".").getCanonicalPath();

                        path += "\\report\\LoanPaymentReport.jrxml";

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

                        parameters.put("date", Helper.getDateAndTime("date"));
                        parameters.put("time", Helper.getDateAndTime("time"));

                        parameters.put("shopName", shopName);
                        parameters.put("shopAddress", shopAddress);
                        parameters.put("shopContact", shopContact);
                        parameters.put("shopEmail", shopEmail);

                        parameters.put("LoanId", VAL);
                        parameters.put("LoanStatus", jTable1.getValueAt(rows[0], 2).toString());

                        rs = jdbc.getData("select name,status from user where user_id = '" + UserLogin.userID + "';");
                        if (rs.next()) {
                            boolean status = rs.getBoolean("status");
                            if (status) {
                                parameters.put("user", rs.getString("name"));
                            } else {
                                parameters.put("user", rs.getString("name") + "(DELETED)");
                            }
                        }

                        parameters.put("RCount", HiddenTable.getRowCount());

                        JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JRTableModelDataSource(HiddenTable.getModel()));
                        JasperViewer.viewReport(jp, false);

                    } else if (rows.length > 1) {
                        JOptionPane.showMessageDialog(this, "One Row At One Time !", "Info", JOptionPane.OK_OPTION);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    int ml = 20;

    public void sleep() {
        try {
            Thread.sleep(ml);
        } catch (InterruptedException ex) {
            Logger.getLogger(searchCustomer.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(searchCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(searchCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(searchCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(searchCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new searchCustomer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable HiddenTable;
    private javax.swing.JLabel c_contact1;
    private javax.swing.JLabel c_folder;
    private javax.swing.JLabel c_name;
    private javax.swing.JLabel c_nic;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField nic;
    // End of variables declaration//GEN-END:variables
}
