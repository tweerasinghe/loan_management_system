/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loan;

import finance.digana.Helper;
import finance.digana.JDBC;
import main.*;
import java.awt.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
public class NewPayment extends javax.swing.JFrame {

    int xmouse, ymouse;
    public final static String UI_NAME = "View Loan Details";
    public final static String UI_NAME1 = "Pay Loan Amount";

    /**
     * Creates new form Home
     */
    public NewPayment(String ID) {

        initComponents();
        onLoad(ID);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/customer.jpg")));

    }
    

    JDBC jdbc = new JDBC();
    loan_function func = new loan_function();

    public static String LOANID = "";
    public static String G_LONEID = "";
    public static String G_P_ID = "";

    private int getUserid() {
        return main.UserLogin.userID;
    }

    private String genaratePaymentID() {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat datetime = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String D = dateFormat.format(cal.getTime()).replaceAll("/", "");
        String T = datetime.format(cal.getTime()).replace(":", "");
        String LOAN_ID = "NPY" + D + T;
        func.setGenaratedLoanID(LOAN_ID);
        return LOAN_ID;

    }

    public void onLoad(String GLID) {
        if (!(GLID.equals(""))) {
            loan_id.setText(GLID);
            inEnterLoanID(GLID);
        }
    }

    public void printBill(String PID) {

        try {

            String path = new File(".").getCanonicalPath();
            path += "\\report\\PrintBill.jrxml";

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

            ResultSet rs1 = jdbc.getData("select * from loan_payment where genarated_id='" + PID + "'");
            if (rs1.next()) {
            ResultSet rsloan = jdbc.getData("select * from loan where loan_id='" + rs1.getString("loan_id") + "'");
            if(rsloan.next()){
            parameters.put("LoanId", rsloan.getString("genarated_id"));
            }
                parameters.put("InvoiceId", rs1.getString("idloan_payment"));
                parameters.put("Amount", rs1.getString("amount"));
                parameters.put("Cash", rs1.getString("cash"));
                parameters.put("Balance", rs1.getString("balance"));
                parameters.put("PaymentId", rs1.getString("genarated_id"));
                String lid = rs1.getString("loan_id");
                ResultSet rs2 = jdbc.getData("select customer_id from loan where loan_id='" + lid + "'");
                if (rs2.next()) {
                    String cid = rs2.getString("customer_id");
                    ResultSet rs3 = jdbc.getData("select * from customer where customer_id='" + cid + "'");
                    if (rs3.next()) {
                        parameters.put("Cname", rs3.getString("name_full"));
                        parameters.put("Cadress", rs3.getString("no") + "," + rs3.getString("street") + "," + rs3.getString("city"));
                        parameters.put("Cnic", rs3.getString("custoemer_nic"));
                    }
                }

            }

            rs = jdbc.getData("select name,status from user where user_id = '" + UserLogin.userID + "';");
            if (rs.next()) {
                boolean status = rs.getBoolean("status");
                if (status) {
                    parameters.put("user", rs.getString("name"));
                } else {
                    parameters.put("user", rs.getString("name") + "(DELETED)");
                }
            }

            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetPayment(String PaymentID, String StatusID, double Last_Loan_Value, double Last_Interest_Value) {
        try {
            String SQL = "";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int pay() {
        int Y = 0;
        String SQLL = "SELECT status FROM loan WHERE genarated_id='" + G_LONEID + "'";
        //loan_id, genarated_id, amount, interest_rate, status, date, time, customer_id, user_id
        String STA = "";
        try {
            ResultSet RS = jdbc.getData(SQLL);
            if (RS.next()) {
                STA = RS.getString("status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (STA.equals("2")) {

            if (!(LOANID.equals("")) && !(G_LONEID.equals(""))) {
                if (!(p_amount.getText().equals(""))) {

                    if (!(p_cash.getText().equals(""))) {
                        if (getUserid() > 0) {
                            double amount = Double.parseDouble(p_amount.getText());
                            double cash = Double.parseDouble(p_cash.getText());
                            String[] dateAndTime = func.getDateAndTime();
                            if (amount <= cash) {
                                double balance = cash - amount;
                                try {
                                    G_P_ID = genaratePaymentID();
                                    if (!(G_P_ID.equals(""))) {

                                        if (true) {
                                            String SQL2 = "SELECT * FROM loan_status WHERE loan_id='" + LOANID + "' AND status='2'";
                                            //idloan_status, last_loan_amount, last_interest_amount, month, status, aded_date, aded_time, ex_date, loan_id
                                            ResultSet RS2 = jdbc.getData(SQL2);
                                            if (RS2.next()) {
                                                double last_loan_value = RS2.getDouble("last_loan_amount");
                                                double last_interest_value = RS2.getDouble("last_interest_amount");
                                                if (last_interest_value >= amount) {
                                                    //idloan_payment, genarated_id, amount, cash, 
                                                    //balance, payment_type, month, payed_date, payed_time, loan_id, user_id
                                                    String SQL = "INSERT INTO "
                                                            + "loan_payment(genarated_id, "
                                                            + "amount,"
                                                            + " cash,"
                                                            + " balance,"
                                                            + " payment_type,"
                                                            + " month,"
                                                            + " payed_date,"
                                                            + " payed_time,"
                                                            + " loan_id,"
                                                            + " user_id,"
                                                            + "status) VALUES("
                                                            + "'" + G_P_ID + "',"
                                                            + "'" + amount + "',"
                                                            + "'" + cash + "',"
                                                            + "'" + balance + "',"
                                                            + "'1',"
                                                            + "'" + month_count.getText() + "',"
                                                            + "'" + dateAndTime[0] + "',"
                                                            + "'" + dateAndTime[1] + "',"
                                                            + "'" + LOANID + "',"
                                                            + "'" + getUserid() + "',"
                                                            + "'1')";

                                                    int I = jdbc.putData(SQL);

                                                    double newInterest = last_interest_value - amount;
                                                    String SQL3 = "UPDATE loan_status SET last_interest_amount='" + newInterest + "' WHERE loan_id='" + LOANID + "' AND status='2'";
                                                    int YY = jdbc.putData(SQL3);
                                                    if (YY == 1) {

                                                        Y = 1;

                                                    } else {
                                                        resetPayment(G_P_ID, LOANID, last_interest_value, last_loan_value);
                                                    }
                                                } else if (last_interest_value < amount) {

                                                    double total_paybel = last_interest_value + last_loan_value;
                                                    double b_val = amount - last_interest_value;

                                                    if (total_paybel == amount) {
                                                        //idloan_payment, genarated_id, amount, cash, 
                                                        //balance, payment_type, month, payed_date, payed_time, loan_id, user_id
                                                        String SQL = "INSERT INTO "
                                                                + "loan_payment(genarated_id, "
                                                                + "amount,"
                                                                + " cash,"
                                                                + " balance,"
                                                                + " payment_type,"
                                                                + " month,"
                                                                + " payed_date,"
                                                                + " payed_time,"
                                                                + " loan_id,"
                                                                + " user_id,"
                                                                + "status) VALUES("
                                                                + "'" + G_P_ID + "',"
                                                                + "'" + amount + "',"
                                                                + "'" + cash + "',"
                                                                + "'" + balance + "',"
                                                                + "'1',"
                                                                + "'" + month_count.getText() + "',"
                                                                + "'" + dateAndTime[0] + "',"
                                                                + "'" + dateAndTime[1] + "',"
                                                                + "'" + LOANID + "',"
                                                                + "'" + getUserid() + "',"
                                                                + "'1')";

                                                        int I = jdbc.putData(SQL);

                                                        double Val = amount - last_interest_value;
                                                        double newLoanAmount = last_loan_value - Val;

                                                        String SQL3 = "UPDATE loan_status SET last_interest_amount='0.0',last_loan_amount='" + newLoanAmount + "' WHERE loan_id='" + LOANID + "' AND status='2'";
                                                        int YY2 = jdbc.putData(SQL3);
                                                        if (YY2 == 1) {
                                                            String SQL4 = "UPDATE loan SET status='3' WHERE loan_id='" + LOANID + "'";
                                                            int Y2 = jdbc.putData(SQL4);
                                                            if (Y2 == 1) {
                                                                JOptionPane.showMessageDialog(rootPane, "Loan Was Settled !");
                                                                Y = 1;
                                                            } else {
                                                                resetPayment(G_P_ID, LOANID, last_loan_value, last_interest_value);
                                                            }
                                                        } else {
                                                            resetPayment(G_P_ID, LOANID, last_loan_value, last_interest_value);
                                                        }

                                                    } else if (total_paybel < amount) {

                                                        JOptionPane.showMessageDialog(rootPane, "Cash Value Is Too High !");
                                                        p_amount.setText(total_paybel + "");
                                                        resetPayment(G_P_ID, LOANID, last_loan_value, last_interest_value);

                                                    } else if (last_loan_value > amount) {
                                                        //idloan_payment, genarated_id, amount, cash, 
                                                        //balance, payment_type, month, payed_date, payed_time, loan_id, user_id
                                                        String SQL = "INSERT INTO "
                                                                + "loan_payment(genarated_id, "
                                                                + "amount,"
                                                                + " cash,"
                                                                + " balance,"
                                                                + " payment_type,"
                                                                + " month,"
                                                                + " payed_date,"
                                                                + " payed_time,"
                                                                + " loan_id,"
                                                                + " user_id,"
                                                                + "status) VALUES("
                                                                + "'" + G_P_ID + "',"
                                                                + "'" + amount + "',"
                                                                + "'" + cash + "',"
                                                                + "'" + balance + "',"
                                                                + "'1',"
                                                                + "'" + month_count.getText() + "',"
                                                                + "'" + dateAndTime[0] + "',"
                                                                + "'" + dateAndTime[1] + "',"
                                                                + "'" + LOANID + "',"
                                                                + "'" + getUserid() + "',"
                                                                + "'1')";

                                                        int I = jdbc.putData(SQL);
                                                        double Val = amount - last_interest_value;
                                                        double newLoanAmount = last_loan_value - Val;
                                                        String SQL3 = "UPDATE loan_status SET last_interest_amount='0.0',last_loan_amount='" + newLoanAmount + "' WHERE loan_id='" + LOANID + "' AND status='2'";
                                                        int YY = jdbc.putData(SQL3);
                                                        if (YY == 1) {
                                                            Y = 1;
                                                        } else {
                                                            resetPayment(G_P_ID, LOANID, last_loan_value, last_interest_value);
                                                        }

                                                    }

                                                }

                                            } else {

                                            }

                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(rootPane, "Error With Genarating Payment ID Clear !!  !");
                                        clear();
                                    }
                                } catch (Exception e) {
                                    
                                    e.printStackTrace();
                                }

                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Incorrect Cash Value  !");
                                p_cash.grabFocus();
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Currunt User Is Not Detected Please Log Out And Log In Again !");
                            clear();
                        }

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Please Enter Cash !");
                        p_cash.grabFocus();
                    }

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Please Enter Amount !");
                    p_amount.grabFocus();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please Enter Loane ID !");
                clear();
            }

        } else if (STA.equals("3")) {
            JOptionPane.showMessageDialog(rootPane, "Loan Was Settled !");

        }
        return Y;
    }

    private void clear() {
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        c_name.setText("");
        c_nic.setText("");
        l_amount.setText("");
        month_count.setText("");
        loan_id.setText("");
        l_interest_amount.setText("");
        p_amount.setText("");
        p_balance.setText("");
        p_cash.setText("");
        payable.setText("");
        loan_id.grabFocus();
        LOANID = "";
        G_LONEID = "";
        G_P_ID = "";

    }

    private void inEnterLoanID(String LID) {
        String LoanID = LID;
        G_LONEID = LoanID;
        if (!(LoanID.equals(""))) {
            try {
                String SQL = "SELECT * FROM loan WHERE genarated_id='" + LoanID + "'";
                //loan_id, genarated_id, amount, interest_rate, status, date, time, customer_id, user_id
                ResultSet RS = jdbc.getData(SQL);
                if (RS.next()) {

                    String CID = RS.getString("customer_id");
                    String SQL2 = "SELECT * FROM customer WHERE customer_id='" + CID + "'";
                    //customer_id, custoemer_nic, name_full, name_ini, gender, 
                    //no, street, city, contact1, contact2, email, addedDate, addedTime, 
                    //tatus, specialNote, folderid, user_user_id, imageName
                    ResultSet RS2 = jdbc.getData(SQL2);
                    if (RS2.next()) {
                        c_name.setText(RS2.getString("name_full"));
                        c_nic.setText(RS2.getString("custoemer_nic"));

                        String SQL3 = "SELECT * FROM loan_status WHERE loan_id='" + RS.getString("loan_id") + "' AND status='2'";
                        //idloan_status, last_loan_amount, last_interest_amount, 
                        //month, status, aded_date, aded_time, ex_date, loan_id
                        ResultSet RS3 = jdbc.getData(SQL3);
                        if (RS3.next()) {
                            LOANID = RS.getString("loan_id");
                            String SQL4 = "SELECT * FROM loan_payment WHERE loan_id='" + LOANID + "' AND status='1'";
                            //idloan_payment, genarated_id, amount, cash, balance,
                            //payment_type, month, payed_date, payed_time, loan_id, user_id
                            ResultSet RS4 = jdbc.getData(SQL4);
                            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                            dtm.setRowCount(0);
                            while (RS4.next()) {
                                Vector v = new Vector();
                                v.add(RS4.getString("genarated_id"));
                                v.add(RS4.getString("amount"));
                                v.add(RS4.getString("month"));
                                v.add(RS4.getString("payed_date"));
                                v.add(RS4.getString("payed_time"));
                                String SQL5 = "SELECT name FROM user WHERE user_id='" + RS4.getString("user_id") + "'";
                                ResultSet RS5 = jdbc.getData(SQL5);
                                if (RS5.next()) {
                                    v.add(RS5.getString("name"));
                                }
                                dtm.addRow(v);

                            }

                            l_amount.setText("RS: " + RS3.getString("last_loan_amount"));
                            l_interest_amount.setText("RS: " + RS3.getString("last_interest_amount"));
                            month_count.setText(RS3.getString("month"));
                            double pay = RS3.getDouble("last_loan_amount") + RS3.getDouble("last_interest_amount");
                            payable.setText("RS: " + pay);
                            p_amount.grabFocus();

                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Loan Status Data Not Found Please Try Again !");
                            clear();

                        }

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Customer Data Not Found Please Try Again !");
                        clear();

                    }

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Incorrect Loan ID !");
                    loan_id.grabFocus();
                }
            } catch (Exception e) {
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please Enter Loan ID !");
            loan_id.grabFocus();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        AddCustomer = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        p_amount = new javax.swing.JTextField();
        loan_id = new javax.swing.JTextField();
        p_cash = new javax.swing.JTextField();
        p_balance = new javax.swing.JLabel();
        payable = new javax.swing.JLabel();
        month_count = new javax.swing.JLabel();
        c_nic = new javax.swing.JLabel();
        l_interest_amount = new javax.swing.JLabel();
        c_name = new javax.swing.JLabel();
        l_amount = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        read2 = new javax.swing.JLabel();
        read = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CUSTOMER");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AddCustomer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        AddCustomer.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 606, 106, 49));

        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });
        AddCustomer.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(833, 606, 106, 49));

        p_amount.setBackground(new java.awt.Color(57, 61, 75));
        p_amount.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        p_amount.setForeground(new java.awt.Color(255, 255, 255));
        p_amount.setBorder(null);
        p_amount.setCaretColor(new java.awt.Color(255, 255, 255));
        p_amount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                p_amountKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                p_amountKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p_amountKeyTyped(evt);
            }
        });
        AddCustomer.add(p_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 554, 240, 24));

        loan_id.setBackground(new java.awt.Color(57, 61, 75));
        loan_id.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        loan_id.setForeground(new java.awt.Color(255, 255, 255));
        loan_id.setBorder(null);
        loan_id.setCaretColor(new java.awt.Color(255, 255, 255));
        loan_id.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loan_idMouseClicked(evt);
            }
        });
        loan_id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                loan_idKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                loan_idKeyTyped(evt);
            }
        });
        AddCustomer.add(loan_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 115, 359, 24));

        p_cash.setBackground(new java.awt.Color(57, 61, 75));
        p_cash.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        p_cash.setForeground(new java.awt.Color(255, 255, 255));
        p_cash.setBorder(null);
        p_cash.setCaretColor(new java.awt.Color(255, 255, 255));
        p_cash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                p_cashKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                p_cashKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                p_cashKeyTyped(evt);
            }
        });
        AddCustomer.add(p_cash, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 554, 240, 24));

        p_balance.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        p_balance.setForeground(new java.awt.Color(255, 255, 255));
        p_balance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AddCustomer.add(p_balance, new org.netbeans.lib.awtextra.AbsoluteConstraints(666, 553, 285, 28));

        payable.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        payable.setForeground(new java.awt.Color(255, 255, 255));
        payable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AddCustomer.add(payable, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 258, 285, 28));

        month_count.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        month_count.setForeground(new java.awt.Color(255, 255, 255));
        month_count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AddCustomer.add(month_count, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 198, 285, 28));

        c_nic.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        c_nic.setForeground(new java.awt.Color(255, 255, 255));
        c_nic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AddCustomer.add(c_nic, new org.netbeans.lib.awtextra.AbsoluteConstraints(386, 198, 285, 28));

        l_interest_amount.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        l_interest_amount.setForeground(new java.awt.Color(255, 255, 255));
        l_interest_amount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AddCustomer.add(l_interest_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(386, 258, 285, 28));

        c_name.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        c_name.setForeground(new java.awt.Color(255, 255, 255));
        c_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AddCustomer.add(c_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 198, 285, 28));

        l_amount.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        l_amount.setForeground(new java.awt.Color(255, 255, 255));
        l_amount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AddCustomer.add(l_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 258, 285, 28));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/minimize2.png"))); // NOI18N
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
        AddCustomer.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1021, 10, 20, 20));

        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });
        AddCustomer.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1051, 10, 20, 20));

        read2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        read2.setForeground(new java.awt.Color(255, 255, 255));
        AddCustomer.add(read2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 600, 450, 20));

        read.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        read.setForeground(new java.awt.Color(255, 255, 255));
        AddCustomer.add(read, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 340, 20));

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
        AddCustomer.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1083, 70));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PAYMENT ID", "AMOUNT", "MONTH", "DATE", "TIME", "USER NAME"
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        AddCustomer.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 980, 170));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/newPaymentBack.jpg"))); // NOI18N
        AddCustomer.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(AddCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1083, 665));

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

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        sleep();
        jLabel6.setIcon(null);
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        sleep();
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/img/pay2.jpg")));
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        if (evt.getButton() == 1) {
            if (!UserLogin.userPrivilage.contains(UI_NAME1)) {
                JOptionPane.showMessageDialog(this, "Access Denied");
            } 
            else {
                int Y = pay();
                if (Y == 1) {
                    JOptionPane.showMessageDialog(rootPane, "Payment Record Added !");
                    onLoad(G_LONEID);
                    p_amount.setText("");
                    p_cash.setText("");
                    p_balance.setText("");
                    p_amount.grabFocus();
                    printBill(G_P_ID);
                } else {
                    //JOptionPane.showMessageDialog(rootPane, "Something Went Wronge !");
                }
            }
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked

        clear();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void p_amountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p_amountKeyTyped
        if (evt.getKeyChar() == '.') {
            String QC = p_amount.getText();
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
    }//GEN-LAST:event_p_amountKeyTyped

    private void loan_idKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loan_idKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_loan_idKeyTyped

    private void p_cashKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p_cashKeyTyped
        if (evt.getKeyChar() == '.') {
            String QC = p_cash.getText();
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
    }//GEN-LAST:event_p_cashKeyTyped

    private void loan_idKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loan_idKeyPressed
        if (evt.getKeyCode() == 10) {
            if (!UserLogin.userPrivilage.contains(UI_NAME)) {
                JOptionPane.showMessageDialog(this, "Access Denied");
            } else {
                inEnterLoanID(loan_id.getText());
            }
        }
    }//GEN-LAST:event_loan_idKeyPressed

    private void p_amountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p_amountKeyPressed
        if (evt.getKeyCode() == 10) {
            if (!(LOANID.equals("")) && !(G_LONEID.equals(""))) {
                if (!(p_amount.getText().equals(""))) {
                    p_cash.grabFocus();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Please Enter Amount !");
                    p_amount.grabFocus();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please Enter Loane ID !");
                clear();
            }

        }
    }//GEN-LAST:event_p_amountKeyPressed

    private void p_cashKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p_cashKeyReleased
        if (!(p_cash.getText().equals(""))) {
            double amount = Double.parseDouble(p_amount.getText());
            double cash = Double.parseDouble(p_cash.getText());

            if (amount < cash) {
                double v = cash - amount;
                p_balance.setText("RS: " + v);
            } else {
                p_balance.setText("");
            }

        }
        String DATA = p_cash.getText();
        if (!(DATA.equals(""))) {

            if (Double.parseDouble(DATA) < 990000000) {
                int val = Integer.parseInt(DATA);
                read2.setText(func.convertNumberToWords(val) + "  " + "Rupees Only");
            }

        } else {
            read2.setText("");
        }

    }//GEN-LAST:event_p_cashKeyReleased

    private void p_cashKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p_cashKeyPressed
        if (evt.getKeyCode() == 10) {
            int Y = pay();
            if (Y == 1) {
                JOptionPane.showMessageDialog(rootPane, "Payment Record Added !");
                onLoad(G_LONEID);
                p_amount.setText("");
                p_cash.setText("");
                p_balance.setText("");
                p_amount.grabFocus();
                printBill(G_P_ID);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Something Went Wronge !");
            }
        }
    }//GEN-LAST:event_p_cashKeyPressed

    private void loan_idMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loan_idMouseClicked
        if (evt.getButton() == 1) {
            clear();
        }
    }//GEN-LAST:event_loan_idMouseClicked

    private void p_amountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p_amountKeyReleased
        String DATA = p_amount.getText();
        if (!(DATA.equals(""))) {

            if (Double.parseDouble(DATA) < 990000000) {
                int val = Integer.parseInt(DATA);
                read.setText(func.convertNumberToWords(val) + "  " + "Rupees Only");
            }

        } else {
            read.setText("");
        }
    }//GEN-LAST:event_p_amountKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
       if(evt.getButton()==1 && evt.getClickCount()==2){
           int R=jTable1.getSelectedRow();
           if(R>=0){
               String PID=jTable1.getValueAt(R, 0).toString();
               printBill(PID);
               
           }
       }
    }//GEN-LAST:event_jTable1MouseClicked
    int ml = 20;

    public void sleep() {
        try {
            Thread.sleep(ml);

        } catch (InterruptedException ex) {
            Logger.getLogger(NewPayment.class
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
            java.util.logging.Logger.getLogger(NewPayment.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewPayment.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewPayment.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewPayment.class
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewPayment("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddCustomer;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel c_name;
    private javax.swing.JLabel c_nic;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel l_amount;
    private javax.swing.JLabel l_interest_amount;
    private javax.swing.JTextField loan_id;
    private javax.swing.JLabel month_count;
    private javax.swing.JTextField p_amount;
    private javax.swing.JLabel p_balance;
    private javax.swing.JTextField p_cash;
    private javax.swing.JLabel payable;
    private javax.swing.JLabel read;
    private javax.swing.JLabel read2;
    // End of variables declaration//GEN-END:variables
}
