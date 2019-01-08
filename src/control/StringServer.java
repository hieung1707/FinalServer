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
import java.util.Objects;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import model.Answer;
import model.Student;

/**
 *
 * @author ASUS
 */
public class StringServer extends Thread {
    static final int PORT = 10001;
    static final int step = 3;
    
    ServerSocket server;
    
    public StringServer() {

    }
    
    private void init() throws Exception {
        server = new ServerSocket(PORT);
        System.out.println("String server ready");
        while (true) {
            sendAndComputeString();
        }
    }
    
    private void sendAndComputeString() {
        ObjectOutputStream oos = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            Socket socket = server.accept();
            oos = new ObjectOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            String maSV = dis.readUTF();
            String hovaten = dis.readUTF();
            int nhom = dis.readInt();
            int code = dis.readInt();
            Answer answer = new Answer(new Student(maSV, hovaten, "", nhom), new Object[3], new boolean[3], true);
            switch (code) {
                case 0:
                    answer.getIsRights()[0] = ceasarProblem(dis, dos);
                    answer.getIsRights()[1] = subStringProblem(dis, oos);
                    answer.getIsRights()[2] = countCharProblem(dis, dos);
                    break;
                case 1:
                    answer.getIsRights()[0] = countCharProblem(dis, dos);
                    answer.getIsRights()[1] = subStringProblem(dis, oos);
                    answer.getIsRights()[2] = ceasarProblem(dis, dos);
            }
            oos.writeObject(answer);
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
                e.printStackTrace();
            }
        }
    }
    
    private boolean ceasarProblem(DataInputStream dis, DataOutputStream dos) throws Exception {
        String orString = generateString();
        dos.writeUTF(orString);
        dos.writeInt(step);
        String encrypted = dis.readUTF();
        if (encrypted.equals(ceasarEncrypt(orString)))
            return true;
        return false;
    }
    
    private boolean subStringProblem(DataInputStream dis, ObjectOutputStream oos) throws Exception {
        String orString = generateString();
        int a, b;
        a = new Random().nextInt(orString.length() + 1);
        do {
            b = new Random().nextInt(orString.length() + 1);
        } while (b == a);
        if (a > b) {
            int temp  = a;
            a = b;
            b = temp;
        }
        oos.writeObject(orString);
        oos.writeObject(a);
        oos.writeObject(b);
        String subString = dis.readUTF();
        if (orString.substring(a, b).equals(subString))
            return true;
        return false;
    }
    
    private boolean countCharProblem(DataInputStream dis, DataOutputStream dos) throws Exception {
        String orString = generateString();
        dos.writeUTF(orString);
        Character result = dis.readChar();
        if (result == getSecondCount(orString))
            return true;
        return false;
    }
    
    private char getSecondCount(String str) {
        int arr[] = new int[256];
        for (int i = 0; i < 256; i++)
            arr[i] = 0;
        for (int i = 0; i < str.length(); i++) {
            arr[str.charAt(i)] += 1;
        }
        int mostCount = 0;
        int secondCount = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > arr[mostCount]) {
                secondCount = mostCount;
                mostCount = i;
            }
            else if (arr[i] > arr[secondCount] && arr[i] < arr[mostCount])
                secondCount = i;
        }
        return (char) secondCount;
    }
     
    private String ceasarEncrypt(String str) {
        String newStr = "";
        for (int i = 0; i < str.length(); i++) {
            int ch = (int) str.charAt(i);
            ch = (ch - 'a' + step) % 26 + 'a';
            newStr += (char) ch;
        }
        return newStr;
    }
    
    private String generateString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
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
