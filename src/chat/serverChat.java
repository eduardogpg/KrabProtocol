
package chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import krabprotocol.singletonServerChat;
/**
 *
 * @author 1020142461
 */
public class serverChat  extends UnicastRemoteObject implements chatCommunication {
    
     private Hashtable<String,String> dictionariChats =new Hashtable<String,String>(); 
    
    singletonServerChat ssc;
     public serverChat() throws RemoteException{
	super();
               
        ssc= singletonServerChat.getInstance();
        
        
    }

    /**
     *
     * @param userName 
     * @param ip
     * @return
     * @throws RemoteException
     */
    @Override 
    public boolean setNewConversation(String userName, String ip) throws RemoteException {
        
        if(!this.dictionariChats.containsKey(userName)){
            this.dictionariChats.put(userName, ip);
        
            
        }
        
        return true;
    }

     @Override
    public void sendPublicMessage(String UserName, String message) throws RemoteException {
      
    }

    public boolean connect() throws RemoteException {
        return true;
    }
       
   
    
}
