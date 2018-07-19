package Asteroids;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 12/06/13
 * Time: 23:47
 */

public class SeekNShoot implements Controller {
    public static final double FLEE_DISTANCE = 200.0, FLEE_THRUST = 2.0, FLEE_ANGLE = Math.PI /5;
    public static final double PURSUIT_ANGLE = Math.PI /15, PURSUIT_DISTANCE = 150.0, PURSUIT_THRUST = 3.0;
    public static final double SHOOTING_ANGLE = Math.PI /12, SHOOTING_DISTANCE = 100.0;
    GameObject target;
    Action action;
    Ship ship;
    //Saucer saucer;

    public Action action(Game game){

        GameObject nextTarget = findTarget(game.getGameObjects());
        ship = game.getShip();
        if(nextTarget == null)
            return action;
        switchTarget(nextTarget);
        if(ship.dist(nextTarget) < FLEE_DISTANCE)
            evade();
        else
            seek();
        action.shoot = ((Math.abs(angleToTarget()) < SHOOTING_ANGLE) && inShootingDistance());
        return action;
    }

    public double angleToTarget(){
        Vector2D v = ship.to(target);
        v.rotate(-ship.d.theta());

        return v.theta();
    }

    public void evade(){
        ship.fleeing = true;
        double angle = angleToTarget();
        if(Math.abs(angle) < FLEE_ANGLE){
            action.turn = -(int) Math.signum(Math.sin(angle));
            action.thrust = 0;
        }
        else {
            action.thrust = (int)FLEE_THRUST;
        }
    }

    public GameObject findTarget(Iterable<GameObject> gameObjects){
        //double minDistance = Constants.WORLD_WIDTH;
        double minDistance = Constants.FRAME_WIDTH;
        GameObject closestTarget = null;
        for(GameObject obj : gameObjects){
            if(obj == ship || obj instanceof Bullet)
                continue;

            double dist = ship.dist(obj);
            //obj.distToShip = (int)dist;
            if(dist < minDistance){
                closestTarget = obj;
                minDistance = dist;
            }
        }   return closestTarget;
    }

    public boolean inShootingDistance(){
        return ship.dist(target) < SHOOTING_DISTANCE + target.radius();
    }

    public void seek(){
        ship.fleeing = false;
        double angle = angleToTarget();
//        System.out.println(angle);
        action.turn = (int)Math.signum(Math.sin(angle));
        if((Math.abs(angle) < PURSUIT_ANGLE) && !inShootingDistance())
            action.thrust = (int)PURSUIT_THRUST;
        else
            action.thrust = 0;

    }

    public void switchTarget(GameObject newTarget){
       // if(target != null)
         //   target.isTarget = false;
        target = newTarget;
        //newTarget.isTarget = true;

    }


}

