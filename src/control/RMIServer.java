/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author ASUS
 */
public class RMIServer extends Thread {

    static final int PORT = 10000;
    static final String SERVER_NAME = "getPort";

    public RMIServer() {
    }

    private void init() throws Exception {
        IRMIServerImp svImp = new IRMIServerImp();
        IRMIServer sv = (IRMIServer) UnicastRemoteObject.exportObject(svImp, 0);
        Registry registry = LocateRegistry.createRegistry(PORT);
//        Registry registry = LocateRegistry.getRegistry();
        registry.bind(SERVER_NAME, sv);
        System.out.println("RMI Server ready");
    }

    @Override
    public void run() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Thread(new RMIServer()).start();
    }
}
