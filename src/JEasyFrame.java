package Asteroids;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: CC #CAKEUP
 * Date: 28/05/13
 * Time: 16:06
 * To change this template use File | Settings | File Templates.
 */
public class JEasyFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4726403615998949058L;
	public Component comp;
    public JEasyFrame(Component comp, String title){
        super(title);
        this.comp = comp;
        setResizable(true);
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
}
