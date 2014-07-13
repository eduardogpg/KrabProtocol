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

import help.DataBaseConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FXMLDocumentController {
   
   @FXML
   private TextField userName;
   @FXML
   private PasswordField password;
   
  
    @FXML
    private void signIn(ActionEvent event) throws IOException {
        
        if (this.isValid( userName.getText() , password.getText())){ //coloco aqui el password a mano por que no se como obtener el valor del paswordfield
            triggerChat tServerChat = new triggerChat(); 
            tServerChat.start(); //executes the chat server
            //application.openContactWindow(event);
            
            //System.out.println(userName.getText() +"  " +password.getText());
            //Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLContacWindow.fxml"));

            //Parent root = FXMLLoader.load(getClass().getResource("FXMLContacWindow.fxml"));
            Parent root = (Parent) loader.load();
            //loader.getController();
            Scene scene = new Scene(root);

            Stage secondStage = new Stage();
            secondStage.setTitle("Main");
            secondStage.setScene(scene);
            ((Node)(event.getSource())).getScene().getWindow().hide();
            secondStage.show();
        
        }else{   
            userName.setText("");
            
        }
    }
    
    @FXML
    public void register(ActionEvent event) throws IOException {
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLRegister.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));

            
            Parent root = (Parent) loader.load();
            loader.getController();
            Scene scene = new Scene(root);

            Stage secondStage = new Stage();
            secondStage.setTitle("Main");
            secondStage.setScene(scene);
            ((Node)(event.getSource())).getScene().getWindow().hide();
            secondStage.show();
            
            
    }
    
    
    private boolean isValid(String name, String password){
        DataBaseConnection myConnection = new DataBaseConnection();
        ResultSet myResult = myConnection.searchUser(name);
        
        if (myResult == null)
             return false;
        else try {
            if(myResult.getString("password").equals(password) ){
               myConnection.closConnection();
                return true;
            }else
                return false;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    
}
