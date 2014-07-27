

package services;

import chat.chatCommunication;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import scanner.scannerNetwork;

/**
 *
 * @author 1020142461
 */
public class triggerScannerServices extends Thread{
    
    public void run(){
        
        while(true){
            
            try{
                Thread.sleep(1000);//Only 5 sec
                scannerNetwork myScanner = (scannerNetwork)Naming.lookup("rmi://localhost:1099/scanner");
                Hashtable ht = myScanner.sendList();
                
                if(ht.size()!=0){
                    
                    Enumeration<String> elemnts = ht.keys();
                    
                    while(elemnts.hasMoreElements()){

                        String user = elemnts.nextElement();
                        String address = "rmi://"+ ht.get(user) +":1099/myChat";

                        try{
                            chatCommunication newMessage = (chatCommunication)Naming.lookup(address);
                            try{
                                if(newMessage.connect()){
                                
                                }else{
                                   myScanner.deleateUser(user);
                                }
                                
                            }catch (Exception exx){
                                myScanner.deleateUser(user);
                            }

                        }catch(MalformedURLException | NotBoundException | RemoteException e){
                           System.err.println(e);
                           myScanner.deleateUser(user);
                           System.out.println("Borrado "+ user);
                        }

                    } //Fin de while
                }else{
                    System.out.println("Si entra al Hilo, pero no muestra nada");
                }
                
            }catch(Exception ex){
                System.err.println(ex);
            }
            
                
            
        
        }
    
    }
    
    
}
