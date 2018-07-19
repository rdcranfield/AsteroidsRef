package Asteroids;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class RemoteKeys implements Controller {
    RemoteController rkb;
    Action action;

    RemoteKeys(String serverName) {
        action = new Action();
        String name = "//" + serverName + ":" + Constants.REGISTRY_PORT
                + "/RemoteKeyboard";
        try {
            rkb = (RemoteController) Naming.lookup(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Action action(Game game) {
        try {
             action = rkb.action();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return action;
    }

}
