
package krabprotocol;

import java.util.Hashtable;


/**
 *
 * @author 1020142461
 */
public class singletonServerChat {
    
    /*Variables */
    private String currentUserName;
    private String ipServer;
   
   
    static boolean flagTree = true;
    
    static Hashtable userOnline = new Hashtable();
    
    
    private FXMLContactWindowController myController;
    /*End Variables*/
    
    /*****************************************/
    private singletonServerChat(){}
     
    private static singletonServerChat instanceSingleton = null;
    
    public static singletonServerChat getInstance() {
        if(singletonServerChat.instanceSingleton ==null)
            instanceSingleton = new singletonServerChat();
        return instanceSingleton;
    }
    /*****************************************/
    
    public void setFXMLContactWindowController( FXMLContactWindowController controller){
        this.myController = controller;
    }
    
    public FXMLContactWindowController getFXMLContactWindowController(){
        return this.myController;
    }
    
    public void setUserName(String userName){
        this.currentUserName = userName;
    }
    public String getUserName(){
        return this.currentUserName;
    }
    
    
    public void setIpServer(String ipServer){
        this.ipServer = ipServer;
    }
    
    public String getIpServer(){
        return this.ipServer;
    }
   
    
}