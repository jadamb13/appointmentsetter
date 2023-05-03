package model;

import DBAccess.DBContact;
import DBAccess.DBUser;

/** Class representing a User object */
public class User {
    private int userId;
    private String userName;
    private String userPassword;

    public User(int userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /* Getters and Setters */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     Gets Name associated with a User ID.

     @param id contact id
     @return u.getUserName() Contact name associated with the parameter id

     */
    public static String getUserNameById(int id){
        for (User u : DBUser.getAllUsers()){
            if(u.getUserId() == id){
                return u.getUserName();
            }
        }
        return "";
    }
}
