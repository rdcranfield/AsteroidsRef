package Asteroids;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 31/05/13
 * Time: 17:15
 */

public abstract class GameObject {
    // Abstract class instance variables (inherited by subclasses)

    Game game;
    Vector2D s;
    Vector2D v;
    public int playerId;
    public boolean dead;
    public boolean isTarget;
    public int shipId;

    // empty constructor
    public GameObject(){}

    // Constructor with parameter arguments
    public GameObject(Game game, Vector2D s, Vector2D v){
        this.game = game;
        this.s = new Vector2D();
        this.v = new Vector2D();
        this.s = s;
        this.v = v;
        //this.ctrl = ctrl;

    }

    // generalised hit method
    void hit(GameObject object){
        //isTarget = true;
        dead = true;
    }

    void hit(GameObject object, GameObject otherObject){
        dead = true;
    }

    // abstract radius method
    abstract double radius() ;
    // abstract getBounds method
    abstract Rectangle2D getBounds2D();

    // GameObject dist method returns double for
    // distance between GameObject and other GameObject
    // used by GameAI classes (SeekNShoot, AimNShoot etc)
    public double dist(GameObject other){
       double x = Math.abs(this.s.x-other.s.x);
       double y = Math.abs(this.s.y-other.s.y);
       return Math.hypot(x,y);

   }

    // to method returns vector of other objects position, minus
    // current objects position and returns the new position
    // used by GameAI classes
    public Vector2D to(GameObject other){
        return new Vector2D(other.s.x - s.x, other.s.y - s.y);
    }

    // abstract boolean intersects method
    abstract boolean intersects(GameObject other);

    //update method
    void update(){}

    // abstract draw method
    abstract void draw(Graphics2D g);

    public void explode(){}

    //public String toString(){}
}

