package Asteroids;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 14/06/13
 * Time: 19:45
 */
public interface Game {
    // Game Interface, AsteroidsAIGame implements this interface and makes use of methods

    public Asteroid makeAsteroid ();

    public ArrayList<GameObject> objects = new ArrayList<GameObject>();

    ArrayList<GameObject> particles = new ArrayList<GameObject>();

    public Iterable<GameObject> getParticles();

    public void add(GameObject obj);

    public Iterable<GameObject> getGameObjects();

    public Iterable<GameObject> getBackgroundObjects();

    public Ship getShip();

    public int remainingAsteroids();

    public int remainingSaucers();

    public void incScore();

   // public void createAsteroidSplit(GameObject object);

    public int getScore();

    public int getLife();

    public void incLife();

    public int getLevel();

    public void nextLevel();
}
