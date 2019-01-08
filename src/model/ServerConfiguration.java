/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class ServerConfiguration implements Serializable {
    static final long serialVersionUID = 3L;
    String rmiServerName;
    int rmiPort;
    int stringServerPort;
    int numericServerPort;
    int code;

    public ServerConfiguration(String rmiServerName, int rmiPort, int stringServerPort, int numericServerPort, int code) {
        this.rmiServerName = rmiServerName;
        this.rmiPort = rmiPort;
        this.stringServerPort = stringServerPort;
        this.numericServerPort = numericServerPort;
        this.code = code;
    }

    public void setRmiServerName(String rmiServerName) {
        this.rmiServerName = rmiServerName;
    }

    public void setRmiPort(int rmiPort) {
        this.rmiPort = rmiPort;
    }

    public void setStringServerPort(int stringServerPort) {
        this.stringServerPort = stringServerPort;
    }

    public void setNumericServerPort(int numericServerPort) {
        this.numericServerPort = numericServerPort;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    
}
