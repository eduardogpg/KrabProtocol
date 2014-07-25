
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

    @Override 
    public boolean setNewConversation(String userName, String ip) throws RemoteException {
        
        if (!singletonServerChat.dictionariChats.containsKey( userName ))
            this.makeNewWindowsChat(userName, ip);
        else
            System.err.println("Ya no se puede :( ");
        
        return true;
    }

    private void makeNewWindowsChat(String userName, String ip){
        FXMLContactWindowController controller = ssc.getFXMLContactWindowController();
        controller.makeChat(userName+'p', ip);
        
    }
    
    @Override
    public void sendPublicMessage(String userName, String message) throws RemoteException {
        System.out.println(userName +" : "+ message );
         if (singletonServerChat.dictionariChats.containsKey( userName ))
            this.pushMessage(userName , message);
        else
            System.err.println("Ya no se puede colocar el mensaje :( ");
    }
    
    
    private void pushMessage(String userName, String message){
        ChatWindowController myController = singletonServerChat.dictionariChats.get(userName);
        myController.putMessage("\n"+userName + " : "+ message);
    }

        
    public boolean connect() throws RemoteException {
        return true;
    }
       
   
    
}
