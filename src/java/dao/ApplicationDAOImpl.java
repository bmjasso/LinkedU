/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.mail.PasswordAuthentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import the_beans.ProfileBean;
import the_beans.LoginBean;

/**
 *
 * @author IT353S837
 * Brian Jasso
 */
public class ApplicationDAOImpl implements ApplicationDAO {
    
    String userId;
    
    @Override
    public int createProfile(ProfileBean aProfile) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            
            String query = "SELECT UserID FROM Users";
            String[] users = selectUsersFromDB(query);
            
            for(int i = 0;i < users.length;i++){
                if(users[i].equals(aProfile.getUserID()))
                {
                    rowCount = 2;
                    DBConn.close();
                    return rowCount;
                } 
                
            }
            
            if(!aProfile.getPassword().equals(aProfile.getPasswordConfirm())){
                rowCount = 3;
                DBConn.close();
                return rowCount;
            }
            
            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO Users VALUES ('"
                    + aProfile.getFirstName()
                    + "','" + aProfile.getLastName()
                    + "','" + aProfile.getUserID()
                    + "','" + aProfile.getPassword()
                    + "','" + aProfile.getEmail()
                    + "','" + aProfile.getSecurityQuestion()
                    + "','" + aProfile.getSecurityAnswer()
                    + "')";

            String insertString2 = "INSERT INTO LoginInfo VALUES ('"
                    + aProfile.getUserID()
                    + "','" + aProfile.getPassword()
                    + "')";
            
            rowCount = stmt.executeUpdate(insertString);
            stmt.executeUpdate(insertString2);
            System.out.println("insert string =" + insertString);
            sendConfirmationEmail(aProfile, aProfile.getEmail());
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 
        return rowCount;
    }
    
    private String[] selectUsersFromDB(String query){
         DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/Project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
      
            String users = "";
            String temp[] = new String [50];
            String userTable[] = new String[0];
            
            
        try {

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            int i = 0;

            while (rs.next()) {
                  users = rs.getString("UserID");
                  temp[i] = users;
                  i++;
            }
            userTable = new String[i];
            for(int j = 0; j < i;j++){
                userTable[j] = temp[j];
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return userTable;
       
    }
    
    private String[] selectPasswordsFromDB(String query){
        String temp[] = new String[50];
        String passTable[] = new String[0];
        String password = "";
        
        Connection DBConn = null;
        
        try{
            String myDB = "jdbc:derby://localhost:1527/Project353";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            int i = 0;
            
            while(rs.next()){
                password = rs.getString("Password");
                
                temp[i] = password;
                i++;
            }
            passTable = new String[i];
            
            for(int j = 0; j < i; j++){
                passTable[j] = temp[j];
            }
            rs.close();
            stmt.close();
        }
        catch(Exception e){
            System.err.println("Error: Problems with SQL select");
            e.printStackTrace();}
        try{
            DBConn.close();}
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return passTable;
               
            
        }
     
   
    @Override
    public int authenticateProfile(LoginBean aLogin){
        
        int rowCount = 0;
        try{
         
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/Project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        String query = "SELECT UserID FROM LoginInfo";
        String userTable[] = selectUsersFromDB(query);
        String query2 = "Select Password FROM LoginInfo";
        String passTable[] = selectPasswordsFromDB(query2);
        
        for(int i = 0; i < userTable.length;i++){
            if(userTable[i].equals(aLogin.getUserID())){
                if(passTable[i].equals(aLogin.getPassword())){
                    rowCount = 1;
                    userId = aLogin.getUserID();
                    return rowCount;
                }
            }
        }
        DBConn.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
       return rowCount;

    }
    
private String[] selectProfileFromDB(String query) {
        String[] profile;
        profile = new String[7];
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/Project353";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String firstName, lastName, userID, password, email, securityQuestion, securityAnswer;
            ProfileBean aProfileBean;
            while (rs.next()) {

                firstName = rs.getString("FirstName");
                lastName = rs.getString("LastName");
                userID = rs.getString("UserID");
                password = rs.getString("Password");
                email = rs.getString("Email");
                securityQuestion = rs.getString("SecurityQuestion");
                securityAnswer = rs.getString("SecurityAnswer");
                
                profile[0] = firstName;
                profile[1] = lastName;
                profile[2] = userID;
                profile[3] = password;
                profile[4] = email;
                profile[5] = securityQuestion;
                profile[6] = securityAnswer;


            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return profile;
    }
    
    @Override
    public void findByName(ProfileBean aProfile) {
        
        String query = "SELECT * FROM Users ";
        query += "WHERE userID = '" + userId + "'";
        
        String[] login;
        login = new String[7];
        login = selectProfileFromDB(query);
        
        aProfile.setFirstName(login[0]);
        aProfile.setLastName(login[1]);
        aProfile.setUserID(login[2]);
        aProfile.setPassword(login[3]);
        aProfile.setEmail(login[4]);
        aProfile.setSecurityQuestion(login[5]);
        aProfile.setSecurityAnswer(login[6]);
        
    }
    
    @Override
    public int updateProfile(ProfileBean aProfile) {
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            DBConn = DriverManager.getConnection(myDB, "itkstu", "student");


            String update;
            Statement stmt = DBConn.createStatement();

            update = "UPDATE Users SET "
                    + "firstName = '" + aProfile.getFirstName()+ "', "
                    + "lastName = '" + aProfile.getLastName() + "', "
                    + "password = '" + aProfile.getPassword() + "', "
                    + "email = '" + aProfile.getEmail() + "', "
                    + "securityQuestion = '" + aProfile.getSecurityQuestion() + "', "
                    + "securityAnswer = '" + aProfile.getSecurityAnswer() + "' "
                    + "WHERE userID = '" + aProfile.getUserID() + "'";
            rowCount = stmt.executeUpdate(update);
            System.out.println("updateString =" + update);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1.
        return rowCount;

    }
    
    private void sendConfirmationEmail(ProfileBean aProfile, String email) {
        
        String to = email;
        String from = "bmjasso@ilstu.edu";
        String host = "outlook.office365.com";
       
        
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "587");
        
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bmjasso@ilstu.edu", "Przybocki5254");
            }
        });

        try {
            
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
           
            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            message.setSubject("Sign Up Credentials");
            
            MimeMultipart multipart = new MimeMultipart("related");
            
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<H2>Welcome</H2>" 
                   + "<img src=\"https://cdn.d1baseball.com/logos/teams/256/illinoisst.png\">"
                   + "<h3>First Name:</h3> " + aProfile.getFirstName()
                   + "<h3>Last Name:</h3> "  + aProfile.getLastName()
                   + "<h3>User ID:</h3> "    + aProfile.getUserID()
                   + "<h3>Password:</h3>"    + aProfile.getPassword()
                   + "<h3>Email:</h3>"       + aProfile.getEmail()
                   + "<h3>Security Question:</h3>" + aProfile.getSecurityQuestion()
                   + "<h3>Security Answer:</h3>"   + aProfile.getSecurityAnswer();
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            
            
           // messageBodyPart = new MimeBodyPart();
            //DataSource fds = new FileDataSource("I:\\Lab3\\redbird.jpg");
            //messageBodyPart.setDataHandler(new DataHandler(fds));
            //messageBodyPart.setHeader("Content-ID", "<image>");
            
            
            //multipart.addBodyPart(messageBodyPart);
            
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    
     @Override
    public int checkUser(String userID) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            
            String query = "SELECT UserID FROM Users";
            String[] users = selectUsersFromDB(query);
            
            for(int i = 0;i < users.length;i++){
                if(users[i].equals(userID))
                {
                    rowCount = 1;
                    DBConn.close();
                    return rowCount;
                } 
    }
                    }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;
    }
}


   

    

