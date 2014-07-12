
package chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author 1020142461
 */
public class serverChat  extends UnicastRemoteObject implements chatCommunication {

     public serverChat() throws RemoteException{
	super();
    }
       
    public void getMessage(String message) throws RemoteException {
       System.out.println(message);
    }
    
    
}
