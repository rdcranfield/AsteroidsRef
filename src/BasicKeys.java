package Asteroids;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BasicKeys extends KeyAdapter implements Controller {
    Action action;
    public BasicKeys(){
        action = new Action();

    }

    public Action action(Game game){
        return action;
    }

    /*
    keyPressed, checks if user has pressed any buttons
    which affect the ship, i.e ships movements are
    determined by left or right direction on the keypad,
    the ship turns in the corresponding direction
    sets shoot to true if space bar has been pressed
    and ship can thrust via up direction on the keypad
     */
    public void keyPressed(KeyEvent e){
        //System.out.println(e);
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP :
                action.thrust = 1;

                break;
            case KeyEvent.VK_LEFT :
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT :
                action.turn = +1;
                break;
            case KeyEvent.VK_SPACE :
                action.shoot = true;
                break;
            case KeyEvent.VK_B :
                action.powershoot = true;
                break;
            case KeyEvent.VK_F2 :
                action.fullscreenmode = 1;
                Constants.fullscreenmode = true;
                //action.fullscreenmode = true;
                break;
            case KeyEvent.VK_S :
                Constants.SaveGame = true;
                break;


        }
    }

    /*
    keyReleased, checks if user has released
    any buttons previously clicked while moving the ship
    it sets the ship movements i,e left & right to 0
    (no movement), sets shoot to false once space bar
    has been released and stops ship thrusting when user
    isn't pressing the up direction on the keypad
     */
    public void keyReleased(KeyEvent e){
        //System.out.println(e);
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP :
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT :
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT :
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE :
                action.shoot = false;
                break;
            case KeyEvent.VK_B :
                action.powershoot = false;
                break;
            case KeyEvent.VK_F2 :
                Constants.fullscreenmode = false;
                action.fullscreenmode = 0;
                System.out.println("F2");

                break;
        } // end of switch/case statement
    } // end of keyReleased method

        //testing purposes
      /*  public void keyTyped(KeyEvent e){
            System.out.println(e);
        } */
    }

