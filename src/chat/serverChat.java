
package chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
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

    public boolean setNewConversation(String userName, String ip) throws RemoteException {
        if(!singletonServerChat.ChatList.containsKey(userName)){
            
            FXMLContactWindowController controller = ssc.getFXMLContactWindowController();
            controller.makeChat(userName,ip,1);
        
        }else
            System.err.println("El usuario ya esta en la lista ");
        
        return true;
    }

    public void sendPublicMessage(String userName, String message) throws RemoteException {
      
        
        
        if(singletonServerChat.ChatList.containsKey(userName)){
            ChatWindowController controller = singletonServerChat.ChatList.get(userName);
            controller.putMessage("\n"+userName + " : "+ message);
        }else{
            System.err.println("NOOOOOOOOOOOO ESTAAAAAAAAAAAAA ");
        }
        
    }
    
    public boolean connect() throws RemoteException {
        return true;
    }
       
   
    
}
