/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Answer;
import model.Student;
import view.Form;

/**
 *
 * @author ASUS
 */
public class NumericServer extends Thread {
    static final int PORT = 10002;
    static final HashMap<String, Integer> DIC = initDictionary();    
    
    private Form frm;
    ServerSocket server;
    
    public static HashMap<String, Integer> initDictionary() {
        HashMap<String, Integer> dic = new HashMap<>();
        dic.put("Mot", 1);
        dic.put("Hai", 2);
        dic.put("Ba", 3);
        dic.put("Bon", 4);
        dic.put("Nam", 5);
        dic.put("Sau", 6);
        dic.put("Bay", 7);
        dic.put("Tam", 8);
        dic.put("Chin", 9);
        return dic;
    }
        
    public NumericServer(Form frm) {
        this.frm = frm;
    }
    
    private void init() throws Exception {
        server = new ServerSocket(PORT);
        System.out.println("Numeric server ready");
        while (true) {
            sendAndComputeNumber();
        }
    }
    
    public void sendAndComputeNumber() {
        ObjectOutputStream oos = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            Socket socket = server.accept();
            socket.setSoTimeout(TCPServer.TIMEOUT);
            oos = new ObjectOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
            String maSV = dis.readUTF();
            String havoten = dis.readUTF();
            int nhom = dis.readInt();
            int code = dis.readInt();
            if (!TCPServer.listMaSV.contains(maSV))
                return;
            Student student = new Student(maSV, havoten, "", nhom);
            Answer answer = null;
            for (Answer a : TCPServer.listAnswer) {
                if (a.getStudent().getMaSV().equals(maSV)) {
                    answer = a;
                    break;
                }
            }
            switch (code) {
                case 0:
                    answer.getIsRights()[0] = coPrimeProblem(dis, dos);
                    answer.getIsRights()[1] = primeProblem(dis, dos);
                    break;
                case 1:
                    answer.getIsRights()[1] = primeProblem(dis, dos);
                    answer.getIsRights()[0] = coPrimeProblem(dis, dos);
                    break;
            }
            oos.writeObject(answer);
            frm.updateInfo();
            oos.close();
            dis.close();
            dos.close();
            socket.close();
        } catch (Exception e) {
            try {
                oos.close();
                dis.close();
                dos.close();
            } catch (IOException ex) {
                Logger.getLogger(NumericServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private boolean coPrimeProblem (DataInputStream dis, DataOutputStream dos) throws Exception {
        String str1 = "asda123123 aszxcz12345 axxvx1231";
        String str2 = "asdzx312sad12 xcz213 1h2312bb";
        dos.writeUTF(str1);
        dos.writeUTF(str2);
        boolean answer = dis.readBoolean();
        if ((ucln(extractNumber(str1), extractNumber(str2)) == 1) == answer) {
            return true;
        }
        return false;
    }
    
    private boolean primeProblem (DataInputStream dis, DataOutputStream dos) throws Exception {
        String str3 = "Bon;Nam;Sau;Bay";
        dos.writeUTF(str3);
        int answer = dis.readInt();
        if (answer == sumPrimes(extractNumberFromDic(str3)))
            return true;
        return false;
    }
     
    private int extractNumberFromDic(String str) {
        String num = "";
        String[] splits = str.split(";");
        for (String s : splits) {
            num += DIC.get(s);
        }
        return Integer.parseInt(num);
    }
    
    private int extractNumber(String str) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9')
                sum += (int) (c - '0');
        }
        return sum;
    }
    
    private int ucln(int a, int b) {
        if (a == 1 || b == 1)
            return 1;
        while (a != b) {
            if (a > b)
                a = a - b;
            else if (a < b)
                b = b - a;
        }
        return a;
    }
    
    private int sumPrimes(int num) {
        int sum = 0;
        for (int i = 2; i < num; i++) {
            boolean isPrime = true;
            for (int j = 2; j <= i / 2; j++)
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            if (isPrime)
                sum += i;
        }
        return sum;
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
