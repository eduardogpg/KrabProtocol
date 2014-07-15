package Cipher;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.spec.SecretKeySpec;


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
      
      public StringBuffer getmd5(String text) throws IOException, NoSuchAlgorithmException{
            
            String key=text;
            StringBuffer md5pass =new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
            byte[] digest = md.digest();
            for (byte b : digest) {
               md5pass.append(String.format("%02x", b & 0xff));
		}
            //System.out.println(md5pass);
        return md5pass;
    }
      
      public  byte[] Symetricencrypt(String plainText, String encryptionKey) throws Exception {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
            SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
            byte[]encrypted=cipher.doFinal(plainText.getBytes());
            return encrypted;
                }

      public  String Symetricdecrypt(byte[] cipherText, String encryptionKey) throws Exception{
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
            SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
            String decrypted=new String(cipher.doFinal(cipherText));
            return decrypted;
                }
      
}
