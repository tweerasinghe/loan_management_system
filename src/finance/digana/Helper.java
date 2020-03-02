/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance.digana;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.AboutUS;
import main.UserLogin;

/**
 *
 * @author Teran Weerasinghe
 */
public class Helper {

    public static String getDateAndTime(String type) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat datetime = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        String response = null;

        if (type.equalsIgnoreCase("date")) {
            response = dateFormat.format(cal.getTime());
        } else {
            response = datetime.format(cal.getTime());
        }

        return response;

    }

    public static void validationTextfield(KeyEvent evt, String type) {
        if (type.equalsIgnoreCase("text")) {
//            if (!Character.isLetter(evt.getKeyChar())) {
//                evt.consume();
//            }
        } else if (type.equalsIgnoreCase("number")) {
            if (!Character.isDigit(evt.getKeyChar())) {
                evt.consume();
            }
        } else if (type.equalsIgnoreCase("initial")) {
            if (evt.getKeyChar() != '.') {
                evt.consume();
            } else if (Character.isDigit(evt.getKeyChar())) {
                evt.consume();
            }
        }
    }
    public static boolean PrivillageManager(String name,JFrame instance,Component component){
       
        
         boolean status = UserLogin.userPrivilage.contains(name);
        
         if(!status){
             JOptionPane.showMessageDialog(component, "Access Denied");
         }
         else{
             instance.setVisible(true);
        }
         
         return status;
    } 
}
