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
import model.Answer;
import model.ServerConfiguration;
import model.Student;
import view.Form;

/**
 *
 * @author ASUS
 */
public class TCPServer extends Thread {

    static final String RMI_NAME = "getPort";
    static final int RMI_PORT = 10000;
    static final int CONFIG_PORT = 12345;
    static final int TIMEOUT = 5000;
    static volatile ArrayList<String> listMaSV = new ArrayList<>();
    public static volatile ArrayList<Answer> listAnswer = new ArrayList<>();

    private ServerSocket socket;
    private Form frm;

    public TCPServer(Form frm) {
        this.frm = frm;
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
            clientSocket.setSoTimeout(TIMEOUT);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
            Student s = (Student) ois.readObject();
            ServerConfiguration config = (ServerConfiguration) ois.readObject();
            System.out.println(s.getMaSV());
            if (!listMaSV.contains(s.getMaSV())) {
                listMaSV.add(s.getMaSV());
                listAnswer.add(new Answer(s, new Object[5], new boolean[5], true));
                frm.setAnswerList(listAnswer);
            }
            config.setNumericServerPort(NumericServer.PORT);
            config.setStringServerPort(StringServer.PORT);
//            config.setRmiServerName(RMI_NAME);
//            config.setRmiPort(RMI_PORT);
            config.setCode(new Random().nextInt(2));
            oos.writeObject(config);
            oos.close();
            ois.close();
        } catch (Exception e) {
            try {
                oos.close();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
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
