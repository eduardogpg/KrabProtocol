package krabprotocol;

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

import help.DataBaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLDocumentController {
   
   @FXML
   private TextField userName;
   private PasswordField password;


    @FXML
    private void signIn(ActionEvent event) throws IOException {
        
        if (this.isValid( userName.getText() , "xxpesar")){ //coloco aqui el password a mano por que no se como obtener el valor del paswordfield
            //application.openContactWindow(event);
        
            //Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLContacWindow.fxml"));

            //Parent root = FXMLLoader.load(getClass().getResource("FXMLContacWindow.fxml"));
            Parent root = (Parent) loader.load();
            //loader.getController();
            Scene scene = new Scene(root);

            Stage secondStage = new Stage();
            secondStage.setTitle("Nueva Ventana");
            secondStage.setScene(scene);
            ((Node)(event.getSource())).getScene().getWindow().hide();
            secondStage.show();
            //FXMLContactWindowController controller = loader.getController();
            //controller.cargar();
        
        }else{   
            userName.setText("");
            
        }
    }
    
    
    private boolean isValid(String name, String password){
        DataBaseConnection myConnection = new DataBaseConnection();
        ResultSet myResult = myConnection.searchUser(name);
        
        if (myResult == null)
             return false;
        else try {
            if(myResult.getString("password").equals(password) )
                return true;
            else
                return false;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    
}
