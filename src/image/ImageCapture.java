package image;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;
import customer.Customer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ImageCapture extends JFrame implements Runnable, WebcamListener, WindowListener, UncaughtExceptionHandler, ItemListener, WebcamDiscoveryListener {

    private static final long serialVersionUID = 1L;

    private Webcam webcam = null;
    private WebcamPanel panel = null;
    private WebcamPicker picker = null;

    @Override
    public void run() {

        Webcam.addDiscoveryListener(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/camera.png")));
        setTitle("CUSTOMER IMAGE");
        //setUndecorated(true);
        //setResizable(false);
        setLayout(new BorderLayout());

        setSize(500, 500);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        addWindowListener(this);

        picker = new WebcamPicker();
        picker.addItemListener(this);

        /*
        
        
         */
//        Dimension[] nonStandardResolutions = new Dimension[]{WebcamResolution.VGA.getSize(),
//            WebcamResolution.VGA.getSize(),
//            new Dimension(100, 100),
//            new Dimension(100, 100),};
        webcam = picker.getSelectedWebcam();

        if (webcam == null) {
            JOptionPane.showMessageDialog(this, "No Camera Found", "Info", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }

        // webcam.setCustomViewSizes(nonStandardResolutions);
        webcam.setViewSize(new Dimension(176, 144));

        webcam.addWebcamListener(ImageCapture.this);

        panel = new WebcamPanel(webcam, false);
        panel.setFPSDisplayed(false);

        add(picker, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        JButton b = new JButton(new AbstractAction("Take Snapshot Now") {

            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = String.format("test-%d.jpg", System.currentTimeMillis());
                    ImageIO.write(webcam.getImage(), "JPG", new File(name));

                    JOptionPane.showMessageDialog(ImageCapture.this, "SAVED", "Info", JOptionPane.INFORMATION_MESSAGE);

                    String path = null;

                    try {
                        path = new File(".").getCanonicalPath();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    String fullName = path + "\\" + name;
                    Customer.customerImage.setIcon(new ImageIcon(fullName));
                    Customer.imageName = name;
                    dispose();

                } catch (IOException t) {
                    t.printStackTrace();
                }
            }
        });
        b.setText("Capture Image");

        add(b, BorderLayout.SOUTH);

        try {

            setVisible(true);

        } catch (Exception e) {
        }

        Thread t = new Thread() {

            @Override
            public void run() {
                panel.start();
            }
        };
        t.setName("example-starter");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ImageCapture());
    }

    @Override
    public void webcamOpen(WebcamEvent we) {
        System.out.println("webcam open");
    }
    private void action(){
    
    }
    @Override
    public void webcamClosed(WebcamEvent we) {
        System.out.println("webcam closed");
    }

    @Override
    public void webcamDisposed(WebcamEvent we) {
        System.out.println("webcam disposed");
    }

    @Override
    public void webcamImageObtained(WebcamEvent we) {
        //System.out.println("A");
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        webcam.close();
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("webcam viewer resumed");
        panel.resume();
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("webcam viewer paused");
        panel.pause();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(String.format("Exception in thread %s", t.getName()));
        e.printStackTrace();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() != webcam) {
            if (webcam != null) {

                panel.stop();

                remove(panel);

                webcam.removeWebcamListener(this);
                webcam.close();

                webcam = (Webcam) e.getItem();
                webcam.setViewSize(WebcamResolution.VGA.getSize());
                webcam.addWebcamListener(this);

                System.out.println("selected " + webcam.getName());

                panel = new WebcamPanel(webcam, false);
                panel.setFPSDisplayed(false);

                add(panel, BorderLayout.CENTER);
                pack();

                Thread t = new Thread() {

                    @Override
                    public void run() {
                        panel.start();
                    }
                };
                t.setName("example-stoper");
                t.setDaemon(true);
                t.setUncaughtExceptionHandler(this);
                t.start();
            }
        }
    }

    @Override
    public void webcamFound(WebcamDiscoveryEvent event) {
        if (picker != null) {
            picker.addItem(event.getWebcam());
        }
    }

    @Override
    public void webcamGone(WebcamDiscoveryEvent event) {
        if (picker != null) {
            picker.removeItem(event.getWebcam());
        }
    }
}
