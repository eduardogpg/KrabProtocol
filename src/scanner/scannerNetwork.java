/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scanner;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

/**
 *
 * @author eduardo
 */
public interface scannerNetwork extends Remote{
    
    public boolean addMe(String userName, String ip) throws RemoteException;
    public Hashtable<String,String> sendList()  throws RemoteException;
    public boolean deleateUser(String userName)throws RemoteException;
}
