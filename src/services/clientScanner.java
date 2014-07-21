/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;


import java.rmi.Naming;
import java.util.Hashtable;
import scanner.scannerNetwork;


/**
 *
 * @author 1020142461
 */
public class clientScanner {
    
    public Hashtable getMembersOnline(String ipServer){
        
        scannerNetwork myScanner;
        try {
            myScanner = (scannerNetwork)Naming.lookup("rmi://"+ipServer+":1099/scanner");
            Hashtable ht = myScanner.sendList();
            return ht;
        } catch(Exception  ex){
            System.err.println(ex);
            return null;
        }
       
    
    }
    
    
    public boolean addMeatNetwork(String userName, String ip, String ipServer){
    
        scannerNetwork myScanner;
        try {
            myScanner = (scannerNetwork)Naming.lookup("rmi://"+ipServer+":1099/scanner");
            myScanner.addMe(userName, ip);
            return true;
        } catch(Exception  ex){
            System.err.println(ex);
            return false;
        }
    
    }
    
    
    public boolean removeMe(String userName, String ipServer){
        scannerNetwork myScanner;
        try {
            myScanner = (scannerNetwork)Naming.lookup("rmi://"+ipServer+":1099/scanner");
            myScanner.deleateUser(userName);
            return true;
        } catch(Exception  ex){
            System.err.println(ex);
            return false;
        }
        
       
    }
}
