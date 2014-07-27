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
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yarib
 */
public class Alice {
    String Na,Nb,Kab;
    public void init(String name,String pass,String bob,String host) throws NoSuchAlgorithmException{
        String url="rmi://"+host+":1099/Bob";
        Keys k=new Keys();
        Cipher c=new Cipher();
        try {     
            System.out.println("Alice Started");
            Krab b=(Krab)Naming.lookup(url);
            BigInteger m=b.getmod(bob);
            BigInteger e=b.getexp(bob);
            PublicKey Bpub=k.genpubkey(m,e);
            if(Bpub==null){System.out.println("Failed to generate Bob pub key");}
            PrivateKey Aprivkey=k.getprivKey(name, pass);
            PublicKey Apubkey=k.getPubKey(name);
            if(Aprivkey==null||Apubkey==null||Bpub==null){
            System.err.println("Failed to get Key");
            }else{
            b.sendexp(k.getExponent());
            b.sendmod(k.getModulus());
            //System.out.println("Sending Alice pub Key");
            BigInteger nounce=this.GenNounce();
            Na=nounce.toString();
            System.out.println(Na);
            byte[] nl1=c.PubEncrypt(Na.getBytes(), Bpub);//capa 1 nounce encriptado con llave publica de bob
            c.ReEncryptPriv(nl1, Aprivkey);// capa 2 encriptado con la llave privada de alice
            byte[] npart1=c.getpart1();
            byte[] npart2=c.getpart2();
            b.sendnounce(npart1,npart2);// se envia el nounce encriptado en 2 capas en 2 partes
            b.Step2();
            byte[]Nb1=b.getNb1();
            byte[]Nb2=b.getNb2();
            byte[]Nb=c.DecryptLayerPub(Nb1, Nb2, Bpub);
            byte[]DecNb=c.Privdecrypt2(Nb, Aprivkey);
            String All=new String(DecNb);
            String[] split;
            split=All.split("#");
            String hashNa=split[0];
            String Nbs=split[1];
         //   System.out.println("Hash Na: "+new String(c.getmd5(Na)));
           //System.out.println("Recibed Na Hash: "+hashNa);
          if(hashNa.equals(new String(c.getmd5(Na)))){
            //System.out.println("Na Valid");
            BigInteger kab=generateKab(Na,Nbs);
            Kab=kab.toString();
            String Kabhash=new String(c.getmd5(kab.toString()));
            byte[] enckab=c.PubEncrypt(Kab.getBytes(), Bpub);
            System.out.println("Kab :"+Kab);
            //System.out.println("KabHash :"+Kabhash);
            byte[] kabhash=Kabhash.getBytes();
            c.ReEncryptPriv(enckab, Aprivkey);
            byte[] kab1=c.getpart1();
            byte[] kab2=c.getpart2();
            byte[]Enchashkab=c.PrivEncrypt(kabhash,Aprivkey);
            b.sendKab(kab1,kab2,Enchashkab);
            }else{System.err.println("Invalid Na");}}
        } catch (MalformedURLException | RemoteException | NotBoundException ex) {
             System.err.println("Rmi Neg Failed");
             ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("Can not open key file");
        } 
    }
       SecureRandom random = new SecureRandom();
      private BigInteger GenNounce() {
       BigInteger n=new BigInteger(80,random);
       return n;
    }
     public String getKab(){
     return this.Kab;
     }
   
  
    private BigInteger generateKab(String Nas, String Nbs) {
        BigInteger Fna=new BigInteger(Nas);
        BigInteger Fnb=new BigInteger(Nbs);
        BigInteger Fn=this.GenNounce();
        BigInteger Kab=Fna.modPow(Fnb, Fn);
        return Kab; 
    }
}
