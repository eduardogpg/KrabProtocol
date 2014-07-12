

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
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args)  {
        
        triggerChat t = new triggerChat();
        t.start();
        
        launch(args);
        
        /* Prueba de que la base de datos funciona 
        DataBaseConnection ci = new DataBaseConnection();
        ResultSet myResultSet = ci.searchUser("eduardo78d");
            
            if (myResultSet == null)//No Existe el usuario
              System.out.println("nada");
            else{
            try {
                String DBPassword =myResultSet.getString("password");
                System.out.println(DBPassword);
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }*/
        
        /*
            Pruebas de RMI
        */

              
    }
    
}
