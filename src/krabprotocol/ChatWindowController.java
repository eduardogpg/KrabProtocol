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
import java.rmi.Naming;
import javafx.scene.control.TextArea;
/**
 * FXML Controller class
 *
 * @author 1020142461
 */
public class ChatWindowController implements Initializable {
    
    private String remoteName;
    private boolean messageStart;
    
    @FXML
    Button send;
    @FXML
    TextField message;
    @FXML
    TextArea conversation;
    
    @FXML
    TextField ip;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.remoteName = "rmi://localhost:1099/message";
    }
    
    @FXML
    public void sendMessage(ActionEvent event) throws IOException {
        
        if(!this.message.getText().equals("")){
            if (this.messageStart == false){
                this.newConversation(this.ip.getText());
                this.sendNewMessage();
            }else
                this.sendNewMessage();
        }
          
    }
    
    private void newConversation(String ip){
                
                try{
                    chatCommunication newMessage = (chatCommunication)Naming.lookup(this.remoteName);
                    this.messageStart = newMessage.setConversation("Eduardo", ip);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                
    }
    
    private void addYourMessage(String message){
        this.conversation.setText( this.conversation.getText() + "\nYou: "+ message+ " ");
    }
    
    private void newMessage(String userName, String message){
            this.conversation.setText( this.conversation.getText() + "\n"+userName+":"+ message+ " ");
    }
    
    private void sendNewMessage(){
                try{
                    chatCommunication newMessage = (chatCommunication)Naming.lookup(this.remoteName);
                    this.addYourMessage( this.message.getText());
                    newMessage.getMessage(this.message.getText());
                    this.message.setText("");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
    }
    
}
