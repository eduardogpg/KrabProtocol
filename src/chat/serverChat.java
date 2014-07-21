
package chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import krabprotocol.ChatWindowController;
import krabprotocol.FXMLContactWindowController;
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
        System.out.println("Vamos");
        FXMLContactWindowController s =ssc.getFXMLContactWindowController();
        s.makeChat(userName, ip);
       
                  
        return true;
    }

     @Override
    public void sendPublicMessage(String userName, String message) throws RemoteException {
       System.out.println(userName + " : "+ message);
       
    }

    public boolean connect() throws RemoteException {
        return true;
    }
       
   
    
}
