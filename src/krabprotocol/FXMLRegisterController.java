package krabprotocol;

import Cipher.Cipher;
import Cipher.Keys;
import chat.triggerChat;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import models.DataBaseConnection;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class FXMLRegisterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    DataBaseConnection c;
    
    @FXML
    private Label xUserName;
    
    @FXML
    private Label xPassword;
    
    @FXML
    private Label xRepeatPassword;
    
    @FXML
    private Label xCellNumber;
    
    @FXML
    private Label xEmail;
    
    @FXML
    private Label alert;
    
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cellNumber.setOnKeyTyped(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                char kt = event.getCharacter().charAt(0);
                if(!Character.isDigit(kt))
                    event.consume();
            }
        });
        this.hideTools();
        c = new DataBaseConnection();
    }
    private void hideTools(){
        this.xUserName.setVisible(false);
        this.xCellNumber.setVisible(false);
        this.xPassword.setVisible(false);
        this.xRepeatPassword.setVisible(false);
        this.xEmail.setVisible(false);
        this.alert.setVisible(false);
    }
    
    @FXML
    public void registry(ActionEvent event) throws IOException {
        this.hideTools();
        
        if(isValid(this.userName.getText(),this.name.getText(),this.lastName.getText(),this.password.getText(),this.repeatPassword.getText(), cellNumber.getText() , this.email.getText(), this.institution.getText())){

         // generar llaves y encriptar pass
         Cipher ci=new Cipher();
         Keys k=new Keys();
         StringBuffer md5pass = new StringBuffer(); 
         
        try {
            System.out.println("Generating keys...");
            k.generatekeys(userName.getText(),password.getText());
            String key=userName.getText()+password.getText();
            md5pass=ci.getmd5(key);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Failed to Generate Keys");
            ex.printStackTrace(); 
        } catch (IOException ex) {
            System.out.println("Failed to Generate Files");
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        //         generar llaves y encriptar pass
   
         if (c.register(this.userName.getText(),this.name.getText(),this.lastName.getText(),new String(md5pass), cellNumber.getText() , this.email.getText(), this.institution.getText())){
             c.closeConnection();

                
                triggerChat t = new triggerChat();
                t.start();
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLContacWindow.fxml"));

                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);

                Stage secondStage = new Stage();
                secondStage.setTitle("Main");
                secondStage.setScene(scene);
                secondStage.setMinHeight(400);
                secondStage.setMinWidth(500);
                ((Node)(event.getSource())).getScene().getWindow().hide();
                secondStage.show();
            
            }else{
                this.alert.setVisible(true);
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
       
        if( (userName.equals("")) || (name.equals("")) || (lastName.equals("")) || (password.equals("")) || (RepeatPassword.equals("")) || (cellNumber.equals("")) || (email.equals("")) || (institutuon.equals(""))){
            return false;
        }else{
            
            if (  ( ( this.isNumber(cellNumber))   && (cellNumber.length()>=10)  ) ){
                this.xCellNumber.setVisible(false);
                this.alert.setVisible(false);
                if ((( password.equals(RepeatPassword) ) && ( password.length() > 6 ) ) ){
                    this.xPassword.setVisible(false);
                    this.alert.setVisible(false);
                    if(email.contains("@")){
                        if( this.c.searchUser(userName) == null)
                            return true;
                        else
                            return false;
                    }else{
                       this.xEmail.setVisible(true);
                       this.alert.setText("E-mail Incorrecto");
                       this.alert.setVisible(true);
                       return false;
                    }
                        
                }else{
                    this.xPassword.setVisible(true);
                    this.xRepeatPassword.setVisible(true);
                    this.alert.setText("Contraseñas muy cortas");
                    this.alert.setVisible(true);
                    return false;
                }
            }else{
                this.xCellNumber.setVisible(true);
                this.alert.setText("Numero de celular incorrecto");
                return false;
            }
            
            
        }
  }
}

