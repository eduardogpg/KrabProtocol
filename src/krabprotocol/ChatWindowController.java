/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package krabprotocol;


import chat.*;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.rmi.Naming;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
/**
 * FXML Controller class
 *
 * @author 1020142461
 */
public class ChatWindowController implements Initializable {
    
    private String remoteIpForConnect;
    private String nameChat;
    
    private boolean communicationEstablished;
    
    
   
    @FXML
    Button sendButton;
    @FXML
    TextField messageField;
    @FXML
    TextArea conversationArea;
    
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            Image ico = new Image(getClass().getResourceAsStream("img/cangrejo.png"));
            //this.conversationArea.setStyle("-fx-background-color: black;");
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
                
                putMessage("\nYou : " + this.messageField.getText());
                this.messageField.setText("");
            }
            
        }
        
        public void putMessage(String message){
            this.conversationArea.setText( this.conversationArea.getText() +  message );
        
        }
        
        private void senPublicMessage(String message){
            //System.out.println("\n\nEnviar a "+ this.remoteIpForConnect);
            //System.out.println("A usaurio  "+ this.nameChat);
            try{
                chatCommunication newMessage = (chatCommunication)Naming.lookup(this.remoteIpForConnect);
                singletonServerChat ssc = singletonServerChat.getInstance();
                newMessage.sendPublicMessage(this.nameChat, message);
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
        
        public void setNameChat(String name){
            this.nameChat = name;
        }
       
    
}
