package Asteroids;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 16/06/13
 * Time: 21:43
 */

public class Particle extends GameObject {
    // Particle class instance variables

    private static final int RADIUS = 1;
    private static final Color COLOR = Color.red;
    double ttl;

    // Particle constructor
    public Particle(Game game, Vector2D s, double ttl){

        // call to super (GameObject)
        super(game, new Vector2D(s), new Vector2D(Constants.RANDOM.nextGaussian(), Constants.RANDOM.nextGaussian()));
        this.ttl = ttl;
    }

    // Particle update method
    @Override
    public void update(){
        s.add(v);
        v.mult(0.99);
        ttl--;
        if(ttl < 0) dead = true;
    }

    // returns Particles RADIUS
    double radius() {
        return RADIUS;
    }

    // gets bounds of Particle
    Rectangle2D getBounds2D() {
        return new Rectangle((int)s.x,(int) s.y, 2 * RADIUS, 2 * RADIUS);
    }

    // checks intersection between Particle and other Object  (NOT NEEDED OR USED)
    boolean intersects(GameObject other) {
        return !(other.s.x >= s.x+radius() || other.s.x + other.radius() <= s.x
                || other.s.y >= s.y+radius() || other.s.y + other.radius() <= s.y);
    }

    // draw method, draws the Particle
    @Override
    void draw(Graphics2D g) {
        int x = (int)s.x;
        int y = (int)s.y;
        g.setColor(COLOR);
        g.fillOval(x- RADIUS, y - RADIUS,
                2 * RADIUS, 2 * RADIUS);
        /*if(ttl<=100){
            g.setColor(Color.orange);
        } */
    }
}
