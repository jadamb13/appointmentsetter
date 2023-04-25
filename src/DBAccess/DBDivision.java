package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBDivision {

    public static ObservableList<Division> getAllDivisionsFromDb(){
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        try {
            // SQL statement
            String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";

            // Create a PreparedStatement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute query and get results
            ResultSet rs = ps.executeQuery();

            // Work through result set one row at a time
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division d = new Division(divisionId, divisionName, countryId);
                divisionList.add(d);
            }

        }catch (SQLException e){
            System.out.println("Caught ye div: " + e.getMessage());
            }

                return divisionList;
    }

    public static ObservableList<String> getAllUnitedStatesDivisionNames(){
        ObservableList<String> unitedStatesDivisions = FXCollections.observableArrayList();
        for (Division d : getAllDivisionsFromDb()){
            if(d.getCountryId() == 1){
                unitedStatesDivisions.add(d.getDivision());
            }
        }

        return unitedStatesDivisions;
    }

    public static ObservableList<String> getAllCanadaDivisionNames(){
        ObservableList<String> canadaDivisions = FXCollections.observableArrayList();
        for (Division d : getAllDivisionsFromDb()){
            if(d.getCountryId() == 3){
                canadaDivisions.add(d.getDivision());
            }
        }
        return canadaDivisions;
    }

    public static ObservableList<String> getAllUnitedKingdomDivisionNames(){
        ObservableList<String> unitedKingdomDivisions = FXCollections.observableArrayList();
        for (Division d : getAllDivisionsFromDb()){
            if(d.getCountryId() == 2){
                unitedKingdomDivisions.add(d.getDivision());
            }
        }
        return unitedKingdomDivisions;
    }

    public static int getDivisionIdByName(String divisionName){
        for (Division d : getAllDivisionsFromDb()){
            if(d.getDivision().equals(divisionName)){
                return d.getDivisionId();
            }
        }
        return -1;
    }

    public static String getDivisionNameById(int divisionId){
        for (Division d : getAllDivisionsFromDb()){
            if(d.getDivisionId() == divisionId){
                return d.getDivision();
            }
        }
        return "";
    }

    public static String getCountryNameById(int divisionId) {
        String countryName = "";
        try {
            // SQL statement
            String sql = "SELECT Country FROM client_schedule.first_level_divisions, client_schedule.countries \n" +
                    "WHERE first_level_divisions.Country_ID = client_schedule.countries.Country_ID \n" +
                    "AND Division_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            countryName = rs.getString("Country");

        } catch(SQLException e){
            System.out.println("Caught you DBDivision: " + e.getMessage());
        }
        return countryName;
    }
}
