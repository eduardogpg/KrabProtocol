
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
        //userName =;
        
        //Hashtable table = s.getHashTable();
        //if(!table.containsKey(userName)){
        
        if(!singletonServerChat.dictionariChats.containsKey( userName)){
            FXMLContactWindowController s =ssc.getFXMLContactWindowController();
            s.makeChat(userName, ip);
            ChatWindowController sH = singletonServerChat.dictionariChats.get(userName);
            sH.requestForConversation();
                    
        }else{
            /*
            System.err.println("El server dice que ya existe, nombre a buscar : "+ userName);
            Enumeration<String> elemnts = singletonServerChat.dictionariChats.keys();
            while(elemnts.hasMoreElements()){
               String user = elemnts.nextElement();
               System.out.println("Usuario en lista " + user);
            }*/
        }
       
        return true;
    }

    
    //Algo va mal aqui
     @Override
    public void sendPublicMessage(String userName, String message) throws RemoteException {
       //System.out.println(userName + " : "+ message);
       
        
       singletonServerChat ssh = singletonServerChat.getInstance();
        FXMLContactWindowController x =ssh.getFXMLContactWindowController();
        //ChatWindowController sH = x.getChatWindowController(userName);
        if(singletonServerChat.dictionariChats.containsKey(userName)){
            ChatWindowController sH = singletonServerChat.dictionariChats.get(userName);
            sH.putMessage("\n"+userName + " : "+message);
        }else{
           // System.err.println("No hay chat a donde agregar : ");
        }
        
        
        
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
