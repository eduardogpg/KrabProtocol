

package krabprotocol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import help.DataBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import chat.triggerChat;

/**
 *
 * @author 1020142461
 */
public class KrabProtocol extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene= new Scene(root);
        
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    /*
    NO se por que aparecio esto :/
    public static void main(String[] args)  {

        @Override
        public void stop() throws Exception{
            super.stop();
         }
    */
    public static void main(String[] args) {
       
        triggerChat tServerChat = new triggerChat(); 
        tServerChat.start(); //executes the chat server
        
   
        /* Prueba de que la base de datos funciona */
        
        launch(args);
        Application.launch(KrabProtocol.class, args); //Para que sirve esto?

              
    }
    
}
