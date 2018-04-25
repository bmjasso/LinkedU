/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import the_beans.ProfileBean;
import the_beans.LoginBean;

/**
 *
 * @author IT353S837
 */
public interface ApplicationDAO {
    public int createProfile(ProfileBean aProfile);
    public int authenticateProfile(LoginBean aLogin);
    public void findByName(ProfileBean aProfile);
    public int checkUser(String userID);
    public int updateProfile(ProfileBean aProfile);
    


}
