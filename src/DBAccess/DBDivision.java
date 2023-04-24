package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
}
