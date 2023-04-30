package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A class to communicate with the DB about Users. */
public class DBUser {

    /**
     Queries DB to gather data and create User objects to be added to ObservableList.

     @return userList list of User objects representing data in the DB

     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT User_ID, User_Name, Password FROM client_schedule.users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                User userToAdd = new User(userId, username, password);
                userList.add(userToAdd);
            }
        } catch (SQLException e) {
            System.out.println("Caught you " + e.getMessage() + e.getCause());
        }
        return userList;
    }
}