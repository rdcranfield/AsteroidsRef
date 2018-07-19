package Asteroids;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 04/06/13
 * Time: 19:39
 */

public class Action implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4285953341079929863L;
	// action instance variables used throughout the other classes
    int thrust;
    int turn;
    boolean shoot;
    boolean powershoot;
    int fullscreenmode;

    // toString of action, was to be used with multiplayer, to keep track of actions of both players ship
    public String toString(){
        return "Action(thrust= " + thrust + ", turn= " + turn + ", shoot= " + shoot + ")";
    }
}

