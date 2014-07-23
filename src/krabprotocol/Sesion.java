
package krabprotocol;



/**
 *
 * @author Yarib
 */
public class Sesion extends Thread{
     public Sesion(){}
     
     public void run(){
         
         try {
             
            sleep(20000);
             
         } catch (InterruptedException ex) {
             ex.printStackTrace();
         }
        singletonServerChat ssC = singletonServerChat.getInstance();
        FXMLContactWindowController controller = ssC.getFXMLContactWindowController();
        
        controller.reload();
        
     }
}
