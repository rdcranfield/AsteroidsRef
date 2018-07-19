package Asteroids;

import javax.swing.*;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 18/06/13
 * Time: 17:00
 */

public class AsteroidsAIGame implements Game {
	
	 //main method
    public static void main(String[] args) {
        /* AsteroidGame result = null;
        // attempt to implement load and save function into game, commented out as it created
        // errors
    try{
        File file = new File(Constants.SAVE_FILE);
        SoundManager.play(SoundManager.thrust);
        ObjectInputStream in = new ObjectInputStream (new FileInputStream(file));
        result = (AsteroidGame)(in.readObject());
        GameView view = new GameView(result);
        result.view = view;
        BasicKeys ctrl = new BasicKeys();
        result.ctrl = ctrl;
        JFrame j = (fullscreenmode ? new JEasyFrameFull(view) :  new JEasyFrame(view, "Asteroid Game"));
        j.addKeyListener(ctrl);
        file.delete();




    }catch(Exception e){
        e.printStackTrace();
    }    */


        // AsteroidsAIGame game instance created
        AsteroidsAIGame game = new AsteroidsAIGame(Constants.fullscreenmode);

        // GameView view instance created
        GameView view = new GameView(game);

        // JFrame created using JEasyFrame for the screen
        JFrame j = (Constants.fullscreenmode ? new JEasyFrame(view, "Asteroid Game") :  new JEasyFrame(view, "Asteroid Game"));//.addKeyListener(game.ctrl));
       // new JEasyFrameFull(view, "Asteroid Game").addKeyListener(game.ctrl);

        // key listener to listen in for keyboard input from user
        j.addKeyListener(game.ctrl);

        // run the game
        while(!(Constants.LIFE == 0)){
            // call to game update
            game.update();
            // Call to mainview repaint
            view.repaint();
            try {
				Thread.sleep(Constants.DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(game.remainingAsteroids() == 0 && game.remainingSaucers() == 0){

                    // new level if all enemies destroyed on current level
                    game.nextLevel();

                    // every even number for next level this variable being
                    // false allows for updating of the saucer, this was done
                    // as the ship continued updating & shooting while dead
                    game.saucer.dead = false;
                }
           // if player lives gone gameGoing false, end game loop
        } if(Constants.LIFE==0){
            Constants.gameGoing = false;
            System.out.println("game over");
        }
    }

    public static Vector2D s, v;

    // players ship
    Ship ship;

    // AimNShoot GameAI for saucer
    AimNShoot ctrl2;

    // RotateNShoot GameAI for saucer
    RotateNShoot ans2;

    SeekNShoot ans3;

    // Saucer variable to hold instance of Object
    Saucer saucer;


    // Array lists of type GameObject (All Game Objects that extend GameObject class)
    // objects main list, alive, pending used for alive or dead game objects, particles
    // list used to hold the particles created a call to updateParticles() allows for
    // removal of dead particles
    ArrayList<GameObject> objects;
    ArrayList<GameObject> alive;
    ArrayList<GameObject> pending;
    ArrayList<GameObject> particles;

    // Controller instance variable
    //Controller ctrl;

    // BasicKeys instance variable to be used for players ship key movements
    BasicKeys ctrl;

    // BasicKey instance variable to be used with remote for multiplayer ships
    BasicKeys keys;

    // Constructor for class
    public AsteroidsAIGame(boolean fullscreenmode){


        // call to new to create the array lists in memory space to hold to game Objects
        // to be iterated and modified within synchronized loops for game play
        objects = new ArrayList<GameObject>();
        alive = new ArrayList<GameObject>();
        pending = new ArrayList<GameObject>();
        particles = new ArrayList<GameObject>();
        Constants.fullscreenmode = fullscreenmode;

        // loops constant static variable for the initial amount of asteroids
        // for the starting level and invoked method make asteroid, adding the
        // asteroids to the objects array list
        for(int i = 0; i < Constants.N_INITIAL_ASTEROIDS; i++){
            objects.add(makeAsteroid());
        }

       // ans = new SeekNShoot();
        // call to new to create instances of AimNShoot and RotateNShoot GameAI Controllers
        //ctrl2 = new AimNShoot();
        ans2 = new RotateNShoot();
        ctrl = new BasicKeys();
        keys = new BasicKeys();
        ctrl2 = new AimNShoot();



        // call to new to create instance of BasicKeys to be used as an argument in ships parameter
        // to listen and react to players keyboard input in regards to ships movements






// creates a new instance of Saucer object with game, GameAI Controller, hitsToTake
        // constant variable as the arguments to its parameter which results, in reacting to
        // this game, with game AI of the controller used and the amount of hits the saucer
        // can take before being destroyed
        saucer = new Saucer(this, ctrl2, Constants.EshipHitsToTakeS);


        // creates new instance of Ship object with game, BasicKeys, colour for the
        // arguments for the ships parameter, resulting in similar effect to saucer
        // but with basic key registered to ship rather than gameAI controller,
        // resulting in players keyboard input listened to by BasicKeys and reacted to
        // by modifying the ships actions
        ship = new Ship(this, ctrl, Color.cyan, Constants.Sid);

        // adds ship and saucer to the objects array
        objects.add(getShip());

        if (Constants.SERVER_NAMES.length == 0) {
            // commented out to stop single player errors
            // uncomment back when testing multiplayer
            //objects.add(new Ship(this, keys, Constants.SHIP_COLORS[0]), Mid);
        } else {
            addShip(keys);
            for (String serverName : Constants.SERVER_NAMES)
                addShip(new RemoteKeys(serverName));
        }
    }
    public void addShip(Controller keys) {
        int numberOfShips = 0;
        for (GameObject go : objects)
            if (go instanceof Ship)
                numberOfShips++;
        if (numberOfShips >= 4) {
            System.err.println("At most four ships are supported");
            return;
        }

        Ship ship = new Ship(this, keys, Constants.SHIP_COLORS[numberOfShips], Constants.Mid);
        ship.s = new Vector2D(100, 100);
        if (numberOfShips % 2 != 0)
            ship.s.add(Constants.FRAME_WIDTH - 200, 0);
        if (numberOfShips / 2 != 0)
            ship.s.add(0, Constants.FRAME_HEIGHT - 200);
        objects.add(ship);
        // uncomment this out for multplayer update to work
        // also change Ship update method to take no argument i.e no Action action arg
        //ship.update();
    }

    // update method, updates game objects during game
    public void update(){

        //synchronized(this){

        // for loop iterates through objects array list checking instances of the objects and then
        // checks the collision between the objects with a call to checkCollision method which
        // checks the overlapping of the game objects, and a check to object not being dead is made
        // to add the alive game objects into alive array and for objects which are dead the
        // instances of the Objects class type is checked and a call the method explode is used
        synchronized (this){
        for(GameObject object : objects){
            object.update();
            //if(!(object instanceof Bullet));
            if(object instanceof Ship || object instanceof Asteroid || object instanceof Saucer)
                ////object.explode();

                checkCollision(object);



            if(!object.dead){

                alive.add(object);



            }
            else

                if(object instanceof Asteroid){
                    explode(object.s, 100, 500);
                    Asteroid.astCount-=1;
           // }else if(object instanceof Saucer) {
              //  saucerCount--;

                }else if(object instanceof Saucer){

                    explode(object.s, 50, 100);

                }else if(object instanceof Ship){

                    explode(object.s, 250, 200);

                }
        }
            updateParticles();
    }


        //updateParticles();
       /* if(saucerHit && saucerCount>0)
            addSaucer();  */

        // synchronized while the objects array list is being modified so there isn't any
        // concurrent exceptions, objects list is cleared, then pending added, particles
        // for explosions and alive objects added to objects array
        synchronized (this){

            objects.clear();
            objects.addAll(pending);
            objects.addAll(particles);
            objects.addAll(alive);
        }

        // call to updateParticles to rid of any dead particles and pending and alive lists are
        // cleared

        pending.clear();
        alive.clear();

        // call to saucer and ships update methods with the appropriate arguments for their parameters
        // GameAI for the saucer in its up to update the behavour of the saucer and ship update has the
        // basic key action in game for the ships update, this gives the saucer and ship the behavour to
        // now make use of the behavours. i.e saucer updates with gameAI, ship can now be moved with keyboard


        //eship.update(ans3.action(this));

        // used for single player update of players ship
        ship.update(ctrl.action(this));

        // uncomment when testing multiplayer
        //ship.update();

        // every even level the player gets to a saucer will be sent
        // here the update i.e gameAI action of ship is updated
        // when it meets the if statement requirement
        if(Constants.currentLevel %2 == 0 && !saucer.dead){
            saucer.dead = false;
            saucer.update(ctrl2.action(this));

        }

        // Constant save game true when user presses s button on keypad
        // invokes save method to save game, not fully implemented caused
        // errors even with declared serialisation
        /*if(Constants.SaveGame){
            save();   */
        }
    //}

    // checkCollision method, takes object as argument and checks if the object in the argument
    // and the otherObject which is found by Iterating objects list of current gameplay objects
    // and checks if they can get based the objects class which is checked against the canHit method
    // which specifies the object classes and possible objects that can hit, call to both objects
    // hit method is invoked in relation to the collision (behavour shifted) then instance checks
    // are used to score the player when it is a shot from his ships bullet i.e playerId 1 , 2 assigned
    // to enemy saucer
    public void checkCollision(GameObject object) {
        //if(object.dead)return;
        // check with other game objects
        if(!object.dead)

            for(GameObject otherObject : objects){
                if(canHit(object.getClass(), otherObject.getClass()) && overlap(object, otherObject)){
                    //score(object);
                    // the objects hit, and the other is also
                    object.hit(otherObject);
                    otherObject.hit(object);
                    if(otherObject instanceof Bullet && ((Bullet) otherObject).playerId == 1){
                            score(object);

                        // managed to split asteroid but errors followed so code commented out
                    }if(object instanceof Ship && ((Ship) object).shipId == 1){
                        Constants.LIFE -= 1;
                    }
                    //createAsteroidSplit(object);
                    //else if(object instanceof Asteroid){

                }
                }
            }

    public void checkAst(GameObject object){
        if(Constants.astHit = true){
            if(object instanceof Asteroid){
                createAsteroidSplit(object);

            }
    }
}

    // overlap boolean method compares the dist of both gameplay object vectors via the
    // vector2D class method dist and compares with the objects radius
    public boolean overlap(GameObject j, GameObject i){
        return j.s.dist(i.s) <= j.radius() + i.radius();
    }


    // adds object to pending list
    public void add(GameObject object){
        pending.add(object);
    }

    // method Iterable GameObject, returns the game objects
    @Override
    public Iterable<GameObject> getGameObjects(){
        return objects;
    }

    // method Iterable GameObject
    public Iterable<GameObject> getBackgroundObjects(){
        return null;
    }

    // returns ship
    public Ship getShip(){
        //return ship;
        return ship;
    }

    // returns int value of remaining asteroids
    // by iterating throughout the objects array list
    @Override
    public int remainingAsteroids(){
        int count = 0;
        for(GameObject o : objects){
            if(o instanceof Asteroid){
                count++;
            }
        }
        return count;
    }

    // explode method takes current objects position in its parameter
    // amount of particles and the life time of particles, explosion
    // sound invoked to play sound and synchronized loop adds the
    // particles by looping int n times which is variable passed
    // as argument and a call to create new Particle Object instances
    public void explode(Vector2D s, int n, int ttl) {
        SoundManager.play(SoundManager.bangMedium);
        synchronized (this){
            for (int i = 0; i < n; i++){
                particles.add(new Particle(this, s, (1 + Math.random() * ttl)));
            }
        }
    }

    // returns int of remaining saucers
    public int remainingSaucers(){
        int count = 0;
        for(GameObject o : objects){
            if(o instanceof Saucer){
                count++;
            }
        }
        return count;
        //return 0;
    }

    // increments score by 1 (not used)
    public void incScore(){
        Constants.score++;
    }

    // returns int score
    public int getScore(){
        return Constants.score;
    }

    // returns int life
    public int getLife(){

        return Constants.LIFE;
    }

    // increments life (planned to increment every certain amount of score earned
    public void incLife(){
        //if(score == 30)
        Constants.LIFE+=1;
    }

    // return int of current level
    public int getLevel(){
        return Constants.currentLevel;
    }

    // moves to next level of game, adding 1 more asteroid each time
    // synchronized loop used while adding new asteroids to the objects
    // list for the new level
    public void nextLevel(){
        //saucer.dead = false;
        Constants.currentLevel++;
        Constants.astCleared ++;
        //saucerCount = 0;
        //currentSpawnLimitSaucer = 0;
        if(Constants.currentLevel>1)


            // ship.reset();
            synchronized (this){
                for(int i = 0; i < Constants.astCleared; i++){

                    objects.add(makeAsteroid());

                }
               // if(saucerCount2>50)
                  //  saucer2 = new Saucer(this, ans, EshipHitsToTakeS, Epn);
                   // objects.add(saucer);

            } if(Constants.currentLevel % 2 == 0){
                objects.add(saucer);

        }



        /*}else if(getLife() == 0){
            System.out.println("game Over");
            currentLevel = 1;
        }   */
    }

    // save method to write to save file - text file "newSaveGame"
    public void save(){
        ObjectOutputStream out;
        try{
            out = new ObjectOutputStream(new FileOutputStream(Constants.SAVE_FILE));
            out.writeObject(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // iterable, returns particle iterated in list
    public Iterable<GameObject> getParticles(){
        return particles;
    }

    // updates particles, removing dead particles from the game
    public void updateParticles(){
        // iterate over the set of particles, removing any dead ones
        ListIterator<GameObject> it = particles.listIterator();
        synchronized (this){
            while(it.hasNext()){
                Particle p = (Particle) it.next();
                p.update();
                if(p.dead){
                    it.remove();
                }
            }
        }
    }
    public void createAsteroidSplit(GameObject ob){



        //if(object instanceof Asteroid)
        //System.out.println("loop");
        Asteroid ab = (Asteroid) ob;
        System.out.println(ab.getClass());
        double splitAstX = ab.s.x;
        System.out.println("absx"+ab.s.x);
        double splitAstY = ab.s.y;
        System.out.println("absx"+ab.s.y);

        //int size =;
        for(int j = 0; j<2; j++){
            double xV = Math.random() + 1;
            double yV = Math.random() + 1;
            int xD = (int)(Math.random() *2);
            int yD = (int)(Math.random() *2);
            s = new Vector2D(splitAstX, splitAstY);
            v = new Vector2D(xV, yV);
            if(xD == 1)
                xV *= (-1);
            if(yD == 1)
                yV *= (-1);

            //split = true;
            Asteroid.splitAst-=1;


        }

            ab = new Asteroid(s, v, Asteroid.radius/Math.sqrt(Constants.astSplitAst), Asteroid.splitAst, Asteroid.hitsToTake-1);


            synchronized (this){
                objects.remove(ob);
                objects.add(ab);


         //objects.add(ab);// new Asteroid(s, v, astStage);


        //return new Asteroid;
        //int
        //return new Asteroid(s.x, s.y, hitsToTake-1, 2)

    }
    }


    // returns static boolean, makes sure game objects of the same type can't hit each other
    public static boolean canHit(Class<?> c1, Class<?> c2){
        return
                // no hits between objects of same class
                (c1 != c2) && ((c1 == Bullet.class) || (c2 == Bullet.class) ||
                        (c1 == Asteroid.class && c2 == Ship.class) ||
                        (c1 == Ship.class && (c2 == Asteroid.class) ||
                        c2 == Saucer.class)) || (c1 == Saucer.class && c2 == Ship.class);
    }


   

    // method creates new asteroid using Vector2D variables s, v to maintain position and velocity
    // reutning a new object instance of the Asteroid class
    public Asteroid makeAsteroid (){
            s = new Vector2D(Math.random() * Constants.FRAME_WIDTH, Math.random() * Constants.FRAME_HEIGHT);
            v = new Vector2D((Math.random() - 0.5) * 2 * Constants.VMAX, (Math.random() - 0.5) * 2 * Constants.VMAX);
            return new Asteroid(s, v, Constants.Rr, Constants.astSplitAst, Constants.astHitsToTake);

    }

    // score method increments score by 10 if asteroid hit by player
    // else if saucer hit, saucer points incremented
    public void score(GameObject object){
        if(object instanceof Asteroid) Constants.score+= 10;

         else if(object instanceof Saucer){
        Constants.score += Constants.SAUCER_POINTS;
        Constants.saucerHit = true;
    }
        if (Constants.score %1000 == 0){
            Constants.LIFE+=1;
        }
    }
}



