/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finals;

import control.NumericServer;
import control.ObjectServer;
import control.RMIServer;
import control.StringServer;

/**
 *
 * @author ASUS
 */
public class Finals {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ObjectServer().start();
        new RMIServer().start();
        new StringServer().start();
        new NumericServer().start();
    }
    
}
