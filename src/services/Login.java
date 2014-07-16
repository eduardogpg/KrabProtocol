/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

/**
 *
 * @author 1020142461
 */
public class Login {

    public boolean loginUser(java.lang.String userName, java.lang.String password) {
        org.model.Login_Service service = new org.model.Login_Service();
        org.model.Login port = service.getLoginPort();
        return port.loginUser(userName, password);
    }
    
}
