package Asteroids;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 04/06/13
 * Time: 19:42
 */

// Controller interface
public interface Controller{

    // Action with regard to the game allows for classes that implement Controller
    // to observe and react to actions in the game, and respond.
    public Action action(Game game);
}

