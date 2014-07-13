

package Cipher;

import java.security.PrivateKey;
import java.security.PublicKey;


public class Cipher {
 
    
     public byte[] PubEncrypt(String data,PublicKey pubk){
        byte []toencrypt=data.getBytes();
        byte []encrypted=null;
       try{
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA");  
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE,pubk);  
        encrypted = cipher.doFinal(toencrypt);        
       }catch(Exception e){
            e.printStackTrace();
         }
       return encrypted;
    }
     
     public byte[] PrivEncrypt(String data,PrivateKey privk){
        byte []toencrypt=data.getBytes();
        byte []encrypted=null;
       try{
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA");  
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE,privk);  
        encrypted = cipher.doFinal(toencrypt);
       }catch(Exception e){
            e.printStackTrace();
         } 
       return encrypted;
    }
     
     public String Pubdecrypt(byte[]data,PublicKey pubk){
          byte[] decrypted = null;  
        
          try {  
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA");  
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, pubk);  
            decrypted = cipher.doFinal(data);  
            String res=new String(decrypted);
            return res;
          } catch (Exception e) {  
            e.printStackTrace();return "Failed to Decrypt"; }  
          
      }
     
     
      public String Privdecrypt(byte[]data,PrivateKey privk){
          byte[] decrypted = null;  
          try {  
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA");  
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, privk);  
            decrypted = cipher.doFinal(data);  
            String res=new String(decrypted);
           // System.out.println("Decrypted Data: " + res); 
            return res;
          } catch (Exception e) {  
            e.printStackTrace();return "Failed to Decrypt"; }    
       }
}
