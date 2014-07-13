
package chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import krabprotocol.ChatWindowController;

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
        System.out.println("Se a creado una nueva ventana");
        return true;
    }
    
    
}
