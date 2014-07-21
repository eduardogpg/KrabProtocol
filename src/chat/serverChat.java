
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

    /**
     *
     * @param userName 
     * @param ip
     * @return
     * @throws RemoteException
     */
    @Override 
    public boolean setNewConversation(String userName, String ip) throws RemoteException {
        //System.out.println("Vamos");
        
        FXMLContactWindowController s =ssc.getFXMLContactWindowController();
        Hashtable table = s.getHashTable();
        if(!table.containsKey(userName+"Prueba"))
            s.makeChat(userName+"Prueba", ip);
       
        return true;
    }

     @Override
    public void sendPublicMessage(String userName, String message) throws RemoteException {
       //System.out.println(userName + " : "+ message);
        
       singletonServerChat ssh = singletonServerChat.getInstance();
        FXMLContactWindowController x =ssh.getFXMLContactWindowController();
        ChatWindowController sH = x.getChatWindowController(userName+"Prueba");
        sH.putMessage("\n"+userName+"Prueba" + " : "+message);
        
        /*
        Enumeration<String> elemnts = sH.keys();
        while(elemnts.hasMoreElements()){
               String user = elemnts.nextElement();
               System.out.println("Valor en la lista " + user);
        }
        ChatWindowController xx = x.getChatWindowController(userName+"Prueba");*/
        
    }

    public boolean connect() throws RemoteException {
        return true;
    }
       
   
    
}
