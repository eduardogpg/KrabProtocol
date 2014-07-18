

package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author 1020142461
 */
public interface chatCommunication extends Remote{
    
    public boolean setNewConversation(String userName, String ip) throws RemoteException;
    public void sendPublicMessage(String UserName, String message) throws RemoteException;
    public boolean connect(String userName) throws RemoteException;
   
    
}
