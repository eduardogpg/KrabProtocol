package krabprotocol;

import Cipher.*;
import Krab.Bob;
import chat.triggerChat;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
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
import services.triggerScannerServices;

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
            
            myConnection.checkIn( userName.getText() ,currentDate ,this.count);//Hisotrico de Logins
            myConnection.closeConnection(); //Cerramos la conexión para evitar ataques
            
            LocateRegistry.createRegistry(1099); 
            LocateRegistry.createRegistry(2099); 
            
        
            //Consumimos el servicio en la maquina de Yarib
            webScanner ws = new webScanner();
            

            if (true==false){//ws.imFirst(userName.getText(), InetAddress.getLocalHost().getHostAddress() )){ 
    
                System.out.println("Iam the Scanner because Im the First");
                triggerServer tS = new triggerServer();
                tS.run();//Comenzamos el Scanner
                
                triggerScannerServices ttS = new triggerScannerServices();
                ttS.start();
                singletonServerChat.imScanner = true;
                
            }else{
                System.out.println("Iam the Client because Im No the Firts");
                singletonServerChat.imScanner = false;
            }
            //"Variables de sesión"
            singletonServerChat sc = singletonServerChat.getInstance(); 
            sc.setUserName(  this.userName.getText()  ); 
            sc.setPassword( this.password.getText() );
            
            
            sc.setIpServer("192.168.0.102"); //InetAddress.getLocalHost().getHostAddress() );
            


            //sc.setIpServer( ws.getFisrtIp());     
            
            
            //Agregamos al usuario en la red, incluyendo si es el Scanner o no
            clientScanner cS = new clientScanner();
            if(cS.addMeatNetwork( userName.getText() , InetAddress.getLocalHost().getHostAddress() , sc.getIpServer())){
                System.err.println("Comenzamos el servior de chat");
                this.serverChat(); //Comenzamos el servidor de Chat
                //this.sesion( ); //Comenzamos la sesion
            
            }
            
            
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
                            
                            
                        singletonServerChat  sS = singletonServerChat.getInstance();
                        cS.removeMe( sS.getUserName(), sS.getIpServer());
                        secondStage.close();
                        
                        
                        //webScanner wS = new webScanner();
                        //wS.removeUser( sS.getUserName() );
                            
                        System.exit(1);
                            
                        
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
        
        /*
        Keys k=new Keys();
        Cipher c=new Cipher();
            
        Login ac = new Login();
        ac.negociacion();
        boolean res=ac.loginUser(userName.getBytes(),password.getBytes());
        System.out.println(res);
        return res;
                */
        return true;

    }           
      Bob b;public static String myname,mypass;
      public void listener(String name,String pass){
        try {
            FXMLDocumentController.myname=name;FXMLDocumentController.mypass=pass;
            b=new Bob(name,pass);// nombre y pass del usuario loggeado
            String url="rmi://localhost:1099/Bob";
            Naming.rebind(url,b);
            System.out.println("Rmi for Krab Waiting...");
        } catch (RemoteException | MalformedURLException ex) {
           ex.printStackTrace();
        }
   }
    
}
