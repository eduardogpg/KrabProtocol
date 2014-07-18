
package chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import krabprotocol.singletonServerChat;
/**
 *
 * @author 1020142461
 */
public class serverChat  extends UnicastRemoteObject implements chatCommunication {
    
    singletonServerChat ssc;
     public serverChat() throws RemoteException{
	super();
         ssc= singletonServerChat.getInstance();
    }

    @Override
    public boolean setNewConversation(String userName, String ip) throws RemoteException {
        
        ssc.makeNewChat(userName, ip);
        return true;
    }

    @Override
    public void sendPublicMessage(String UserName, String message) throws RemoteException {
       System.out.println("De "+UserName+ "  : "+message);
    }

    @Override
    public boolean connect(String userName) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
    
    
    
}
