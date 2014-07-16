package krabprotocol;

import chat.triggerChat;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import models.DataBaseConnection;
import Cipher.*;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import services.Login;



public class FXMLDocumentController {
   
   private DataBaseConnection myConnection;
   private int count = 0;
   private String currentDate;
   
   
   @FXML
   private TextField userName;
   @FXML
   private PasswordField password;
   
  
    @FXML
    private void signIn(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        if(this.count==0){
            Calendar cal1 = Calendar.getInstance();
            currentDate = ""+cal1.get(Calendar.YEAR)+"-"+cal1.get(Calendar.MONTH)+"-"+cal1.get(Calendar.DATE)+" "+cal1.get(Calendar.HOUR)+":"+cal1.get(Calendar.MINUTE)+":"+cal1.get(Calendar.SECOND);
        
        }
        this.count++;
        if (this.isValid( userName.getText() , password.getText())){ //coloco aqui el password a mano por que no se como obtener el valor del paswordfield
            
            myConnection.checkIn( userName.getText() ,currentDate ,this.count);
            myConnection.closeConnection();
            triggerChat tServerChat = new triggerChat(); 
            tServerChat.start(); //executes the chat server
            
            Sesion s=new Sesion();
            s.start();
            
            singletonChat.userName = userName.getText();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEditProfile.fxml"));

            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage secondStage = new Stage();
            secondStage.setTitle("Main");
            secondStage.setScene(scene);
            ((Node)(event.getSource())).getScene().getWindow().hide();
            secondStage.show();
        
        }else{   
            userName.setText("");
            this.password.setText("");
        }
    }
    
    @FXML
    public void register(ActionEvent event) throws IOException {
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLRegister.fxml"));
           
            Parent root = (Parent) loader.load();
            loader.getController();
            Scene scene = new Scene(root);

            Stage secondStage = new Stage();
            secondStage.setTitle("Main");
            secondStage.setScene(scene);
            ((Node)(event.getSource())).getScene().getWindow().hide();
            secondStage.show();
            
    }
    
    
    private boolean isValid(String userName, String password) throws IOException, NoSuchAlgorithmException{
        myConnection = new DataBaseConnection();
        
         Keys k=new Keys();
        Cipher c=new Cipher();
        String key=userName+password;
        StringBuffer md5pass=c.getmd5(key);
        password = new String(md5pass);
            
        Login ac = new Login();
        return ac.loginUser(userName, password);
    }           
    
}
