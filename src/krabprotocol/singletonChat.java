/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package krabprotocol;

import java.util.Hashtable;

/**
 *
 * @author 1020142461
 */
public class singletonChat {
    
    private singletonChat(){}
     
    private Hashtable<String,String> listChat =new Hashtable<String,String>();
    
    private static singletonChat instanceSingleton = null;
    
    public static singletonChat getInstance() {
        if(singletonChat.instanceSingleton ==null)
            instanceSingleton = new singletonChat();
        return instanceSingleton;
    }
    
}
