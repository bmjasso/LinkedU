/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_beans;

/**
 *
 * @author IT353S837
 */
public class LoginBean {
    
    private String userID;
    private String password;
    
    public LoginBean(){
        
    }
    
    public LoginBean(String userID, String password){
        
        this.userID = userID;
        this.password = password;
        
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
