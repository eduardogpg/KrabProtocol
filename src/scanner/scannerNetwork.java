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
    //Se agrega a un nuevo conectado 
    
    public Hashtable<String,String> sendList()  throws RemoteException;
    //Se regresan todos los usuarios conectados a la red junto con su ip
    
    public boolean deleateUser(String userName)throws RemoteException;
    //Se elimina a un usuario y su Ip del HashTable
    
}
