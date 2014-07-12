/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package help;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 1020142461
 */
public class DataBaseConnection {
  
    private Connection conexion;
    private Statement statemen;
    private final String base ="KrabProtocol";
    private final String usuario="root";
    private final String password="KJvnje1S7XW73jvEd01E";
    String Estado = "Exitoso";
    

    
    public DataBaseConnection(){
        
       try {
            
           Class.forName("org.gjt.mm.mysql.Driver"); 
           conexion = (Connection) DriverManager.getConnection("jdbc:mysql://89.32.144.231:3306/"+base, usuario, password);
           statemen = (Statement) conexion.createStatement(); 
           System.out.println("Conexion "+Estado);
           
        } catch (ClassNotFoundException ex) {
            Estado = "Error de Driver";
            System.out.println("Conexion "+Estado);
        }catch (SQLException ex) {
            Estado = "Error de Base de datos";
            System.out.println("Conexion "+Estado);
        }
    }
    
}
