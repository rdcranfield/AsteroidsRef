package Asteroids;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RemoteKeyboard extends UnicastRemoteObject implements
        RemoteController {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1637366534649883490L;
	public JLabel jl;
    BasicKeysWithJLabel bk;

    class BasicKeysWithJLabel extends BasicKeys {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            jl.setText(action.toString());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            jl.setText(action.toString());
        }
    }

    public RemoteKeyboard() throws RemoteException {
        jl = new JLabel("Welcome!");
        JEasyFrame jf = new JEasyFrame(jl, "Remote Keyboard");
        jf.setSize(300, 100);
        bk = new BasicKeysWithJLabel();
        jf.addKeyListener(bk);
    }

    public static void main(String[] args) throws Exception {
        // start registry if it is not already running
        LocateRegistry.createRegistry(Constants.REGISTRY_PORT);
        RemoteKeyboard service = new RemoteKeyboard();
        Naming.rebind("//localhost:" + Constants.REGISTRY_PORT
                + "/RemoteKeyboard", service);
        System.out.println("Remote keyboard registered");
    }

    @Override
    public Action action() throws RemoteException {
        return bk.action;
    }

}