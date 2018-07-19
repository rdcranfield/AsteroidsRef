package Asteroids;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: CC #CAKEUP
 * Date: 16/06/13
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class Sprite extends GameObject{

    Image image;
    double width, height, direction;

    public double radius(){
        return (width + height)/4.0;
    }

   /* void draw(Graphics g){
        double imW = image.getWidth(null);
        double imH = image.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.rotate(direction, 0, 0);
        t.scale(width/imW, height/imH);
        t.translate(-imW/2.0, -imH/2.0);
        AffineTransform(-imW/2.0, -imH/2.0);
        AffineTransform(s.x, s.y);
        g.drawImage(image, t, null);
        g.setTransform(t0);


    }    */

}
