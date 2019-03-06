/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finals;

import control.NumericServer;
import control.TCPServer;
import control.RMIServer;
import control.StringServer;
import view.Form;

/**
 *
 * @author ASUS
 */
public class Finals {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Form frm = new Form();
        new TCPServer(frm).start();
        new RMIServer().start();
        new StringServer(frm).start();
        new NumericServer(frm).start();

    }

}
