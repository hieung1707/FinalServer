/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ServerConfiguration;
import model.Student;

/**
 *
 * @author ASUS
 */
public class ObjectServer extends Thread {

    static final String RMI_NAME = "getPort";
    static final int RMI_PORT = 10000;
    static final int CONFIG_PORT = 12345;
    static ArrayList<String> listMaSV = new ArrayList<>();

    private ServerSocket socket;

    public ObjectServer() {

    }

    private void init() throws Exception {
        socket = new ServerSocket(CONFIG_PORT);
        System.out.println("TCP Config Server ready");
        while (true) {
            sendRMIInfo();
        }
    }

    private void sendRMIInfo() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {

            Socket clientSocket = socket.accept();
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
            Student s = (Student) ois.readObject();
            ServerConfiguration config = (ServerConfiguration) ois.readObject();
            System.out.println(s.getMaSV());
            if (!listMaSV.contains(s.getMaSV())) {
                listMaSV.add(s.getMaSV());
            }
            config.setRmiServerName(RMI_NAME);
            config.setRmiPort(RMI_PORT);
            config.setCode(new Random().nextInt(2));
            oos.writeObject(config);
            oos.close();
            ois.close();
        } catch (Exception e) {
            try {
                oos.close();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(ObjectServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
