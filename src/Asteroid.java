package Asteroids;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 28/05/13
 * Time: 16:26
 */
public class Asteroid extends GameObject {
    // Asteroid class instance variables

    Image im = Constants.ASTEROID1;
    public static int astCount;
    public int SCALE = 2;
    public static int astStage = 3;
   // private double LargeRADIUS = 25.0;
    private final static int RADIUS = 10;
  //  private final static int SmallRADIUS = 6;
    public static int hitsToTake = 3, splitAst = 2;
    public static double radius = 25.0;
    private int[] py;
    private int[] px;
    private int nPoints;
    int MIN_POINTS = 10;
    int MAX_POINTS = 25;
    int MIN_RADIUS = 15;
    int MAX_RADIUS = 25;
    int MAX_ROTATION = 0;
    double rotation = 0;
    int direction;



    // Asteroid constructor
    public Asteroid(Vector2D S, Vector2D vV, double LargeRADIUS, int splitAst, int hitsToTake){//Vector2D s, Vector2D v){
        this.s = new Vector2D();
        this.v = new Vector2D();
        s = S;
        v = vV;
        radius = LargeRADIUS;
        hitsToTake = 3;
      //  splitAst = splitAst;
        splitAst = 2;
        astStage = 3;

        //hitsToTake = 3;


       // this.astStage = astStage;
        //int n = game.remainingAsteroids();
        astCount = Constants.astCleared;

        //this.s = s.set(s.x, s.y);
        //this.v = v.set(v.x, v.y);
        //this.hitsToTake = hitsToTake;
        //this.splitAst = splitAst;
    }
    public int getAstCount(){
        return astCount;
    }


    public int getSplitAst(){
        return splitAst;

    }

    public int getHitsToTake(){
        return hitsToTake;
    }


    // Asteroid update method
    public void update(){
        Constants.setDelay += 0.1f;
        s = s.add(v, Constants.DT);    //this ? quicker

        s.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

        //direction += rotation ++;

        setPolygon();
        direction += rotation ;




        //d.rotate(rotation);
    }

    // responds to Asteroid being hit and sets the GameObject
    // to dead
    void hit(GameObject object) {
        if(Constants.currentSpawnLimit >30)
            Constants.astHit = true;

            dead = true;
        //if(currentSpawnLimit >30 ) //&& saucerCount >50

           //hitsToTake-= 1;
           Constants.astHitsToTake -= 1;
        //explode();
        //dead = true;
           if(getHitsToTake() > 0 || Constants.astHitsToTake > 0){
               System.out.println(getHitsToTake());

             //  System.out.println(game.getGameObjects());
               //for(GameObject otherObject : game.getGameObjects())

                /*if(otherObject instanceof Asteroid) {
                    createAsteroidSplit(otherObject);
                    System.out.println("o" + object.getClass());
                //}else if(otherObject instanceof Asteroid && object instanceof Bullet || object instanceof Ship){
                  //  createAsteroidSplit(otherObject);
                }   */
               }      //  split = true;

            else{
               dead = true;
               }
           }


    // returns Asteroid RADIUS
    double radius() {
        return radius;
    }


    boolean intersects(GameObject other) {
        return !(other.s.x >= s.x+radius() || other.s.x + other.radius() <= s.x
                || other.s.y >= s.y+radius() || other.s.y + other.radius() <= s.y);
    }

    Rectangle2D getBounds2D() {
        return new Rectangle((int)s.x,(int) s.y, 2 * RADIUS, 2 * RADIUS);
    }

    public void setPolygon() {

            nPoints = MIN_POINTS + (int)(Math.random() * (MAX_POINTS - MIN_POINTS));
            px = new int[nPoints];
            py = new int[nPoints];

            rotation = (int)(Math.random()-0.5) * 2 * MAX_ROTATION;
            for (int i = 0; i < nPoints; i++) {
            // generate vertices within certain ranges in polar coordinates
            // then transform to cartesian
                double theta = (Math.PI * 2 / nPoints) * (i + Math.random());
                double radius = MIN_RADIUS + Math.random() * (MAX_RADIUS - MIN_RADIUS);
                px[i] = (int) (radius * Math.cos(theta));
                py[i] = (int) (radius * Math.sin(theta));

        }
    }

    public void draw(Graphics2D g){

        // store coordinate system

        AffineTransform at = g.getTransform();

        g.setColor(Color.white);
        g.translate(s.x, s.y);
        g.rotate(direction);
        g.drawPolygon(px, py, nPoints);
        // restore original coordinate system
        g.setTransform(at);

        // can switch between jagged asteroids to an image of the asteroids
        //g.drawImage(im, x, y, null);

        //g.translate(s.x, s.y);
        //g.setColor(Color.red);
       // int x = (int) s.x;
       // int y = (int) s.y;
        //g.fillOval(x - (int)radius(), y -(int) radius(), 2*(int) radius(), 2* (int)radius());
        }

       // g.drawImage(im, x, y, (int)LargeRADIUS, (int)LargeRADIUS, null, null);

        }



