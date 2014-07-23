package krabprotocol;

import Cipher.*;
import chat.triggerChat;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.DataBaseConnection;
import org.model.Exception_Exception;
import scanner.triggerServer;
import services.Login;
import services.clientScanner;
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
    private void signIn(ActionEvent event) throws IOException, NoSuchAlgorithmException, Exception_Exception {
        if(this.count==0){
            Calendar cal1 = Calendar.getInstance();
            currentDate = ""+cal1.get(Calendar.YEAR)+"-"+cal1.get(Calendar.MONTH)+"-"+cal1.get(Calendar.DATE)+" "+cal1.get(Calendar.HOUR)+":"+cal1.get(Calendar.MINUTE)+":"+cal1.get(Calendar.SECOND);
        
        }
        this.count++;
        if (this.isValid( userName.getText() , password.getText())){ 
            
            myConnection.checkIn( userName.getText() ,currentDate ,this.count);
            myConnection.closeConnection();
            
            LocateRegistry.createRegistry(1099);
            
            singletonServerChat sc = singletonServerChat.getInstance();
            sc.setUserName(  this.userName.getText()  );
            
        
            webScanner ws = new webScanner();
            
            if (ws.imFirst(userName.getText(), InetAddress.getLocalHost().getHostAddress() )){ //Comenzar El Scanner
                
                System.out.println("Iam the Scanner because Im the First");
                triggerServer tS = new triggerServer();
                tS.run();
            }else{
                System.out.println("Iam the Client because Im No the Firts");
                System.out.println( sc.getIpServer() );
                
            }
            sc.setIpServer( ws.getFisrtIp());        
            
            clientScanner cS = new clientScanner();
            cS.addMeatNetwork( userName.getText() , InetAddress.getLocalHost().getHostAddress() , sc.getIpServer());
            
            this.serverChat(); //Method
            this.sesion( ); //Method
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLContacWindow.fxml"));


            Parent root;
            try {
               root = (Parent) loader.load();
                Scene scene = new Scene(root);

               Stage secondStage = new Stage();
               secondStage.setTitle("Your Count ");
               Image ico = new Image(getClass().getResourceAsStream("img/cangrejo.png"));
               secondStage.getIcons().add(ico);
               secondStage.setScene(scene);
               ((Node)(event.getSource())).getScene().getWindow().hide();
               
               
                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                    
                            webScanner wS = new webScanner();
                            singletonServerChat  sS = singletonServerChat.getInstance();
                            wS.removeUser( sS.getUserName() );
                            
                            
                            cS.removeMe( sS.getUserName(), ws.getFisrtIp() );
                        
                }});
                    
               sc.setFXMLContactWindowController( loader.getController() );
               secondStage.show();  

            } catch (IOException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            //scannerServices ss = new scannerServices();
            //javafx.application.Platform.runLater(ss);
       
        }else{   
            userName.setText("");
            this.password.setText("");
        }
    }
    private void serverChat(){
        triggerChat tServerChat = new triggerChat(); 
        tServerChat.start(); //executes the chat server
    }
    
    private void sesion(){
        Sesion s=new Sesion();
        s.start();
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
    
    private boolean isValid(String userName, String password) throws IOException, NoSuchAlgorithmException, Exception_Exception{
        myConnection = new DataBaseConnection();
        
         Keys k=new Keys();
        Cipher c=new Cipher();
            
        Login ac = new Login();
        ac.negociacion();
        boolean res=ac.loginUser(userName.getBytes(),password.getBytes());
        System.out.println(res);
        return res;
    }           
    
}
