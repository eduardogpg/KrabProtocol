/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cipher;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yarib
 */
public class Keys {
    
    
    
    public boolean generatekeys(String name,String pass) throws NoSuchAlgorithmException, IOException, Exception{
        PrivateKey privateKey;PublicKey publicKey;
        String pubfile=getpubfile(name);
        String privfile=getprivfile(name);
        String user=System.getProperties().getProperty("user.name");
        String dir="C:\\Users\\"+user+"\\Documents\\Krab\\Keys\\"+name;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair keypair = keyGen.genKeyPair();
            privateKey = keypair.getPrivate();
            publicKey = keypair.getPublic();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            BigInteger PubModulus=rsaPubKeySpec.getModulus();
            BigInteger PubExponent=rsaPubKeySpec.getPublicExponent();
            BigInteger PrivModulus=rsaPrivKeySpec.getModulus();
            BigInteger PrivExponent=rsaPrivKeySpec.getPrivateExponent();
            boolean s1=this.storepubkey(dir,pubfile,PubModulus,PubExponent);
            boolean s2=this.storeprivkey(dir,privfile,PrivModulus,PrivExponent,pass);
          //  this.CipherPrivKey();
            if(s1==true&&s2==true){return true;}
            else{return false;}
        } catch (InvalidKeySpecException ex) {
           ex.printStackTrace();return false;
        }
    }
    private boolean storeprivkey(String dir,String fileName,BigInteger Mod,BigInteger Exp,String pass) throws IOException, Exception{
       FileOutputStream Fios = null;  
       ObjectOutputStream Obos = null;
       Cipher c=new Cipher();
       String mo=Mod.toString();
       String ex=Exp.toString();
       StringBuffer k=c.getmd5(pass);
       byte[]mod=c.Symetricencrypt(mo, new String (k));
       byte[]exp=c.Symetricencrypt(ex, new String (k));
       try{
         File Dir= new File(dir);
         if(!Dir.exists()){
             if(Dir.mkdirs()){System.out.println(dir+" Created");}
             else{System.out.println("Failed to create");return false;}
         }
       }catch(Exception e){e.printStackTrace();}
       
       try {  
         Fios = new FileOutputStream(fileName);  
         Obos = new ObjectOutputStream(new BufferedOutputStream(Fios));  
         Obos.writeObject(mod);  
         Obos.writeObject(exp); 
         return true;
        }catch (Exception e) {  
            e.printStackTrace(); 
            return false;
        }finally{  
         if(Obos != null&&Fios!=null){  
           Obos.close();Fios.close();  }}       
    }
        private boolean storepubkey(String dir,String fileName,BigInteger Mod,BigInteger Exp) throws IOException{
       FileOutputStream Fios = null;  
       ObjectOutputStream Obos = null;
       try{
         File Dir= new File(dir);
         if(!Dir.exists()){
             if(Dir.mkdirs()){System.out.println(dir+" Created");}
             else{System.out.println("Failed to create");return false;}
         }
       }catch(Exception e){e.printStackTrace();}    
       try {  
         Fios = new FileOutputStream(fileName);  
         Obos = new ObjectOutputStream(new BufferedOutputStream(Fios));  
         Obos.writeObject(Mod);  
         Obos.writeObject(Exp); 
         return true;
        }catch (Exception e) {  
            e.printStackTrace(); 
            return false;
        }finally{  
         if(Obos != null&&Fios!=null){  
           Obos.close();Fios.close();  }}       
    }
        BigInteger Modulus=null,Exponent=null;
    public PublicKey getPubKey(String name) throws IOException{
       FileInputStream fi = null;  
       ObjectInputStream oi = null;  
       String pubfile=getpubfile(name);
       Cipher c=new Cipher();
      try {  
       fi = new FileInputStream(new File(pubfile));  
       oi = new ObjectInputStream(fi);  
       BigInteger modulus = (BigInteger) oi.readObject();  
       BigInteger exponent = (BigInteger) oi.readObject(); 
       this.Modulus=modulus;
       this.Exponent=exponent;
       PublicKey publicKey = this.genpubkey(modulus, exponent);
       return publicKey;  
        
          } catch (Exception e) {  
             return null;
        }finally{
      if(oi != null&&fi!=null){  
         oi.close();fi.close();  }}  
  
        }
      public BigInteger getModulus(){
      return this.Modulus;}
      
      public BigInteger getExponent(){
      return this.Exponent;}
      
    public PrivateKey getprivKey(String name,String pass) throws IOException{
       FileInputStream fi = null;  
       ObjectInputStream oi = null;  
       String pubfile=getprivfile(name);
       Cipher c=new Cipher();
      try {  
       fi = new FileInputStream(new File(pubfile));  
       oi = new ObjectInputStream(fi);  
       byte encryptedexp[]=(byte[])oi.readObject();
       byte encryptedmod[]=(byte[])oi.readObject();
       StringBuffer k=c.getmd5(pass);
       String m=c.Symetricdecrypt(encryptedexp, new String(k));
       String e=c.Symetricdecrypt(encryptedmod, new String(k));
       BigInteger modulus=new BigInteger(m);
       BigInteger exponent=new BigInteger(e);
       PrivateKey privateKey = this.genprivkey(modulus, exponent);
       return privateKey;  
          } catch (Exception e) {  
             return null;
        }finally{
      if(oi != null&&fi!=null){  
         oi.close();fi.close();  }}        
        }
    
    public PrivateKey genprivkey(BigInteger modulus,BigInteger exponent){
        try {
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = fact.generatePrivate(rsaPrivateKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
            return null;
        }
    
    }
    public PublicKey genpubkey(BigInteger modulus,BigInteger exponent){
        try {
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PublicKey publicKey = fact.generatePublic(rsaPublicKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
            return null;
        }
    
    }
    
    private String getpubfile(String name){
        String user=System.getProperties().getProperty("user.name");
        String pubfile="C:\\Users\\"+user+"\\Documents\\Krab\\Keys\\"+name+"\\Kpub.cb";
    return pubfile;
    }
    private String getprivfile(String name){
        String user=System.getProperties().getProperty("user.name");
        String privfile="C:\\Users\\"+user+"\\Documents\\Krab\\Keys\\"+name+"\\Kpriv.cb";
    return privfile;
    }
    
    
    }
