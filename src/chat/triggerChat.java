

package chat;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1020142461
 */
public class triggerChat extends Thread{
    
    
    public void run(){
        try {
            System.out.println("Comenzando Server de mensajeria...");
            chatCommunication myChat = new serverChat();
            
            try {
                Naming.rebind("rmi://localhost:1099/myChat", myChat);
                System.out.println("Server de mensajeria Online ...");
            } catch (MalformedURLException ex) {
               System.err.println(ex);
            }
            
        } catch (RemoteException ex) {
            System.err.println(ex);
        }
        
        
    }
}
