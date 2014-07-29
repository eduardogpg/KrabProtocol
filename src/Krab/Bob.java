/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Krab;

import Cipher.Cipher;
import Cipher.Keys;
import java.io.IOException;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import krabprotocol.FXMLDocumentController;

/**
 *
 * @author Yarib
 */
public class Bob extends UnicastRemoteObject implements Krab{
    
    BigInteger Amod=null,Aexp=null;
    String Na=null,Nb=null,Kab=null,Alice;
    Keys k=new Keys();
    Cipher c=new Cipher();
      String BobName,BobPass;
      
        public Bob(String Bname,String Bpass) throws RemoteException{
            super();
           // LocateRegistry.createRegistry(1099);
            this.BobName=Bname;
            this.BobPass=Bpass;
        }
    @Override
    public BigInteger getmod(String name) throws RemoteException{
        Keys k=new Keys();
        try {
            k.getPubKey(name);
            return k.getModulus();
        } catch (IOException ex) {
           ex.printStackTrace();
           return null;
        }
    }
    @Override
    public BigInteger getexp(String name) throws RemoteException{
         Keys k=new Keys();
        try {
            k.getPubKey(name);
            return k.getExponent();
        } catch (IOException ex) {
           ex.printStackTrace();
           return null;
        }
    }

    @Override
    public void sendmod(String Alice,BigInteger Amod) throws RemoteException{
        this.Amod=Amod;
        this.Alice=Alice;
        //System.out.println("getting Alice mod");
    }

    @Override
    public void sendexp(BigInteger Aexp) throws RemoteException{
        this.Aexp=Aexp;
       // System.out.println("getting Alice exp");
    }
    
    @Override
    public void sendnounce(byte[] nounce1,byte[]nounce2) throws RemoteException{
        
         PublicKey Apub=k.genpubkey(Amod, Aexp);
         //System.out.println("Alice pub key generated");
         PrivateKey Bpriv=null;
        try {
             Bpriv=k.getprivKey(BobName,BobPass);
          //   System.out.println("Bob Private key generated");
             if(Bpriv==null){System.out.println("Failed to get Bob priv key");}
        } catch (IOException ex) {
           ex.printStackTrace();
        }
         byte[]EncNounce=c.DecryptLayerPub(nounce1, nounce2,Apub);
         byte[]Nounce=c.Privdecrypt2(EncNounce,Bpriv);
         Na=new String(Nounce);
       //  System.out.println(Na);
    }
    byte[]part1,part2; 
    PublicKey Apub;PrivateKey Bpriv;
    @Override
    public void Step2()throws RemoteException{
        try {
            Nb=GenNounce().toString();
            //System.out.println("Nb: "+Nb);
            Apub=k.genpubkey(Amod, Aexp);
            Bpriv=k.getprivKey(BobName, BobPass);
            StringBuffer hashNa=c.getmd5(Na);
            String tosend=new String(hashNa+"#"+Nb);
            byte[] layer1=c.PubEncrypt(tosend.getBytes(), Apub);
            c.ReEncryptPriv(layer1, Bpriv);
            this.part1=c.getpart1();
            this.part2=c.getpart2();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        
}
    @Override
    public String sendkey(byte[] nonce) throws RemoteException{
        return null;
    }
    
     SecureRandom random = new SecureRandom();
      private BigInteger GenNounce() {
       BigInteger n=new BigInteger(90,random);
       return n;
    }

    @Override
    public byte[] getNb1() throws RemoteException {
        return this.part1;
    }

    @Override
    public byte[] getNb2() throws RemoteException {
       return this.part2;
    }
    @Override
    public void sendKab(byte []Kab1,byte []Kab2,byte[] KabHash1){
        try {
            byte[] kabhash=c.Pubdecrypt2(KabHash1,Apub);
            String HashKab=new String (kabhash);
            byte[] Dkab=c.DecryptLayerPub(Kab1, Kab2, Apub);
            byte[]Fkab=c.Privdecrypt2(Dkab, Bpriv);
            String kab=new String (Fkab);
            StringBuffer mykabhash=c.getmd5(kab);
            String hash=new String(mykabhash);
            if(hash.equals(HashKab)){
         //   FXMLDocumentController.Kab=new String (mykabhash);
            FXMLDocumentController.kabs.put(Alice, mykabhash);
            System.out.println("B Kab: "+HashKab);}
            else{System.err.println("Kab Recived Invalid");}
            
        } catch (IOException | NoSuchAlgorithmException ex) {
            System.err.println("failed to Decrypt kab");
        }
    }
    
      
}
