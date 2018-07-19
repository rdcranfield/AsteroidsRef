package Asteroids;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 12/06/13
 * Time: 23:37
 */

public class AimNShoot implements Controller {

    public static final double SHOOTING_DISTANCE = 150, SHOOTING_ANGLE = Math.PI /12;

    GameObject target;
    Action action = new Action();
    Ship ship;



    @Override
    public Action action(Game game) {

        ship = game.getShip();
        //System.out.println(game.getGameObjects());
        GameObject nextTarget = findTarget(game.getGameObjects()) ;
        if(nextTarget == null)
            return action;
        switchTarget(nextTarget);
        aim();
        action.shoot = ((Math.abs(angleToTarget()) < SHOOTING_ANGLE) && inShootingDistance());
        return action;

        //return action.shoot;
    }

    public GameObject findTarget(Iterable<GameObject> gameObjects){
        double minDistance = 2*SHOOTING_DISTANCE;
        GameObject closestTarget = null;
        //synchronized (this){
        for(GameObject obj : gameObjects){
            if(obj == ship || obj instanceof Bullet)
                continue;
            //System.out.println(obj.getClass());
            //System.out.println(ship);
//            System.out.println("dist"+ship.dist(obj));
            double dist = ship.dist(obj);

           // o.distToShip = (int)dist;
            if(dist < minDistance){
                closestTarget = obj;
                minDistance = dist;
          //  }
            }
        }
        return closestTarget;
    }

    public double angleToTarget(){
        Vector2D v = this.ship.to(this.target);
        v.rotate(-ship.d.theta());

        return v.theta();
    }

    public boolean inShootingDistance(){
        return ship.dist(target) < SHOOTING_DISTANCE + target.radius();
        //Vector2D v = ship.to(target);
        //return v.mag() < SHOOTING_DISTANCE + target.radius();
        //return false;
    }

    public void aim(){
        double angle = angleToTarget();
        action.turn = (int)Math.signum(Math.sin(angle));
    }

    public void switchTarget(GameObject newTarget){
        target = newTarget;
        /*if(target != null)
            target.isTarget = false;
        target = newTarget;
        newTarget.isTarget = true;   */
    }


}