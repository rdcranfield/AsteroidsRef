package Asteroids;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 18/06/13
 * Time: 21:04
 */

public class Saucer extends GameObject {
    // Saucer class instance variables

    static final int RADIUS = 8;
    public static int EshipCount;
    private static final Color COLOR_BODY = Color.GREEN;
    private static final Color COLOR_BELT = Color.WHITE;
    private static final int HEIGHT = 16;
    private static final int WIDTH = 24;
    private static final int WIDTH_ELLIPSE = 25;
    static final double STEER_RATE2 = 5 * Math.PI; // in radians per sec
    static final double LOSS = 0.99;
    public int EshipHitsToTake = 4;
    public static int pn;
    //Vector2D s, v;
    // direction
    Vector2D d;

    // Controller instance variable
    Controller ctrl;

    // Saucer constructor
    public Saucer(Game game, Controller ctrl, int EshipHitsToTake){
        this.game = game;
        this.ctrl = ctrl;
        this.EshipHitsToTake = EshipHitsToTake;
        EshipCount = 1;
        playerId = 2;
        playerId = pn;

        this.s = new Vector2D();
        this.v = new Vector2D();
        this.d = new Vector2D();
        //dead = false;
        reset();

    }

    // Saucer update method
    public void update(Action action){
        //dead = false;
         if(!dead)
            //Constants.currentSpawnLimitSaucer += 0.1f;
            //saucerCount +=0.1f;
        //if(saucerCount > 50)
            //super.update();
            Constants.SHOT_DELAY2 ++;
            action = ctrl.action(game);
//        action = game.ctrl.action(game);//
           // Action action = ctrl.action(game); //(game);//(game);
            d.rotate(action.turn * STEER_RATE2 * Constants.DT);
            v = v.add(this.d, Constants.RANDOM.nextFloat());
            v = v.mult(LOSS);
            s = s.add(v, Constants.DT);
            s = s.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
            Constants.thrusting = (action.thrust == 1);



            if(Constants.SHOT_DELAY2  >= Constants.SHOT_READY2){
            if(action.shoot && !dead){
                //playerId = Saucer.getPn();
                shootBullet();
                Constants.SHOT_DELAY2 = 0;
            }
            }else{
                Constants.currentBulletLife2 += 0.1f;
        }

    }

    // initial position for Saucer
    public void reset(){
            this.d = d.set(0, -1);
            this.v = v.set(0, 0);
            this.s = s.set(Constants.FRAME_WIDTH / Constants.RANDOM.nextGaussian(), Constants.FRAME_HEIGHT /Constants.RANDOM.nextGaussian());
    }

    // allows Saucer to shoot bullets at a specific speed and with a certain
    // lifetime, with a fire sound invoked
    private void shootBullet(){
       // Constants.SaucerBullet = true;
        Constants.PBull = false;
        Vector2D bV = new Vector2D(v);
        bV = bV.add(d, Constants.SHIP_VMAX);
        Bullet b = new Bullet(new Vector2D(s), bV, Constants.Epn, Color.red);
        b.s = b.s.add(b.v, (RADIUS + b.radius()) * 1.5 / b.v.mag());
        game.add(b);
        SoundManager.play(SoundManager.fire);
    }

    // returns Saucers RADIUS

    double radius() {
        return RADIUS;
        //return 0.0;

    }

    public static int getEshipCount(){
        return EshipCount;
    }

    // responds to Saucer being hit and sets the GameObject
    // to dead
    void hit(GameObject object) {
        //if(currentSpawnLimitSaucer > 50){
            this.dead = true;
            EshipCount -=1;
            //saucerCount = 0;
        //}else
          //  dead = false;
    }

    // gets bounds of Saucer
    Rectangle2D getBounds2D() {
        return new Rectangle((int)s.x,(int) s.y, 2 * RADIUS, 2 * RADIUS);
    }

    // checks intersection between Ship and other Object
    boolean intersects(GameObject other) {
        return !(other.s.x >= s.x+radius() || other.s.x + other.radius() <= s.x
               || other.s.y >= s.y+radius() || other.s.y + other.radius() <= s.y);
    }

    // draw method, draws the Saucer
    @Override
    void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(s.x, s.y);
        Ellipse2D eclipse = new Ellipse2D.Double(-WIDTH_ELLIPSE/2.0,-HEIGHT/2.0, WIDTH_ELLIPSE, HEIGHT);
        g.setColor(COLOR_BODY);
        g.fill(eclipse);
        g.setColor(COLOR_BELT);
        g.drawLine(-WIDTH/2, 0, WIDTH/2, 0) ;
        g.setTransform(at);
    }
}

