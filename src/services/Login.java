

package services;

/**
 *
 * @author 1020142461
 */
import Cipher.Cipher;
import Krab.Alice;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.DataBaseConnection;
import org.model.Exception_Exception;

public class Login {

      public boolean loginWithDB(String userName, String Password){
        DataBaseConnection c = new DataBaseConnection();
        ResultSet r = c.searchUser(userName);        
        try {
            if (Password.equals(r.getString("password")))
                return true;
            else
                return false;
        } catch (SQLException ex) {
           return false;
        }
    }

    public  void negociacion() {
        org.model.Login_Service service = new org.model.Login_Service();
        org.model.Login port = service.getLoginPort();
        port.negociacion();
    }

  

   

    public  boolean loginUser(byte[] userName, byte[] password) throws Exception_Exception {
          try {
             // String url="rmi://yaroc:1099/Bob";
              Cipher c=new Cipher();
              Alice a=new Alice();
              String user=new String(userName);
              String pass=new String(password);
              a.init(user, pass, "Server", "yaroc");
              String kab=a.getKab();
              System.out.println("kab from a"+kab);
              byte[]Ename=c.Symetricencrypt(user, kab);
              byte[]Epass=c.Symetricencrypt(pass, kab);
              System.out.println("name & pass encrypted with kab");
              org.model.Login_Service service = new org.model.Login_Service();
              org.model.Login port = service.getLoginPort();
              return port.loginUser(Ename, Epass);
          } catch (Exception ex) {
             return false;
          }
    }
}
