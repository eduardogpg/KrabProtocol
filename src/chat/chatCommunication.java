/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author 1020142461
 */
public interface chatCommunication extends Remote{
    
    public void getMessage(String message) throws RemoteException;
    public boolean setConversation(String userName, String ip)throws RemoteException;
}
