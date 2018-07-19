package Asteroids;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 13/06/13
 * Time: 23:39
 */
public class Bullet extends GameObject implements Serializable {
    // Bullet class instance variables

    /**
	 * 
	 */
	private static final long serialVersionUID = 3930712842682830503L;
	private final static int RADIUS = 2;
    static final Color COLOR = Color.yellow;
   // private Color color;

    // Bullet constructor
    public Bullet(Vector2D S, Vector2D bV, int pn, Color color){
        Constants.currentBulletLife = 0f;
        Constants.currentBulletLife2 = 0f;
        this.s = new Vector2D();
        this.v = new Vector2D();

        // will keep track of playerId of bullet created in each class,
        // via Constant variables Spn = 1, Epn = 0 : playerid = 1, saucerId = 0
        playerId = pn;
  //      this.color = color;
        s = S;
        v = bV;

    }

    // Bullet update method
    public void update(){
        Constants.currentBulletLife += 0.1f;
        //Constants.currentBulletLife2 += 0.1f;
        //Constants.currentPBulletLife += 0.1f;
        this.s = this.s.add(this.v, Constants.DT/ Constants.BMAX);
        this.s = this.s.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

        if (Constants.bulletLife <= Constants.currentBulletLife)
            dead = true;
        if(Constants.bulletLife2 <= Constants.currentBulletLife2){
            dead = true;
        }
        if(Constants.PBulletLife <= Constants.currentPBulletLife){
            dead = true;
        }

    }

    // bullet dead if it hits another GameObject
    void hit(GameObject object) {
        this.dead = true;

    }

    // return Bullet RADIUS
    double radius() {
        return RADIUS;
       // return radius();
    }

    // gets bounds of Bullet
    Rectangle2D getBounds2D() {

        return null;
    }

    // checks intersection between Bullet and other Object  (NOT NEEDED OR USED)
    boolean intersects(GameObject other) {
        return !(other.s.x >= s.x+radius() || other.s.x + other.radius() <= s.x
                || other.s.y >= s.y+radius() || other.s.y + other.radius() <= s.y);
    }

    // draw method, draws the Bullet
    void draw(Graphics2D g) {
            g.setColor(Color.yellow);
            int x = (int) s.x;
            int y = (int) s.y;
            g.fillOval(x - RADIUS, y - RADIUS, 2* RADIUS, 2*RADIUS);
        if(Constants.PBull){
            g.setColor(Color.red);
            g.fillOval(x - RADIUS, y - RADIUS, 2* RADIUS, 2*RADIUS);

        }

    }
}
