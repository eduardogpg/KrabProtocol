package Cipher;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import static java.util.Arrays.copyOfRange;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;


public class Cipher {
 
    
     public byte[] PubEncrypt(byte []toencrypt,PublicKey pubk){
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
     public SealedObject PubEncrypt2(byte []toencrypt,PublicKey pubk){
       byte []encrypted=null;
       SealedObject myEncyptedMessage=null;
       try{
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA");  
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE,pubk);
             myEncyptedMessage = new SealedObject(toencrypt, cipher);
           // encrypted = cipher.doFinal(toencrypt);        
       }catch(Exception e){
            e.printStackTrace();
       }
            return myEncyptedMessage;
    }
     
     public byte[] PrivEncrypt(byte []toencrypt,PrivateKey privk){
       
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
          } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {  
                e.printStackTrace();return "Failed to Decrypt"; }  
          
      }
         public byte[] Pubdecrypt2(byte[]data,PublicKey pubk){
          byte[] decrypted = null;  
        
          try {  
                javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA");  
                cipher.init(javax.crypto.Cipher.DECRYPT_MODE, pubk);  
                decrypted = cipher.doFinal(data);  
               // String res=new String(decrypted);
                return decrypted;
          } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {  
                e.printStackTrace();return null; }  
          
      }
       public byte[] Privdecrypt2(byte[]data,PrivateKey privk){
           byte[] decrypted = null;  
          try {  
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA");  
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, privk);  
            decrypted = cipher.doFinal(data);  
           // String res=new String(decrypted);
           // System.out.println("Decrypted Data: " + res); 
            return decrypted;
          } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {  
            e.printStackTrace();return null; }       
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
          } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {  
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
       byte[] part1;byte[] part2;
      public void ReEncryptPub(byte[]data,PublicKey pub){
          
          byte[] part1;byte[]part2;
          int x=data.length;
          int y=x/2;
          part1=copyOfRange(data,0,y);
          part2=copyOfRange(data, y, x);
          byte []capa2p1=PubEncrypt(part1, pub);
          byte []capa2p2=PubEncrypt(part2, pub);
          this.part1=capa2p1;
          this.part2=capa2p2;
      }
      public void ReEncryptPriv(byte[]data,PrivateKey priv){
          byte[] part1;byte[]part2;
          int x=data.length;
          int y=x/2;
          part1=copyOfRange(data,0,y);
          part2=copyOfRange(data, y, x);
          byte []capa2p1=PrivEncrypt(part1, priv);
          byte []capa2p2=PrivEncrypt(part2, priv);
          this.part1=capa2p1;
          this.part2=capa2p2;
      }
      public byte[] getpart1(){
      return this.part1;
      }
      public byte[] getpart2(){
      return this.part2;
      }
      public byte[] DecryptLayerPriv(byte[]p1,byte[] p2,PrivateKey priv){
        byte[]decp1=Privdecrypt2(p1,priv);
        byte[]decp2=Privdecrypt2(p2,priv);
        //byte[] fin=copyOfRange(decp1,0,decp1.length);
        byte[] fin = new byte[decp1.length + decp2.length];
        System.arraycopy(decp1, 0, fin, 0, decp1.length);
        System.arraycopy(decp2, 0, fin, decp1.length, decp2.length);
        return fin;   
}
      
      public byte[] DecryptLayerPub(byte[]p1,byte[] p2,PublicKey pub){
        byte[]decp1=Pubdecrypt2(p1,pub);
        byte[]decp2=Pubdecrypt2(p2, pub);
        //byte[] fin=copyOfRange(decp1,0,decp1.length);
        byte[] fin = new byte[decp1.length + decp2.length];
        System.arraycopy(decp1, 0, fin, 0, decp1.length);
        System.arraycopy(decp2, 0, fin, decp1.length, decp2.length);
        return fin;
}
}
