/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance.digana;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

/**
 *
 * @author Tekka
 */
public class JDBC {

    private static JDBC instance;

    public JDBC() {
    }

    public static synchronized JDBC getInstance() {
        if (instance == null) {
            instance = new JDBC();
        }
        return instance;
    }

    public Connection con() throws Exception {
        //---------------------------------------
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //-------------------------------
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://"+prop.getProperty("dbhost")+":"+prop.getProperty("dbport")+"/"+prop.getProperty("database"), prop.getProperty("dbuser"), prop.getProperty("dbpassword"));
        return c;
    }

    public int putData(String sql) throws Exception {
        return con().createStatement().executeUpdate(sql);
    }

    public ResultSet getData(String sql) throws Exception {
        return con().createStatement().executeQuery(sql);
    }
}
