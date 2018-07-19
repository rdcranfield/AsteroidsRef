package Asteroids;

import java.rmi.*;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 16/06/13
 * Time: 00:44
 */



public interface RemoteController extends Remote {
    public Action action() throws RemoteException;
}
