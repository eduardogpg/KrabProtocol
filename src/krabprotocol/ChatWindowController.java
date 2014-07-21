/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package krabprotocol;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import chat.*;
import java.net.InetAddress;
import java.rmi.Naming;
import javafx.scene.control.TextArea;
/**
 * FXML Controller class
 *
 * @author 1020142461
 */
public class ChatWindowController implements Initializable {
    
    private String remoteIpForConnect;
    private boolean communicationEstablished;
    
    
   
    @FXML
    Button sendButton;
    @FXML
    TextField messageField;
    @FXML
    TextArea conversationArea;
    
        @Override
        public void initialize(URL url, ResourceBundle rb) {

        }

        public void setIPReceiver(String ip){
            this.remoteIpForConnect = "rmi://"+ip+":1099/myChat";
        }

        @FXML
        public void sendMessage(ActionEvent event) throws IOException {
            if( !this.messageField.getText().equals("")){
                if(this.communicationEstablished == false)
                    this.communicationEstablished = this.setConversation();
                
                this.senPublicMessage(this.messageField.getText());
                
                putYourMessage(this.messageField.getText());
            }

        }
        
        private void putYourMessage(String message){
             this.conversationArea.setText( this.conversationArea.getText() + "\nYou: "+ message+ " ");
             this.messageField.setText("");
             
        }
        private void senPublicMessage(String message){
            
            try{
                chatCommunication newMessage = (chatCommunication)Naming.lookup(this.remoteIpForConnect);
                singletonServerChat ssc = singletonServerChat.getInstance();
                newMessage.sendPublicMessage(ssc.getUserName(), message);
            }catch(Exception e){
                
            }
            
        }
        private boolean setConversation(){
            try{
                chatCommunication newMessage = (chatCommunication)Naming.lookup(this.remoteIpForConnect);
                singletonServerChat ssc = singletonServerChat.getInstance();
                return newMessage.setNewConversation(ssc.getUserName(), InetAddress.getLocalHost().getHostAddress() );
            }catch(Exception e){
                return false;
            }
             
        }
        
        
        public void requestForConversation(){
            this.communicationEstablished = true;
        }
        
       public void putMessage(String userName, String message){
            this.conversationArea.setText( this.conversationArea.getText() + "\n"+userName+":" + message+ " ");
       }
    
}
