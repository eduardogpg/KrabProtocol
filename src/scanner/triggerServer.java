/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scanner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author eduardo
 */
public class triggerServer extends Thread{
    
    public void run(){
    
        System.out.println("Comenzando scanner");
        try {
            scannerNetwork myServer = new serverScanner();
            Naming.rebind( "rmi://localhost:2099/scanner" , myServer);
            System.out.println("scanner en linea...");
            
        } catch (RemoteException ex) {
            Logger.getLogger(triggerServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(triggerServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
