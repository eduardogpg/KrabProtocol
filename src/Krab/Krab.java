/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Krab;

import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Yarib
 */
public interface Krab extends Remote{
    public BigInteger getmod(String name)throws RemoteException;
    public BigInteger getexp(String name)throws RemoteException;
    public void sendmod(BigInteger Alicemod)throws RemoteException;
    public void sendexp(BigInteger AliceExp)throws RemoteException;
    public void sendnounce(byte[]nounce1,byte[]nounce2)throws RemoteException;
    public String sendkey(byte[]nonce)throws RemoteException;
    public void Step2()throws RemoteException;
    public byte[] getNb1()throws RemoteException;
    public byte[] getNb2()throws RemoteException;
    public void sendKab(byte[] Kab1,byte[] Kab2,byte[]KabHash1)throws RemoteException;
}
