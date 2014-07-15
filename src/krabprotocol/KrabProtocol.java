
package krabprotocol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import help.DataBaseConnection;

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

    public static void main(String[] args) {
     
        launch(args);
        Application.launch(KrabProtocol.class, args); //Para que sirve esto?

        
        
    }
    
}
