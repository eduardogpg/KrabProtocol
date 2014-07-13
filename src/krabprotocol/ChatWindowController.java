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
                this.newConversation();
                this.sendNewMessage();
            }else
                this.sendNewMessage();
        }
          
    }
    
    private void newConversation(){
                
                try{
                    chatCommunication newMessage = (chatCommunication)Naming.lookup(this.remoteName);
                    this.messageStart = newMessage.setConversation("Eduardo", "192.168.1.10");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                
    }
    
    private void sendNewMessage(){
                try{
                    chatCommunication newMessage = (chatCommunication)Naming.lookup(this.remoteName);
                    newMessage.getMessage(this.message.getText());
                    this.conversation.setText( this.conversation.getText() + "\nYou: "+ this.message.getText()+ " ");
                    this.message.setText("");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
    }
    
}
