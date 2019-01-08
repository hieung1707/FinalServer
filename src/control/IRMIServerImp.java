/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.ServerConfiguration;
import model.Student;

/**
 *
 * @author ASUS
 */
public class IRMIServerImp implements IRMIServer, Serializable {

    @Override
    public ServerConfiguration getStringServerDes(Student student, ServerConfiguration config) throws RemoteException {
        String maSV = student.getMaSV();
        if (ObjectServer.listMaSV.contains(maSV))
            config.setStringServerPort(StringServer.PORT);
        return config;
    }

    @Override
    public ServerConfiguration getNumericServerDes(Student student, ServerConfiguration config) throws RemoteException {
        String maSV = student.getMaSV();
        if (ObjectServer.listMaSV.contains(maSV))
            config.setNumericServerPort(NumericServer.PORT);
        return config;
    }
    
}
