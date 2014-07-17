
package chat;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import krabprotocol.singletonChat;
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

    public boolean setConversation(String userName, String ip) throws RemoteException {
        
        System.out.println("Aqui se abre una nueva  ventana");

        return true;
    }
    
    
}
