/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import dao.ApplicationDAO;
import dao.ApplicationDAOImpl;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import the_beans.LoginBean;
import the_beans.ProfileBean;
import the_beans.ResetBean;

/**
 *
 * @author IT353S837
 */
@ManagedBean
@SessionScoped
public class LoginController {
    
 private LoginBean loginModel;
 private ProfileBean theModel;
 private ResetBean resetModel;
 int totalAttempts = 3;
 private String updateStatus = "";
 private String userResult = "";
 
 public LoginController(){
     theModel = new ProfileBean();
     loginModel = new LoginBean();
 }
 
 public String authenticate() {
        
         String login = "";
         if(totalAttempts != 0){
        ApplicationDAO aProfileDAO = new ApplicationDAOImpl();
        int status = aProfileDAO.authenticateProfile(loginModel);
        if (status == 1)
        {
            aProfileDAO.findByName(theModel);
            login = "index.xhtml";
        }
        else{
            totalAttempts--;
            login = "loginBad.xhtml";}
         }
         else{
             login = "loginFailed.xhtml";
         }
         return login;
    }
     public String createProfile() {
        ApplicationDAO aProfileDAO = new ApplicationDAOImpl();    // Creating a new object each time.
        int status = aProfileDAO.createProfile(theModel); // Doing anything with the object after this?
        if (status == 1)
            return "echo.xhtml"; // navigate to "echo.xhtml"
        else if(status == 2)
            return "error.xhtml"; 
        else if(status == 3)
            return "error2.xhtml";
        else
            return "error3.xhtml";
    }
     
     public void updateThis() {
        ApplicationDAO aProfileDAO = new ApplicationDAOImpl();    // Creating a new object each time.
        int status = aProfileDAO.updateProfile(theModel); // Doing anything with the object after this?
        if (status != 0) {
            setUpdateStatus("Profile updated successfully ...");
        } else {
            setUpdateStatus("Profile update failed!");
        }
    }
     
    /*public String resetPassword() {
        ApplicationDAO aApplicationDAO = new ApplicationDAOImpl();
        int status = aApplicationDAO.resetPassword(resetModel);
        if (status == 1)
        {
            return "resetGood.xhtml";
        }
        else
            return "resetBad.xhtml";
    }*/
    
    public String sendResetEmail() {
        ApplicationDAO aApplicationDAO = new ApplicationDAOImpl();
        int status = aApplicationDAO.sendResetEmailCheck(resetModel);
        if (status == 1)
        {
            return "resetGood.xhtml";
        }
        else
            return "resetBad.xhtml";
    }

    /**
     * @return the loginModel
     */
    public LoginBean getLoginModel() {
        return loginModel;
    }

    /**
     * @param loginModel the loginModel to set
     */
    public void setLoginModel(LoginBean loginModel) {
        this.loginModel = loginModel;
    }

    /**
     * @return the theModel
     */
    public ProfileBean getTheModel() {
        return theModel;
    }

    /**
     * @param theModel the theModel to set
     */
    public void setTheModel(ProfileBean theModel) {
        this.theModel = theModel;
    }

    /**
     * @return the updateStatus
     */
    public String getUpdateStatus() {
        return updateStatus;
    }

    /**
     * @param updateStatus the updateStatus to set
     */
    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    /**
     * @param userResult the userResult to set
     */
    public void setUserResult(String userResult) {
        this.userResult = userResult;
    }

    /**
     * @return the userResult
     */
    public String getUserResult() {
       ApplicationDAO aProfileDAO = new ApplicationDAOImpl(); 
      int status = aProfileDAO.checkUser(theModel.getUserID());
      if(status == 1)
      {
            setUserResult("<span style=\"color:red\">User ID is taken! </span>");
      }
      else
          setUserResult(" ");
      return userResult;
    }

    /**
     * @return the resetModel
     */
    public ResetBean getResetModel() {
        return resetModel;
    }

    /**
     * @param resetModel the resetModel to set
     */
    public void setResetModel(ResetBean resetModel) {
        this.resetModel = resetModel;
    }
    
    
    
}
