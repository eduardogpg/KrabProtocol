/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package help;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import Cipher.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
/**
 *
 * @author 1020142461
 */
public class DataBaseConnection {
  
    private com.mysql.jdbc.Connection myConnection;
    private com.mysql.jdbc.Statement statemen;
    private final String dataBase ="KrabProtocol";
    private final String userDataBase="root";
    private final String password="KJvnje1S7XW73jvEd01E";
    

    
    public DataBaseConnection(){
        
      try {
            Class.forName("org.gjt.mm.mysql.Driver");
            myConnection = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://89.32.144.231:3306/"+dataBase, userDataBase, password);//Make the connection with the dataBase
            statemen = (com.mysql.jdbc.Statement) myConnection.createStatement(); //Make the Statement
        }catch (ClassNotFoundException ex) {
            System.out.println("We have a problem with the connection "+  ex);
        }catch (SQLException ex) {
            System.out.println("We have a problem with the connection "+ ex);
        }
        
    }
    
      public ResultSet Loggin(String name,String pass) throws IOException, NoSuchAlgorithmException{
        try{
            Keys k=new Keys();
            StringBuffer md5pass=k.getmd5pass(name,pass);
            //System.out.println(name+" "+md5pass);
            ResultSet result = this.statemen.executeQuery("SELECT password FROM users WHERE userName='"+name+"'&&"
                    + "password='"+md5pass+"'");
            if(!result.next()){
                    System.out.println("no results were found in the search"); //Only a message for the admin.
                    return null;
             }else
                return result; //Send the result of the search
         
        }catch(SQLException ex){
            return null;
        }
     }
    
      public ResultSet searchUser(String name){
        try{
            ResultSet result = this.statemen.executeQuery("SELECT password FROM users WHERE userName='"+name+"'");
            if(!result.next()){
                    System.out.println("no results were found in the search"); //Only a message for the admin.
                    return null;
             }else
                return result; //Send the result of the search
         
        }catch(SQLException ex){
            return null;
        }
     }
      
     public void closConnection(){
        try {
            this.myConnection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public boolean insert(String userName, String name,String lastName ,String password,String cellNumber, String email, String institution){

         Calendar cal1 = Calendar.getInstance();
         
         String currentlyDate = ""+cal1.get(Calendar.YEAR)+"-"+cal1.get(Calendar.MONTH)+"-"+cal1.get(Calendar.DATE);

                    // generar llaves y encriptar pass
        
         Cipher c=new Cipher();
         Keys k=new Keys();
         StringBuffer md5pass = new StringBuffer();
          
        try {
            System.out.println("Generating keys...");
            k.generatekeys(name);
            md5pass=k.getmd5pass(userName, password);
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Failed to Generate Keys");
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            System.out.println("Failed to Generate Files");
            ex.printStackTrace();
            return false;
        }
//         generar llaves y encriptar pass

         try{
           this.statemen.execute("INSERT INTO `users` VALUES('"+userName+"','"+md5pass+"','"+name+"','"+lastName+"','"+cellNumber+"','"+email+"','"+institution+"','"+currentlyDate+"')"); 
           return true;
         }catch(Exception  ex){
            System.out.println("A problem was accurred " + ex.getMessage());
            return false;
        }
      
     }
      
}