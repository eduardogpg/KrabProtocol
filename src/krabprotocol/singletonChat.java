/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package krabprotocol;

import chat.serverChat;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 1020142461
 */
public class singletonChat {
    
    static String userName;
    
    private singletonChat(){}
     
    private Hashtable<String,String> listChat =new Hashtable<String,String>();
    
    private static singletonChat instanceSingleton = null;
    
    public static singletonChat getInstance() {
        if(singletonChat.instanceSingleton ==null)
            instanceSingleton = new singletonChat();
        return instanceSingleton;
    }
    
    
    public void newConversation(String userName, String ip){
        
        this.listChat.put(userName, ip);
        
    }
    public void makeNewScreen(){
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
            

            Parent root;
         try {
             root = (Parent) loader.load();
             Scene scene = new Scene(root);

            Stage secondStage = new Stage();
            secondStage.setTitle("Main");
            secondStage.setScene(scene);
            //((Node)(event.getSource())).getScene().getWindow().hide();
            secondStage.show();
         } catch (IOException ex) {
             Logger.getLogger(serverChat.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
}
