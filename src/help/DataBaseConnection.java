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
    private final String userDataBase="wawser";//"root";
    private final String password="h7u7zDTYUzS6pAXB";//"KJvnje1S7XW73jvEd01E";
    

    
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
            Cipher c=new Cipher();
            String key=name+pass;
            StringBuffer md5pass=c.getmd5(key);
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
     
    public ResultSet selectAllUser(String user){
        
         try{
            ResultSet result = this.statemen.executeQuery("SELECT * FROM users WHERE userName='"+user+"'");
            if(!result.next()){
                    System.out.println("no results were found in the search"); 
                    return null;
             }else
                return result; //Send the result of the search
         
        }catch(SQLException ex){
            return null;
        }
         
     }
     
    public boolean register(String userName, String name,String lastName ,String password,String cellNumber, String email, String institution){

         Calendar cal1 = Calendar.getInstance();
         
         String currentDate = ""+cal1.get(Calendar.YEAR)+"-"+cal1.get(Calendar.MONTH)+"-"+cal1.get(Calendar.DATE);

         try{
           this.statemen.execute("INSERT INTO `users` VALUES('"+userName+"','"+password+"','"+name+"','"+lastName+"','"+cellNumber+"','"+email+"','"+institution+"','"+currentDate+"')"); 
           return true;
         }catch(Exception  ex){
            System.out.println("A problem was accurred " + ex.getMessage());
            return false;
        }
      
     }
    
    public boolean checkIn(String userName,String timeFirstTry ,int tries){
        
        Calendar cal1 = Calendar.getInstance();
        String currentDate = ""+cal1.get(Calendar.YEAR)+"-"+cal1.get(Calendar.MONTH)+"-"+cal1.get(Calendar.DATE)+" "+cal1.get(Calendar.HOUR)+":"+cal1.get(Calendar.MINUTE)+":"+cal1.get(Calendar.SECOND);
        try{
           this.statemen.execute("INSERT INTO `historicAccessTable` VALUES('"+userName+"','"+timeFirstTry+"','"+currentDate+"','"+tries+"')"); 
           return true;
         }catch(Exception  ex){
            System.out.println("A problem was accurred at the table historicAccessTable" + ex.getMessage());
            return false;
        }
        
    }
     
}