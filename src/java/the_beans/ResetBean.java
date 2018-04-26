/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_beans;

/**
 *
 * @author IT353S831
 */
public class ResetBean {
    
    private String userID;
    private String email;

    public ResetBean() {
        
    }
    
    public ResetBean(String userID, String email) {
        this.userID = userID;
        this.email = email;
    }
    
    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
