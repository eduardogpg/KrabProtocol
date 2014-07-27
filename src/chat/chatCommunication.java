

package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author 1020142461
 */
public interface chatCommunication extends Remote{
    
    public boolean setNewConversation(String userName, String ip) throws RemoteException;
    //Se crea una nueva ventana en la máquina del usuario con la que se quiera crear el chat
    
    public boolean sendPublicMessage(String UserName, String message) throws RemoteException;
    //Recibe el mensajey mediante el userName se busca en un Hasttable el controllador de chat y se agrega el mensaje
    
    public boolean connect() throws RemoteException;
   //Método que nos ayuda a saber si el usuario esta en linea
    
}
