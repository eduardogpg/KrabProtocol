/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package krabprotocol;

import Cipher.Cipher;
import Cipher.Keys;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import org.model.Exception_Exception;
import services.Login;
import services.webScanner;

/**
 * FXML Controller class
 *
 * @author 1020142461
 */
public class AlertController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
       
    @FXML
    public void aceptar(ActionEvent event) throws IOException, Exception_Exception {
    
        Keys k=new Keys();
        Cipher c=new Cipher();
            
        Login ac = new Login();
        ac.negociacion();
        singletonServerChat ssC = singletonServerChat.getInstance();
        
        boolean res=ac.loginUser(ssC.getUserName().getBytes(),ssC.getPassword().getBytes());
        System.out.println(res);
        
        Sesion s = new Sesion();
        s.start();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
       
    @FXML
    public void rechazar(ActionEvent event) throws IOException {
        
        webScanner wS = new webScanner();
        singletonServerChat  sS = singletonServerChat.getInstance();
        wS.removeUser( sS.getUserName() );
                           
                            
        System.exit(1);
        
    }
}