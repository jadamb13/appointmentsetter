package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A class to communicate with the DB about Divisions. */
public class DBDivision {

    /**
     Queries DB to gather data and create Division objects to be added to ObservableList.

     @return divisionList list of Division objects representing data in the DB

     */
    public static ObservableList<Division> getAllDivisionsFromDb(){
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

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

    /**
     Finds the name of the country with specified divisionId.

     @param divisionId divisionId of country
     @return countryName Country name corresponding to division ID parameter

     */
    public static String getCountryNameById(int divisionId) {
        String countryName = "";
        try {
            // SQL statement
            String sql = """
                    SELECT Country FROM client_schedule.first_level_divisions, client_schedule.countries\s
                    WHERE first_level_divisions.Country_ID = client_schedule.countries.Country_ID\s
                    AND Division_ID = ?""";

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
