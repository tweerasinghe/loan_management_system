package finance.digana;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import javax.swing.UIManager;
import main.UserLogin;


public class Runner {

    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
            new UserLogin().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
