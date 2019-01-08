/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.ServerConfiguration;
import model.Student;

/**
 *
 * @author ASUS
 */
public interface IRMIServer extends Remote {
    ServerConfiguration getStringServerDes(Student student, ServerConfiguration config) throws RemoteException ;
    ServerConfiguration getNumericServerDes(Student student, ServerConfiguration config) throws RemoteException;
}
