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
import java.sql.ResultSet;
/**
 * FXML Controller class
 *
 * @author 1020142461
 */
public class FXMLRegisterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    public void registry(){
        if(isValid(this.userName.getText(),this.name.getText(),this.lastName.getText(),this.password.getText(),this.repeatPassword.getText(), cellNumber.getText() , this.email.getText(), this.institution.getText()))
            System.out.println("Lalo");
        else
            System.out.println("No funciono");
    }
    
    private boolean isValid(String userName, String name,String lastName ,String password, String RepeatPassword, String cellNumber, String email, String institutuon){
        try{
            int number = Integer.parseInt(cellNumber);
            if(password.equals(RepeatPassword)){
                DataBaseConnection c = new DataBaseConnection();
                ResultSet myResult = c.searchUser(userName);
                if(myResult == null){
                    return true;
                }else
                    return false;
                
            }else
                return false;
            
        }catch(Exception e){ 
            return false;
        }
    }
}
