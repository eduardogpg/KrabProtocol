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
      
      
}