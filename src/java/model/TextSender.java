/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author IT353S831
 */
public class TextSender {

    public static java.util.List<java.lang.String> getCarriers() {
        isu.ISUSMS_Service service = new isu.ISUSMS_Service();
        isu.ISUSMS port = service.getISUSMSPort();
        return port.getCarriers();
    }

    public static String sendSMS(java.lang.String provider, java.lang.String number, java.lang.String message) {
        isu.ISUSMS_Service service = new isu.ISUSMS_Service();
        isu.ISUSMS port = service.getISUSMSPort();
        return port.sendSMS(provider, number, message);
    }
    
}
