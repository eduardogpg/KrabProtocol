

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
            System.out.println("Run Server ...");
            LocateRegistry.createRegistry(1099);
            chatCommunication myChat = new serverChat();
            
            try {
                Naming.rebind("rmi://localhost:1099/saludo", myChat);
                System.out.println("Server onLine ...");
            } catch (MalformedURLException ex) {
               System.err.println(ex);
            }
            
        } catch (RemoteException ex) {
            System.err.println(ex);
        }
        
        
    }
}
