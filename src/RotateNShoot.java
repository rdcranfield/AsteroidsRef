package Asteroids;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 18/06/13
 * Time: 16:57
 */

public class RotateNShoot implements Controller {
    Action action = new Action();

    @Override
    public Action action(Game game) {

        action.shoot = true;
        action.turn = 1;
        return action;
    }
}

