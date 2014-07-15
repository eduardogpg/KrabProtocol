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

import help.DataBaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import help.DataBaseConnection;
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
public class FXMLEditProfileController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String currentPassword;
    private String newPassword;
    private String profileName;
    private String currentProfileName;
    
    private  DataBaseConnection d;
    
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
        this.loadFile();
    }    
    
    private void loadFile(){
        this.d= new DataBaseConnection();
        ResultSet userD = d.selectAllofUser(singletonChat.userName);
        
        try {
            currentProfileName = userD.getString("userName");
            profileName = this.currentProfileName;
            
    
            this.currentPassword = userD.getString("password");
            this.newPassword = currentPassword;
            
            this.userName.setText( this.currentProfileName );
            this.name.setText( userD.getString("name"));
            this.lastName.setText( userD.getString("lastName"));
            this.cellNumber.setText( userD.getString("cellNumber"));
            this.email.setText( userD.getString("email"));
            this.institution.setText( userD.getString("institution"));
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEditProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
                DataBaseConnection d = new DataBaseConnection();
                if(d.updateUser(profileName, currentProfileName, this.newPassword, this.name.getText(), this.lastName.getText(), this.cellNumber.getText(), this.email.getText(), this.institution.getText())){
                        
                    /***********************************************/
                    /***********************************************/
                    //      Aqui
                    //             Yarib
                    //                      Encripta
                    //                              la variable newPassword
                    //                                  con currentProfilename
                    /***********************************************/
                    /***********************************************/
                    
                        if(!this.currentPassword.equals(newPassword))
                            d.checkChangePassword(currentProfileName, currentPassword,newPassword );
                    
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEditProfile.fxml"));

                        Parent root = (Parent) loader.load();
                        Scene scene = new Scene(root);

                        Stage secondStage = new Stage();
                        secondStage.setTitle("Edit Profile");
                        secondStage.setScene(scene);
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                        secondStage.show();
                }else{
                      System.out.println("Listo");
                }
                  
             
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
       
        if( (userName.equals("")) || (name.equals("")) || (lastName.equals("")) || (cellNumber.equals("")) || (email.equals("")) || (institutuon.equals(""))){
            return false;
        }else{
            
            if((!password.equals("")) || (!RepeatPassword.equals("")) ){
                if ((( password.equals(RepeatPassword) ) && ( password.length() > 6 ) ) ){
                    this.newPassword = password;
                }else{
                    this.xRepeatPassword.setVisible(true);
                    this.xPassword.setVisible(true);
                    return false;
                }
            }
            if (  ( ( this.isNumber(cellNumber))   && (cellNumber.length()>=10)  ) ){
                if(email.contains("@")){
                    if(!this.currentProfileName.equals(userName)){

                        if( this.d.searchUser(userName) == null){
                            this.profileName = userName;
                            return true;
                        }else{
                            this.xUserName.setVisible(true);
                            return false;
                        }                    
                    }
                    return true;
                 
                }else{
                    this.xEmail.setVisible(true);
                    return false;
                }
            }else{
                this.xCellNumber.setVisible(true);
                return false;
            }
            
        }
    }
}
  
    

