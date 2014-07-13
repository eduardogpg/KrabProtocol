/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package krabprotocol;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import help.DataBaseConnection;
import java.io.IOException;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author 1020142461
 */
public class FXMLRegisterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    DataBaseConnection c;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        c = new DataBaseConnection();
    }    
    @FXML
    private TextField userName;
    
    @FXML
    private TextField name;
    
    @FXML
    private TextField lastName;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField cellNumber;
    
    @FXML
    private TextField institution;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private PasswordField repeatPassword;
    
    @FXML
    public void registry(ActionEvent event) throws IOException {
        
        if(isValid(this.userName.getText(),this.name.getText(),this.lastName.getText(),this.password.getText(),this.repeatPassword.getText(), cellNumber.getText() , this.email.getText(), this.institution.getText())){
            System.out.println("a gurdar") ;
            if (c.insert(this.userName.getText(),this.name.getText(),this.lastName.getText(),this.password.getText(), cellNumber.getText() , this.email.getText(), this.institution.getText())){
                 
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
             }else{
                 System.out.println("Algo salio Mal");
             }
             
        }else{
            this.cellNumber.setText("");
            this.userName.setText("");
            this.password.setText("");
            this.repeatPassword.setText("");
        }   
    
    }  
    
    private boolean isValid(String userName, String name,String lastName ,String password, String RepeatPassword, String cellNumber, String email, String institutuon){
        try{
            int number = Integer.parseInt(cellNumber);
            if(password.equals(RepeatPassword)){
                ResultSet myResult = this.c.searchUser(userName);
                if(myResult == null)
                    return true;
                else
                    return false;
                
            }else
                return false;
            
        }catch(Exception e){ 
            return false;
        }
    }
}
