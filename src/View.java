package Asteroids;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created with IntelliJ IDEA.
 * User: CC #CAKEUP
 * Date: 16/06/13
 * Time: 00:04
 * To change this template use File | Settings | File Templates.
 */
public class View extends JComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8103486428275849008L;
	Game game;
    Image im = Constants.MILKYWAY1;
    AffineTransform bgTransf;

    public View(Game game){
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 : Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 : Constants.FRAME_WIDTH/imHeight);
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);

    }

    @Override
    public void paintComponent(Graphics g0){
        Graphics g = (Graphics2D) g0;
        //AffineTransform t0 = g.getTransform();
        g.setColor(Constants.BG_COLOR);
        g.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
       // center = game.getShipLocation();
        //g.translate(FRAME_WIDTH / 2 - );
       // g.drawImage(im, bgTransf, null)
    }
}
