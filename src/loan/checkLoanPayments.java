/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loan;

import finance.digana.JDBC;
import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 *
 * @author Teran Weerasinghe
 */
public class checkLoanPayments {

    JDBC jdbc = new JDBC();
    loan_function func = new loan_function();

    public void mainRun() throws Exception {

        String SQL = "SELECT * FROM loan_status WHERE status='2' ";
        ResultSet RS = jdbc.getData(SQL);
        while (RS.next()) {
            //idloan_status, last_loan_amount, last_interest_amount, month, status, aded_date, aded_time, ex_date, loan_id
            String NowDate
                    = func.getDateAndTime()[0];
            String ExDate = RS.getString("ex_date");
            String id = RS.getString("loan_id");
            if (ExDate.equals(NowDate)) {
                double last_loan_value=RS.getDouble("last_loan_amount");
                String load_id = RS.getString("loan_id");
                String SQL4 = "SELECT * FROM loan WHERE loan_id='" + load_id + "' ";
                ResultSet RS4 = jdbc.getData(SQL4);

                if (RS4.next()) {
                    double loan = RS4.getDouble("amount");
                    if (loan == last_loan_value || loan < last_loan_value) {

                        ResultSet RS2 = jdbc.getData("SELECT interest_rate FROM loan WHERE loan_id='" + id + "'");
                        if (RS2.next()) {
                            BigDecimal rate = new BigDecimal(RS2.getDouble("interest_rate"));
                            BigDecimal HUNDRED = new BigDecimal("100");
                            BigDecimal last_loan_amount = new BigDecimal(RS.getDouble("last_loan_amount"));
                            BigDecimal last_loan_interest = new BigDecimal(RS.getDouble("last_interest_amount"));
                            
                            BigDecimal new_loan_amount = last_loan_amount.add(last_loan_interest);
                            
                            BigDecimal LOAN=new BigDecimal(loan);
                            
                            BigDecimal res = LOAN.multiply(rate);
                            BigDecimal new_lone_interest = res.divide(HUNDRED, BigDecimal.ROUND_HALF_EVEN);
                            int newmonth = RS.getInt("month") + 1;
                            String SQL2 = "INSERT INTO "
                                    + "loan_status("
                                    + "last_loan_amount,"
                                    + " last_interest_amount, "
                                    + "month, "
                                    + "status, "
                                    + "aded_date, "
                                    + "aded_time, "
                                    + "ex_date, "
                                    + "loan_id)"
                                    + "VALUES"
                                    + "('" + new_loan_amount.doubleValue() + "',"
                                    + "'" + new_lone_interest.doubleValue() + "',"
                                    + "'" + newmonth + "',"
                                    + "'2',"
                                    + "'" + func.getDateAndTime()[0] + "',"
                                    + "'" + func.getDateAndTime()[1] + "',"
                                    + "'" + func.getDateAndTime()[2] + "','" + id + "')";

                            int y = jdbc.putData(SQL2);
                            if (y == 1) {

                                String SQL3 = "UPDATE loan_status SET status='1' WHERE idloan_status='" + RS.getString("idloan_status") + "'";
                                jdbc.putData(SQL3);

                            }
                        }
                    }

                    if (loan > last_loan_value) {
                        ResultSet RS2 = jdbc.getData("SELECT interest_rate FROM loan WHERE loan_id='" + id + "'");
                        if (RS2.next()) {
                            BigDecimal rate = new BigDecimal(RS2.getDouble("interest_rate"));
                            BigDecimal HUNDRED = new BigDecimal("100");
                            BigDecimal last_loan_amount = new BigDecimal(RS.getDouble("last_loan_amount"));
                            BigDecimal last_loan_interest = new BigDecimal(RS.getDouble("last_interest_amount"));

                            BigDecimal new_loan_amount = last_loan_amount.add(last_loan_interest);

                            BigDecimal res = new_loan_amount.multiply(rate);
                            BigDecimal new_lone_interest = res.divide(HUNDRED, BigDecimal.ROUND_HALF_EVEN);
                            int newmonth = RS.getInt("month") + 1;
                            String SQL2 = "INSERT INTO "
                                    + "loan_status("
                                    + "last_loan_amount,"
                                    + " last_interest_amount, "
                                    + "month, "
                                    + "status, "
                                    + "aded_date, "
                                    + "aded_time, "
                                    + "ex_date, "
                                    + "loan_id)"
                                    + "VALUES"
                                    + "('" + new_loan_amount.doubleValue() + "',"
                                    + "'" + new_lone_interest.doubleValue() + "',"
                                    + "'" + newmonth + "',"
                                    + "'2',"
                                    + "'" + func.getDateAndTime()[0] + "',"
                                    + "'" + func.getDateAndTime()[1] + "',"
                                    + "'" + func.getDateAndTime()[2] + "','" + id + "')";

                            int y = jdbc.putData(SQL2);
                            if (y == 1) {

                                String SQL3 = "UPDATE loan_status SET status='1' WHERE idloan_status='" + RS.getString("idloan_status") + "'";
                                jdbc.putData(SQL3);

                            }
                        }
                    }

                }

            }

        }
    }

    public static void main(String[] args) {
        try {
            new checkLoanPayments().mainRun();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
