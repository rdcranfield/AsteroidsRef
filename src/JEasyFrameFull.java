package Asteroids;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class JEasyFrameFull extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7979647285856080964L;
	public final static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final static GraphicsDevice device = env.getScreenDevices()[0];
    public static final Rectangle RECTANGLE = device.getDefaultConfiguration().getBounds();
    public static final int WIDTH = RECTANGLE.width;
    public static final int HEIGHT = RECTANGLE.height;

    public Component comp;

    public JEasyFrameFull(Component comp) {
        super();
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp);
        comp.setPreferredSize(new Dimension (WIDTH, HEIGHT));
        this.setUndecorated(true);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }

    public void escapeHandler(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("escaped !");
        } else {
            System.out.println("Not escaped !");
        }
    }
}