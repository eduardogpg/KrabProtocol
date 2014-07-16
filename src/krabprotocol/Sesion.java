/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package krabprotocol;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yarib
 */
public class Sesion extends Thread{
     public Sesion(){}
     
     public void run(){
         try {
             sleep(15000);
         } catch (InterruptedException ex) {
             ex.printStackTrace();
         }
         System.out.println("Sesion Expirada");
     }
}
