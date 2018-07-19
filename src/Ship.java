package Asteroids;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import static Asteroids.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 04/06/13
 * Time: 21:01
 */
public class Ship extends GameObject implements Serializable {
    // Ship class instance variables

    /**
	 * 
	 */
	private static final long serialVersionUID = -8631905979976286646L;
	boolean pendingShot, fleeing;
    Color color;
    long timeLastShot;
    int numVer;
    boolean gameOver;
    //public int currentSpawnTime;
    int[] XP = {0, -3, 0, 3};
    int[] YP = {0, 2, -5, 2};
    int[] XPTHRUST = {0, 0, 0};
    int[] YPTHRUST = {1, 3, 5};
    Controller ctrl;
    static final int RADIUS = 8, SCALE = 4, SHOOTING_DELAY = 500;
    static final double STEER_RATE = 2 * Math.PI; // in radians per sec
    static final double LOSS = 0.99;
    public static int pn;


    static final Color COLOR = Color.cyan;

    AsteroidsAIGame game;

    // direction in which ship is turning
    // this is a "unit" vector (so magnitude is 1)
    Vector2D d;

    //private boolean safeRespawn;

    // Ship constructor
    public Ship(AsteroidsAIGame game,  Controller ctrl, Color color, int shipId){
        this.ctrl = ctrl;
        this.color = color;
        this.s = new Vector2D();
        this.v = new Vector2D();
        this.d = new Vector2D();
        this.shipId = shipId;

        this.game = game;
        reset();
    }

    // initial position for Ship
    public void reset(){
        currentSpawnLimit = 0;
        if(game.getLife() > 0)  //&& safeRespawn
            //System.out.println("getLife" + game.getLife() + "life" +LIFE);
            this.d = d.set(0, -1);
            this.v = v.set(0, 0);
            this.s = s.set(FRAME_WIDTH / 2, FRAME_HEIGHT /2);

    }

    // return RADIUS
    double radius() {
        return RADIUS;
    }
    /*@Override
    public double dist(GameObject other){
        other.getClass();
        double xDiff = s.x - other.s.x;
        double yDiff = s.y - other.s.y;
        double distance = xDiff * xDiff + yDiff * yDiff;
        double tot = RADIUS * other.radius();
        double totR = tot * tot;

        return distance;
    }
         */

    // Ship update method
    //@Override
    public void update(Action action){
        currentSpawnLimit += 0.1f;
        SHOT_DELAY ++;
        PSHOT_DELAY ++;
       // action = game.ctrl.action(game)
//        action = game.ctrl.action(game);//
        action = ctrl.action(game); //(game);//(game);
        d.rotate(action.turn * STEER_RATE * DT);
        v = v.add(d, action.thrust * SHIP_VMAX * DT);
        v = v.mult(LOSS);
        s = s.add(v, DT);
        s = s.wrap(FRAME_WIDTH, FRAME_HEIGHT);
        thrusting = (action.thrust == 1);
        if(thrusting)
            SoundManager.startThrust();
        if(!thrusting) SoundManager.stopThrust();
            if(action.shoot){
               // playerId = getPn();
                shootBullet();

            }
            if(action.powershoot){
                //playerId = getPn();
                shootPBullet();
                //PSHOT_DELAY = 0;
            }

    }


    @Override
    public double dist(GameObject other){
        double x = Math.abs(this.s.x-other.s.x);
        double y = Math.abs(this.s.y-other.s.y);
        return Math.hypot(x,y);

    }

    // responds to Ship being hit by decrementing players life
    // and setting ship to dead when lives are used up
    @Override
    void hit(GameObject object) {
        if(currentSpawnLimit > 30){
            if(game.getLife()>0){
               // LIFE -= 1;
            }else if(game.getLife() == 0){
                dead = true;
            }

        }
    }


    // allows players Ship to shoot bullets at a specific speed and with a certain
    // lifetime, with a fire sound invoked
    private void shootBullet(){
        //playerId = 1;
        if(SHOT_DELAY  >= SHOT_READY){
        Vector2D bV = new Vector2D(v);
        bV = bV.add(d, SHIP_VMAX);
        Bullet b = new Bullet(new Vector2D(s), bV, Spn, Bullet.COLOR);
        b.s = b.s.add(b.v, (RADIUS + b.radius()) * 1.5 / b.v.mag());
        PBull = false;
        game.add(b);
        SoundManager.fire();
        SHOT_DELAY = 0;
        }else{
            currentBulletLife += 0.1f;
        }
    }
    private void shootPBullet(){
        //playerId = 1;
        if(PSHOT_DELAY >= PSHOT_READY){

        Vector2D bpV = new Vector2D(v);
        bpV = bpV.add(d, SHIP_VMAX * 3);
        Bullet b = new Bullet(new Vector2D(s), bpV, Spn, Bullet.COLOR);
        b.s = b.s.add(b.v, (RADIUS + b.radius()) * 1.5 / b.v.mag());
        PBull = true;
        game.add(b);
        SoundManager.fire();
        PSHOT_DELAY = 0;
        }else{
            currentPBulletLife += 0.1f;
    }
    }


    // draw method, draws the Ship
    public void draw(Graphics2D g) {


        AffineTransform at = g.getTransform();
        g.translate(s.x, s.y);
        double rot = d.theta() + Math.PI / 2;
        g.rotate(rot);
        g.scale(SCALE, SCALE);
        g.setColor(COLOR);
        g.drawPolygon(XP, YP, XP.length);
        //g.fillPolygon(XP, YP, XP.length);
        if (thrusting) {
            g.setColor(Color.red);
            for(int i = 0; i<3; i++){
                g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
            }
        }
        g.setTransform(at);
    }

    // gets bounds of Ship
    public Rectangle2D getBounds2D(){
        return new Rectangle((int)s.x,(int) s.y, 2 * RADIUS, 2 * RADIUS);
    }

    // checks intersection between Ship and other Object
    public boolean intersects(GameObject other){
        return !(other.s.x >= s.x+radius() || other.s.x + other.radius() <= s.x
        || other.s.y >= s.y+radius() || other.s.y + other.radius() <= s.y);
    }

}