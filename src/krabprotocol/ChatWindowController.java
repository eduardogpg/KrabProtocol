/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package krabprotocol;


import Krab.Alice;
import chat.*;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.rmi.Naming;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    private boolean ChanelSecure = false;
    
    @FXML
    Button sendButton;
    @FXML
    TextField messageField;
    @FXML
    TextArea conversationArea;
    
    private boolean primeraVez= false;
        
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            Image ico = new Image(getClass().getResourceAsStream("img/cangrejo.png"));
        }
        
        public void setNameChat(String name){
            this.nameChat = name;
        }
        String ip;
        public void setIPReceiver(String ip){
            this.ip=ip;
            this.remoteIpForConnect = "rmi://"+ip+":1099/myChat";
        }

        @FXML
        public void sendMessage(ActionEvent event) throws IOException, NoSuchAlgorithmException {
            
            if (this.ChanelSecure == false){
                if( this.ping()){
                    this.ChanelSecure = true;
                    makeARemoteWindows();
                    this.preparingTextArea();
                }  
            }else{
                if(!this.messageField.getText().equals(""))
                    sendMessage( this.messageField.getText() );
                
                putMessage("\nyou : "+ this.messageField.getText());
                this.messageField.setText("");
            }
            
        }
        
        public void sendMessage(String message){
            
            try{
                chatCommunication sendMessage = (chatCommunication)Naming.lookup(this.remoteIpForConnect);
                singletonServerChat ssC = singletonServerChat.getInstance();
                if(sendMessage.sendPublicMessage(ssC.getUserName(), message) == true){}else{
                    this.putMessage("\n\nEl usuario a dejado la conversación, Es necesario crear una nueva sala");
                }
                
            }catch(Exception ex){
                //Creo que es necesario crear un hilo, esta pasando lo mismo que paso con el chat de Yarib
                //Se queda esperando, aun que si llega el true o false 
            }
            
        }
        
        public boolean ping(){
            try{
                chatCommunication Ping = (chatCommunication)Naming.lookup(this.remoteIpForConnect);
                return Ping.connect();
                
            }catch(Exception ex){
                System.err.println(ex);
                return false;
            }
            
        }
        
        public boolean makeARemoteWindows(){
            try{
                chatCommunication remoteWindows = (chatCommunication)Naming.lookup(this.remoteIpForConnect);
                singletonServerChat singletonChat = singletonServerChat.getInstance();

                                
                return remoteWindows.setNewConversation(  singletonChat.getUserName(), InetAddress.getLocalHost().getHostAddress() );
                
            }catch(Exception ex){
               return false;
            }
            
        }
        
        
        
        public void putMessage(String message){
            this.conversationArea.setText( this.conversationArea.getText() +  message );
            updateTextArea();
        }
        
        public void addHeader(String ip){
            this.conversationArea.setText("Chat with : "+ip+"\nFor establish secure channel to send an empty message");
            
        }
        
        
        public void updateTextArea(){
              this.conversationArea.setText( this.conversationArea.getText() );
        }
        public void preparingTextArea(){
            this.conversationArea.setText( "------------------------------------");
        }
        
        public void setChanelSecure(){
            this.ChanelSecure = true;
        }
    
}
