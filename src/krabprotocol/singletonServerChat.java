
package krabprotocol;

import java.util.Hashtable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 1020142461
 */
public class singletonServerChat {
    
    /*Variables */
    private String currentUserName;
    private FXMLContactWindowController myContactWindowController;
    
    
   
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
    
    
    
    public void setUserName(String userName){
        this.currentUserName = userName;
    }
    public String getUserName(){
        return this.currentUserName;
    }
    
   
    
}