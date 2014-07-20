/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scanner;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

/**
 *
 * @author eduardo
 */
public class serverScanner extends UnicastRemoteObject implements scannerNetwork {
    
    Hashtable<String,String> dictionariUsers =new Hashtable<String,String>();
    
    public serverScanner() throws RemoteException{
        
        super();
     
    }
        
    public boolean addMe(String userName, String ip) throws RemoteException {
        if(!this.dictionariUsers.containsKey(userName))
            this.dictionariUsers.put(userName, ip);
        return true;
    }

    public Hashtable<String, String> sendList() throws RemoteException {
        return this.dictionariUsers;
    }

    public boolean deleateUser(String userName) throws RemoteException {
        try{
            this.dictionariUsers.remove(userName);
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    
   
}
