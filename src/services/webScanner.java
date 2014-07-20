/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

/**
 *
 * @author 1020142461
 */
public class webScanner {

    public static boolean imFirst(java.lang.String userName, java.lang.String ip) {
        org.scanner.Scanner_Service service = new org.scanner.Scanner_Service();
        org.scanner.Scanner port = service.getScannerPort();
        return port.imFirst(userName, ip);
    }

    private static String getFirstUserName() {
        org.scanner.Scanner_Service service = new org.scanner.Scanner_Service();
        org.scanner.Scanner port = service.getScannerPort();
        return port.getFirstUserName();
    }

    private static String getFisrtIp() {
        org.scanner.Scanner_Service service = new org.scanner.Scanner_Service();
        org.scanner.Scanner port = service.getScannerPort();
        return port.getFisrtIp();
    }

    private static boolean removeUser(java.lang.String userName) {
        org.scanner.Scanner_Service service = new org.scanner.Scanner_Service();
        org.scanner.Scanner port = service.getScannerPort();
        return port.removeUser(userName);
    }
    
    

    
}
