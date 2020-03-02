/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loan;

import finance.digana.JDBC;
import java.awt.Color;
import main.*;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Teran Weerasinghe
 */
public class NewLoan extends javax.swing.JFrame {

    int xmouse, ymouse;
    JDBC jdbc = new JDBC();
    loan_function func = new loan_function();
    public final static String UI_NAME = "Add Loan Document";
    public final static String UI_NAME1 = "Add New Loan";

    /**
     * Creates new form Home
     */
    public NewLoan(String ID) {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/loanicon.png")));
        clear();
        onLoad(ID);

    }
    
    public void onLoad(String ID){
        if(!(ID.equals(""))){
            nic.setText(ID);
            int customerIdUsingNIC = getCustomerIdUsingNIC(nic.getText());
            if (customerIdUsingNIC > 0) {
                loan_id.setText(genarateLoanID());
                lone_amount.setEditable(true);
                lone_amount.grabFocus();

            }
            
            
        }
    
    }

    //set get user 
    private int getUserid() {
        return main.UserLogin.userID;
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
                        c_contact.setText(RS.getString("contact1"));
                        String SQL2 = "SELECT status FROM loan WHERE customer_id='" + CID + "'";
                        ResultSet RS2 = jdbc.getData(SQL2);
                        while (RS2.next()) {
                            System.out.println(RS2.getInt("status"));
                            if (RS2.getInt("status") == 2) {
                                unpaid.setText("YES");
                                unpaid.setForeground(Color.white);
                            } else {
                                unpaid.setText("NO");
                                unpaid.setForeground(Color.white);
                            }
                        }

                        c_folderid.setText(RS.getString("folderid"));
                        I = CID;
                        customer_id.setText(I + "");

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "No Customer Data Found !", "ERROR", 3);
                        nic.grabFocus();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "No Customer Data Found !", "ERROR", 3);
                    nic.grabFocus();
                    System.out.println(e);
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

    private void clear() {
        lone_amount.setEditable(false);
        lone_amount.setText("");
        interest_rate.setText("");
        interest_rate.setEditable(false);
        c_contact.setText("");
        c_name.setText("");
        c_nic.setText("");
        c_folderid.setText("");
        unpaid.setText("");
        loan_id.setText("");
        nic.setEditable(true);
        nic.setText("");
        customer_id.setText("");
        nic.grabFocus();
        func.clearData();
        read.setText("");

    }

    private String genarateLoanID() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat datetime = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String D = dateFormat.format(cal.getTime()).replaceAll("/", "");
        String T = datetime.format(cal.getTime()).replace(":", "");
        String LOAN_ID = "LN" + D + T;
        func.setGenaratedLoanID(LOAN_ID);
        return LOAN_ID;

    }

    private int saveData(int status) {
        String LOANID = func.getGenaratedLoanID();
        String CID = customer_id.getText();
        int I = 0;
        int user_id = getUserid();
        if (!(LOANID.equals("")) && !(CID.equals("")) && !(lone_amount.getText().equals("")) && !(interest_rate.getText().equals(""))) {
            if (user_id > 0) {

                func.setCID(CID);
                func.setLOANID(LOANID);
                String[] dateAndTime = func.getDateAndTime();
                String SQL = "INSERT INTO loan"
                        + "(genarated_id, amount, interest_rate, status, date, time, customer_id, user_id)"
                        + "VALUES('" + LOANID + "','" + lone_amount.getText() + "','" + interest_rate.getText() + "',"
                        + "'" + status + "','" + dateAndTime[0] + "','" + dateAndTime[1] + "','" + CID + "','" + user_id + "')";
                String SQL2 = "SELECT * FROM loan WHERE genarated_id='" + LOANID + "' AND status='1'";

                try {
                    ResultSet RS = jdbc.getData(SQL2);
                    if (RS.next()) {
                        I = 1;
                    } else {
                        I = jdbc.putData(SQL);
                    }

                } catch (Exception e) {
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "User Data Not Found Please Log Out And Log In Again !");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please Fill All Field First !");

        }
        return I;

    }

    private int testDocumentAdded() {
        int i = 0;
        try {
            String SQL = "SELECT document_details_id FROM "
                    + "document_loan WHERE genarated_loan_id='" + func.GenaratedLoanID + "'";
            ResultSet RS = jdbc.getData(SQL);
            if (RS.next()) {
                i = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }

    private int saveLoanPayment() {
        int i = 0;
        try {
            String SQL = "SELECT loan_id FROM loan WHERE genarated_id='" + func.getGenaratedLoanID() + "' AND status='2'";
            ResultSet RS = jdbc.getData(SQL);
            if (RS.next()) {
                int id = RS.getInt("loan_id");
                double amount = Double.parseDouble(lone_amount.getText());
                double rate = Double.parseDouble(interest_rate.getText());
                String[] time_date = func.getDateAndTime();
                String added_date = time_date[0];
                String added_time = time_date[1];
                String ex_date = time_date[2];

                BigDecimal HUNDRED = new BigDecimal("100");
                BigDecimal amount_bd = new BigDecimal(amount);
                BigDecimal rate_bd = new BigDecimal(rate);

                BigDecimal res = amount_bd.multiply(rate_bd);
                BigDecimal result = res.divide(HUNDRED, BigDecimal.ROUND_HALF_EVEN);

                String SQL2 = "INSERT INTO "
                        + "loan_status(last_loan_amount, last_interest_amount, month, status, aded_date, aded_time, ex_date, loan_id)"
                        + "VALUES('" + amount + "','" + result.doubleValue() + "','1','2','" + added_date + "',"
                        + "'" + added_time + "','" + ex_date + "','" + id + "')";

                int y = jdbc.putData(SQL2);
                if (y == 1) {
                    i = 1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }

    private int updateStatus() {
        int i = 0;
        try {
            String SQL = "UPDATE loan SET status='2' WHERE "
                    + "genarated_id='" + func.getGenaratedLoanID() + "'";
            int g = jdbc.putData(SQL);
            if (g == 1) {
                int s = saveLoanPayment();
                if (s == 1) {
                    i = 1;
                } else if (s == 0) {
                    String SQL2 = "UPDATE loan SET status='1' WHERE "
                            + "genarated_id='" + func.getGenaratedLoanID() + "'";
                    int g2 = jdbc.putData(SQL2);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    private void save() {
        //check alredy saved data

        try {
            String SQL = "SELECT status FROM loan "
                    + "WHERE genarated_id='" + func.getGenaratedLoanID() + "'";
            ResultSet RS = jdbc.getData(SQL);
            if (RS.next()) {
                int i = testDocumentAdded();
                if (i == 0) {
                    int a = JOptionPane.showConfirmDialog(rootPane, "Do You Want to Proceed without An Documents ?");
                    if (a == 0) {
                        int b = updateStatus();
                        if (b == 1) {
                            JOptionPane.showMessageDialog(rootPane, "Successfully Issued !");
                            clear();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Something Went Wrong  !");
                            clear();
                        }
                    } else if (a == 1) {

                    } else if (a == 2) {
                        clear();
                    }

                } else if (i == 1) {
                    int b = updateStatus();
                    if (b == 1) {
                        JOptionPane.showMessageDialog(rootPane, "Successfully Issued !");
                        clear();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Something Went Wrong  !");
                        clear();
                    }
                }
            } else {
                int val = saveData(1);
                if (val == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Error While Saving Data ! Please Try Again");
                } else if (val == 1) {
                    int x = testDocumentAdded();
                    if (x == 0) {
                        int a = JOptionPane.showConfirmDialog(rootPane, "Do You Want to Proceed without An Documents ?");
                        if (a == 0) {
                            int b = updateStatus();
                            if (b == 1) {
                                JOptionPane.showMessageDialog(rootPane, "Successfully Issued !");
                                clear();
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Something Went Wrong  !");
                                clear();
                            }
                        } else if (a == 1) {

                        } else if (a == 2) {
                            clear();
                        }

                    } else if (x == 1) {
                        int b = updateStatus();
                        if (b == 1) {
                            JOptionPane.showMessageDialog(rootPane, "Successfully Issued !");
                            clear();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Something Went Wrong  !");
                            clear();
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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

        customer_id = new javax.swing.JLabel();
        c_folderid = new javax.swing.JLabel();
        nic = new javax.swing.JTextField();
        c_name = new javax.swing.JTextField();
        c_contact = new javax.swing.JTextField();
        lone_amount = new javax.swing.JTextField();
        interest_rate = new javax.swing.JTextField();
        c_nic = new javax.swing.JTextField();
        loan_id = new javax.swing.JLabel();
        unpaid = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        read = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("NEW LOAN");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        c_folderid.setBackground(new java.awt.Color(255, 255, 255));
        c_folderid.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        c_folderid.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(c_folderid, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 300, 190, 40));

        nic.setBackground(new java.awt.Color(57, 61, 75));
        nic.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        nic.setForeground(new java.awt.Color(255, 255, 255));
        nic.setBorder(null);
        nic.setCaretColor(new java.awt.Color(255, 255, 255));
        nic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nicMouseClicked(evt);
            }
        });
        nic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nicKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nicKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nicKeyTyped(evt);
            }
        });
        getContentPane().add(nic, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 121, 359, 24));

        c_name.setEditable(false);
        c_name.setBackground(new java.awt.Color(57, 61, 75));
        c_name.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        c_name.setForeground(new java.awt.Color(255, 255, 255));
        c_name.setBorder(null);
        c_name.setCaretColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(c_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 247, 359, 24));

        c_contact.setEditable(false);
        c_contact.setBackground(new java.awt.Color(57, 61, 75));
        c_contact.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        c_contact.setForeground(new java.awt.Color(255, 255, 255));
        c_contact.setBorder(null);
        c_contact.setCaretColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(c_contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 307, 359, 24));

        lone_amount.setBackground(new java.awt.Color(57, 61, 75));
        lone_amount.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        lone_amount.setForeground(new java.awt.Color(255, 255, 255));
        lone_amount.setBorder(null);
        lone_amount.setCaretColor(new java.awt.Color(255, 255, 255));
        lone_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lone_amountActionPerformed(evt);
            }
        });
        lone_amount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lone_amountKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lone_amountKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lone_amountKeyTyped(evt);
            }
        });
        getContentPane().add(lone_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 507, 359, 24));

        interest_rate.setBackground(new java.awt.Color(57, 61, 75));
        interest_rate.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        interest_rate.setForeground(new java.awt.Color(255, 255, 255));
        interest_rate.setBorder(null);
        interest_rate.setCaretColor(new java.awt.Color(255, 255, 255));
        interest_rate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                interest_rateKeyTyped(evt);
            }
        });
        getContentPane().add(interest_rate, new org.netbeans.lib.awtextra.AbsoluteConstraints(428, 507, 359, 24));

        c_nic.setEditable(false);
        c_nic.setBackground(new java.awt.Color(57, 61, 75));
        c_nic.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        c_nic.setForeground(new java.awt.Color(255, 255, 255));
        c_nic.setBorder(null);
        c_nic.setCaretColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(c_nic, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 247, 359, 24));

        loan_id.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        loan_id.setForeground(new java.awt.Color(255, 255, 255));
        loan_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(loan_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 429, 220, 25));

        unpaid.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        unpaid.setForeground(new java.awt.Color(255, 255, 255));
        unpaid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        unpaid.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(unpaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 245, 225, 25));

        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(833, 606, 106, 49));

        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(663, 606, 156, 48));

        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
        });
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 606, 106, 49));

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

        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(663, 606, 156, 48));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("   ADD DOCUMENT DETAILS");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 490, 190, 40));

        read.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        read.setForeground(new java.awt.Color(255, 255, 255));
        read.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                readKeyReleased(evt);
            }
        });
        getContentPane().add(read, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, 760, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("FOLDER ID:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 300, 80, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/newLoanBackground.jpg"))); // NOI18N
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

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        sleep();
        jLabel6.setIcon(null);
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        sleep();
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/img/save2.jpg")));
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        sleep();
        jLabel5.setIcon(null);
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        sleep();
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/img/refresh2.jpg")));
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        sleep();
        jLabel7.setIcon(new ImageIcon(getClass().getResource("/img/saveandissue2.jpg")));
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        sleep();
        jLabel7.setIcon(null);
    }//GEN-LAST:event_jLabel7MouseExited

    private void nicKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nicKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_nicKeyTyped

    private void nicKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nicKeyReleased

    }//GEN-LAST:event_nicKeyReleased

    private void lone_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lone_amountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lone_amountActionPerformed

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        jLabel11.setForeground(Color.orange);
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        jLabel11.setForeground(Color.white);
    }//GEN-LAST:event_jLabel11MouseExited

    private void nicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nicKeyPressed
        if (evt.getKeyCode() == 10) {
            int customerIdUsingNIC = getCustomerIdUsingNIC(nic.getText());
            if (customerIdUsingNIC > 0) {
                loan_id.setText(genarateLoanID());
                lone_amount.setEditable(true);
                lone_amount.grabFocus();

            }
        }
    }//GEN-LAST:event_nicKeyPressed

    private void nicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nicMouseClicked
        if (evt.getButton() == 1) {
            clear();
        }
    }//GEN-LAST:event_nicMouseClicked

    private void lone_amountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lone_amountKeyTyped
        if (!(Character.isDigit(evt.getKeyChar()))) {
            evt.consume();
        }
    }//GEN-LAST:event_lone_amountKeyTyped

    private void readKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_readKeyReleased

    }//GEN-LAST:event_readKeyReleased

    private void lone_amountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lone_amountKeyReleased
        String DATA = lone_amount.getText();
        if (!(DATA.equals(""))) {

            if (Double.parseDouble(DATA) < 990000000) {
                int val = Integer.parseInt(DATA);
                read.setText(func.convertNumberToWords(val) + "  " + "Rupees Only");
            }

        } else {
            read.setText("");
        }


    }//GEN-LAST:event_lone_amountKeyReleased

    private void lone_amountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lone_amountKeyPressed
        if (evt.getKeyCode() == 10) {
            if (!(lone_amount.getText().equals(""))) {
                interest_rate.setEditable(true);
                interest_rate.grabFocus();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please Enter Loan Amount !", "ERROR", 3);
            }
        }
    }//GEN-LAST:event_lone_amountKeyPressed

    private void interest_rateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_interest_rateKeyTyped
        if (evt.getKeyChar() == '.') {
            String QC = interest_rate.getText();
            int counter = 0;
            for (int i = 0; i < QC.length(); i++) {
                if (QC.charAt(i) == '.') {
                    counter++;
                }
            }
            if (counter >= 1) {
                evt.consume();
            }
        } else if (!(Character.isDigit(evt.getKeyChar()))) {
            evt.consume();
        }
    }//GEN-LAST:event_interest_rateKeyTyped

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        if (evt.getButton() == 1) {
            if (!UserLogin.userPrivilage.contains(UI_NAME)) {
                JOptionPane.showMessageDialog(this, "Access Denied");
            } else {
                String LOANID = func.getGenaratedLoanID();
                String CID = customer_id.getText();

                if (!(LOANID.equals("")) && !(CID.equals("")) && !(lone_amount.getText().equals("")) && !(interest_rate.getText().equals(""))) {

                    if (LOANID.equals(loan_id.getText())) {
                        int i = JOptionPane.showConfirmDialog(rootPane, "Do You Want To Add Documents For Loan! It will Save Loan Details !");
                        switch (i) {
                            case 0:
                                int I = saveData(1);
                                if (I == 1) {

                                    new document.AddDocument().setVisible(true);

                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Cant Save Loan Data Please Re Try !");
                                    clear();
                                }
                                break;

                            case 1:
                                interest_rate.grabFocus();
                                break;
                            case 2:
                                clear();
                                break;
                            default:

                                break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Error With Genarating loan ID REFRESH..");
                        clear();
                    }

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Please Fill Loan Details First !");
                }
            }

        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        if (evt.getButton() == 1) {
            if (!UserLogin.userPrivilage.contains(UI_NAME1)) {
                    JOptionPane.showMessageDialog(rootPane, "Access Denied");
            } else {
                save();
            }
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        if (evt.getButton() == 1) {
            clear();
        }
    }//GEN-LAST:event_jLabel5MouseClicked
    int ml = 20;

    public void sleep() {
        try {
            Thread.sleep(ml);

        } catch (InterruptedException ex) {
            Logger.getLogger(NewLoan.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(NewLoan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewLoan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewLoan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewLoan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new NewLoan("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField c_contact;
    private javax.swing.JLabel c_folderid;
    private javax.swing.JTextField c_name;
    private javax.swing.JTextField c_nic;
    private javax.swing.JLabel customer_id;
    private javax.swing.JTextField interest_rate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel loan_id;
    private javax.swing.JTextField lone_amount;
    private javax.swing.JTextField nic;
    private javax.swing.JLabel read;
    private javax.swing.JLabel unpaid;
    // End of variables declaration//GEN-END:variables
}
