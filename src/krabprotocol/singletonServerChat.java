
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
    private Hashtable<String,String> dictionarieListChat =new Hashtable<String,String>(); 
    static Hashtable<String,ChatWindowController> dictionarieControllerChat =new Hashtable<String,ChatWindowController>(); 
    
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
    
    
    public void makeNewChat(String userName, String ip){
       if(!this.dictionarieListChat.containsKey(userName)){
           
           Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    Stage secondStage = new Stage();

                    secondStage.setTitle("New chat with :"+userName);
                    secondStage.setScene(scene);
                    
                    ChatWindowController c = loader.getController();
                    c.setIPReceiver(ip);
                    c.requestForConversation();
                    
                    singletonServerChat.dictionarieControllerChat.put(userName, c);
                    
                    secondStage.show();

                } catch (Exception ex) {
                    System.err.println(ex);
                }
                }
            });
           this.addNewChat(userName, ip);
       }
        
        
    }
    
    public void addNewChat(String userName, String ip){
        if(!this.dictionarieListChat.containsKey(userName))
            this.dictionarieListChat.put(userName, ip);
    }
      
   
   
    
}