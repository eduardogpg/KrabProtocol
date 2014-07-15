/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package krabprotocol;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 1020142461
 */
public class FXMLEditProfileController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label xUserName;
    
    @FXML
    private Label xPassword;
    
    @FXML
    private Label xRepeatPassword;
            
    @FXML
    private Label xEmail;
            
    @FXML
    private Label xCellNumber;
    
    
    
    
    
    
    @FXML
    private TextField userName;
    
    @FXML
    private TextField name;
    
    @FXML
    private TextField lastName;
    
    @FXML
    private TextField password;
    
    @FXML
    private TextField repeatPassword;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField cellNumber;
    
    @FXML
    private TextField institution;
            
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.hideTools();
    }    
    private void hideTools(){
        this.xCellNumber.setVisible(false);
        this.xEmail.setVisible(false);
        this.xPassword.setVisible(false);
        this.xRepeatPassword.setVisible(false);
        this.xUserName.setVisible(false);
    }
    
      
    @FXML
    private void updateProfile(ActionEvent event) throws IOException, NoSuchAlgorithmException {
         this.hideTools();
         if(isValid(this.userName.getText(),this.name.getText(),this.lastName.getText(),this.password.getText(),this.repeatPassword.getText(), cellNumber.getText() , this.email.getText(), this.institution.getText())){
               System.out.println("Entro sin problemas");
           }else{
               System.out.println(":( ");
           }
    }
    
    private boolean isNumber(String numberS){
        
        try{
            double number = Double.parseDouble(numberS);
            return true;
            
        }catch(Exception e){ 
            return false;
           
        }
    
    }
    
    private boolean isValid(String userName, String name,String lastName ,String password, String RepeatPassword, String cellNumber, String email, String institutuon){
       
        if( (userName.equals("")) || (name.equals("")) || (lastName.equals("")) || (password.equals("")) || (RepeatPassword.equals("")) || (cellNumber.equals("")) || (email.equals("")) || (institutuon.equals(""))){
            return false;
        }else{
            
            if (  ( ( this.isNumber(cellNumber))   && (cellNumber.length()>=10)  ) ){
                if ((( password.equals(RepeatPassword) ) && ( password.length() > 6 ) ) ){
                    if(email.contains("@")){
                        return true;
                    }else{
                       this.xEmail.setVisible(true);
                       return false;
                    }
                        
                }else{
                    this.xPassword.setVisible(true);
                    this.xRepeatPassword.setVisible(true);
                    return false;
                }
            }else{
                this.xCellNumber.setVisible(true);
                return false;
            }
            
            
        }
  }
    
}
