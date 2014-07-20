package krabprotocol;

import Cipher.*;
import chat.triggerChat;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
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
import scanner.triggerServer;
import services.Login;
import services.scannerServices;
import services.webScanner;


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
        if (this.isValid( userName.getText() , password.getText())){ 
            
            boolean imServer = false;
            
            myConnection.checkIn( userName.getText() ,currentDate ,this.count);
            myConnection.closeConnection();
            
            LocateRegistry.createRegistry(1099);
            
            
            webScanner ws = new webScanner();
            if (ws.imFirst(userName.getText(), InetAddress.getLocalHost().getHostAddress() )){ //Comenzar El Scanner
                imServer = true;
                triggerServer tS = new triggerServer();
                tS.run();
            }
            
            triggerChat tServerChat = new triggerChat(); 
            tServerChat.start(); //executes the chat server
            
            Sesion s=new Sesion();
            s.start();
            
            singletonServerChat sc = singletonServerChat.getInstance();
            sc.setUserName( this.userName.getText() );
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLContacWindow.fxml"));
            
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage secondStage = new Stage();
            secondStage.setTitle("Your Count ");
            secondStage.setScene(scene);
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            secondStage.show();
            
            scannerServices ss = new scannerServices();
            javafx.application.Platform.runLater(ss);
       
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
            secondStage.setTitle("Register");
            secondStage.setScene(scene);
            ((Node)(event.getSource())).getScene().getWindow().hide();
            secondStage.show();
            
    }
    @FXML
     public void showCredits(ActionEvent event) throws IOException {
         
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Credits.fxml"));
           
            Parent root = (Parent) loader.load();
            loader.getController();
            Scene scene = new Scene(root);

            Stage secondStage = new Stage();
            secondStage.setTitle("Credits");
            secondStage.setScene(scene);
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
        //return ac.loginWithDB(userName, password);
    }           
    
}
