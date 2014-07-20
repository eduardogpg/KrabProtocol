
package krabprotocol;



/**
 *
 * @author Yarib
 */
public class Sesion extends Thread{
     public Sesion(){}
     
     public void run(){
         
         try {
             
            sleep(60000);
             
         } catch (InterruptedException ex) {
             ex.printStackTrace();
         }
         System.out.println("Sesion Expirada");
     }
}
