/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sms;

import javax.jws.WebService;

/**
 *
 * @author IT353S831
 */
@WebService(serviceName = "ISUSMS", portName = "ISUSMSPort", endpointInterface = "isu.ISUSMS", targetNamespace = "http://ISU/", wsdlLocation = "WEB-INF/wsdl/SMSWS/gfish.it.ilstu.edu_8080/ISUTextSMS/ISUSMS.wsdl")
public class SMSWS {

    public java.lang.String sendSMSISU(java.lang.String username, java.lang.String password, java.lang.String provider, java.lang.String number, java.lang.String message) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String sendSMS(java.lang.String provider, java.lang.String number, java.lang.String message) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.util.List<java.lang.String> getCarriers() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
